package org.delphix.gs;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OptimalAccountBalancingTest {
    public static void main(String[] args) {
        JUnitCore.main("org.delphix.gs.OptimalAccountBalancingTest");
    }

    @Test
    public void testRealServiceCall() {

        Config config = new Config();
        config.setApiKey("MW9S-E7SL-26DU-VV8V");
        config.setRealTimeUpdateServiceUrl("http://api.bart.gov/api/etd.aspx");
        config.setMaxTrainsToDisplay(10);
        config.setRetryLimit(3);

        String stationCode = "MONT";
        HttpConnector httpConnector = new HttpConnector(config.getRetryLimit());
        ObjectMapper objectMapper = new ObjectMapper();
        RealTimeEstimatedDataService realTimeEstimatedDataService = new RealTimeEstimatedDataService(config, httpConnector, objectMapper);
        EstimatedDepartureTimeResponse departureTimeResponse = realTimeEstimatedDataService.getRealTimeData(stationCode);

        ConsoleWriter consoleWriter = new ConsoleWriter();
        consoleWriter.printOutputData(departureTimeResponse);

        List<Station> stations = departureTimeResponse.getStations();
        if (stations != null && !stations.isEmpty()) {
            int previousEstimation = 0;
            for (Station station : stations) {
                assertTrue("Stations are not sorted based on estimation time",
                        station.getEstimate().getEstimatedTimeOfDepartureInSeconds() >= previousEstimation);
                previousEstimation = station.getEstimate().getEstimatedTimeOfDepartureInSeconds();
            }
        }
    }

    @Test
    public void testRealServiceCallWithInvalidAPIUrl() {
        Config config = new Config();
        config.setApiKey("MW9S-E7SL-26DU-VV8V");
        config.setRealTimeUpdateServiceUrl("http://anyUrl");
        config.setMaxTrainsToDisplay(5);
        config.setRetryLimit(1);

        String stationCode = "MONT";
        HttpConnector httpConnector = new HttpConnector(config.getRetryLimit());
        ObjectMapper objectMapper = new ObjectMapper();
        RealTimeEstimatedDataService realTimeEstimatedDataService = new RealTimeEstimatedDataService(config, httpConnector, objectMapper);
        EstimatedDepartureTimeResponse departureTimeResponse = realTimeEstimatedDataService.getRealTimeData(stationCode);

        assertEquals(departureTimeResponse.getErrors().size(), 1);
        assertEquals("Error message is not unexpected error", "Unexpected error occurred", departureTimeResponse.getErrors().get(0).getMessage());
        assertEquals("Error code is not 500.ETD.ERROR", "500.ETD.ERROR", departureTimeResponse.getErrors().get(0).getErrorCode());

        ConsoleWriter consoleWriter = new ConsoleWriter();
        consoleWriter.printOutputData(departureTimeResponse);
    }

    @Test
    public void testRealServiceCallWithInvalidInput() {
        Config config = new Config();
        config.setApiKey("MW9S-E7SL-26DU-VV8V");
        config.setRealTimeUpdateServiceUrl("http://api.bart.gov/api/etd.aspx");
        config.setMaxTrainsToDisplay(5);
        config.setRetryLimit(1);

        String stationCode = "MON";
        HttpConnector httpConnector = new HttpConnector(config.getRetryLimit());
        ObjectMapper objectMapper = new ObjectMapper();
        RealTimeEstimatedDataService realTimeEstimatedDataService = new RealTimeEstimatedDataService(config, httpConnector, objectMapper);
        EstimatedDepartureTimeResponse departureTimeResponse = realTimeEstimatedDataService.getRealTimeData(stationCode);
        //The response from api in case of invalid station code returns an xml, not a json; mapping the error to 500 error.
        assertEquals(departureTimeResponse.getErrors().size(), 1);
        assertEquals("Error message is not unexpected error", "Unexpected error occurred", departureTimeResponse.getErrors().get(0).getMessage());
        assertEquals("Error code is not 500.ETD.ERROR", "500.ETD.ERROR", departureTimeResponse.getErrors().get(0).getErrorCode());

        ConsoleWriter consoleWriter = new ConsoleWriter();
        consoleWriter.printOutputData(departureTimeResponse);

        stationCode = StringUtils.EMPTY;
        departureTimeResponse = realTimeEstimatedDataService.getRealTimeData(stationCode);
        assertEquals(departureTimeResponse.getErrors().size(), 1);
        assertEquals("Error is not invalid station", "Station is invalid", departureTimeResponse.getErrors().get(0).getMessage());
        assertEquals("Error code is not 400.ETD.VALIDATION_ERROR", "400.ETD.VALIDATION_ERROR", departureTimeResponse.getErrors().get(0).getErrorCode());

        consoleWriter.printOutputData(departureTimeResponse);
    }

    /**
     * Service to get the real time data
     */
    public class RealTimeEstimatedDataService {

        private Config config;
        private HttpConnector httpConnector;
        private ObjectMapper responseObjectMapper;

        public RealTimeEstimatedDataService(Config config, HttpConnector httpConnector, ObjectMapper responseObjectMapper) {
            this.config = config;
            this.httpConnector = httpConnector;
            this.responseObjectMapper = responseObjectMapper;
        }

        /**
         * Gives the real time data
         *
         * @param stationCode station code for which real time data is required
         * @return EstimatedDepartureTimeResponse
         */
        public EstimatedDepartureTimeResponse getRealTimeData(String stationCode) {
            List<Error> validationErrors = validateStationCode(stationCode);
            if (!validationErrors.isEmpty()) {
                EstimatedDepartureTimeResponse estimatedDepartureTimeResponse = buildEstimatedDepartureTimeResponse(stationCode);
                estimatedDepartureTimeResponse.setErrors(validationErrors);
                return estimatedDepartureTimeResponse;
            }
            //prepare query string
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put(Constants.CMD, CMD.etd.name());
            queryParams.put(Constants.KEY, this.config.getApiKey());
            queryParams.put(Constants.JSON, Constants.Y);
            queryParams.put(Constants.ORIGIN, stationCode);

            EstimatedDepartureTimeResponse estimatedDepartureTimeResponse;

            try {
                String contents = httpConnector.getContents(this.config.getRealTimeUpdateServiceUrl(), queryParams);
                estimatedDepartureTimeResponse = responseObjectMapper.map(contents);
            } catch (Exception exp) {
                //logger to log the exception
                estimatedDepartureTimeResponse = buildEstimatedDepartureTimeResponse(stationCode);
                estimatedDepartureTimeResponse.setErrors(Arrays.asList(new Error(ErrorCode.UNEXPECTED_ERROR.getMessage(), ErrorCode.UNEXPECTED_ERROR.getCode())));
            }
            return filterRequiredStationsInSortedOrder(estimatedDepartureTimeResponse, this.config);
        }

        private EstimatedDepartureTimeResponse buildEstimatedDepartureTimeResponse(String stationCode) {
            EstimatedDepartureTimeResponse estimatedDepartureTimeResponse = new EstimatedDepartureTimeResponse();
            estimatedDepartureTimeResponse.setSourceCode(stationCode);
            estimatedDepartureTimeResponse.setSource(stationCode);
            estimatedDepartureTimeResponse.setDate(TimeFormatUtil.getCurrentDateAndTime(StringUtils.EMPTY).getKey());
            estimatedDepartureTimeResponse.setTime(TimeFormatUtil.getCurrentDateAndTime(StringUtils.EMPTY).getValue());
            return estimatedDepartureTimeResponse;
        }


        private EstimatedDepartureTimeResponse filterRequiredStationsInSortedOrder(EstimatedDepartureTimeResponse estimatedDepartureTimeResponse, Config config) {
            if (estimatedDepartureTimeResponse != null && estimatedDepartureTimeResponse.getStations() != null &&
                    !estimatedDepartureTimeResponse.getStations().isEmpty()) {
                PriorityQueue<Station> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(s -> s.getEstimate().getEstimatedTimeOfDepartureInSeconds()));
                estimatedDepartureTimeResponse.getStations().forEach(station -> {
                    if (priorityQueue.size() < config.getMaxTrainsToDisplay()) {
                        priorityQueue.add(station);
                    }
                });
                List<Station> stations = new ArrayList<>();
                while (!priorityQueue.isEmpty()) {
                    stations.add(priorityQueue.poll());
                }
                estimatedDepartureTimeResponse.setStations(stations);
            }
            return estimatedDepartureTimeResponse;
        }

        private List<Error> validateStationCode(String stationCode) {
            List<Error> errors = new ArrayList<>();
            if (StringUtils.isEmpty(stationCode)) {
                errors.add(new Error(Constants.STATION_CODE_INVALID, ErrorCode.VALIDATION_ERROR.getCode()));
            }
            return errors;
        }
    }


    /**
     * HttpConnector class to get contents using HTTP connection
     */
    public class HttpConnector {

        private int retryAllowedCounter;

        public HttpConnector(int retryAllowedCounter) {
            this.retryAllowedCounter = retryAllowedCounter;
        }

        /**
         * Get HTTP response using {@Link HttpURLConnection} GET protocol
         *
         * @param apiUrl      API URL to call
         * @param queryParams query parameters
         * @return java.lang.String
         */
        public String getContents(String apiUrl, Map<String, String> queryParams) {
            apiUrl = StringUtils.join(apiUrl, buildQueryPathParams(queryParams));
            StringBuilder content = new StringBuilder();
            int retryCount = 0;
            while (retryCount < retryAllowedCounter) {
                int responseCode = 0;
                String responseMessage = StringUtils.EMPTY;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(apiUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(Constants.GET);
                    responseCode = connection.getResponseCode();
                    responseMessage = connection.getResponseMessage();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                            String line;
                            while ((line = input.readLine()) != null) {
                                content.append(line);
                            }
                        }
                        break;
                    } else {
                        ++retryCount;
                    }
                } catch (IOException e) {
                    //logger to log the exception
                    ++retryCount;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                if (retryCount == retryAllowedCounter) {
                    content.append(buildErrorResponse(Integer.toString(responseCode), responseMessage));
                }
            }
            return content.toString();
        }

        private String buildErrorResponse(String responseCode, String errorMessage) {

            JSONObject errorMessageJson = new JSONObject();
            errorMessageJson.put(Constants.ERROR, StringUtils.join("Error Occurred while retrieving data, ErrorCode:: ",
                    responseCode, "Error Message:: ", errorMessage));
            JSONObject message = new JSONObject();
            message.put(Constants.MESSAGE, errorMessageJson);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Constants.ROOT, message);
            return jsonObject.toJSONString();
        }

        private String buildQueryPathParams(Map<String, String> params) {
            StringBuilder query = new StringBuilder();
            query.append("?");
            params.entrySet().stream().forEach(paramEntry -> {
                query.append(paramEntry.getKey()).append("=").append(paramEntry.getValue());
                query.append("&");
            });
            return query.substring(0, query.length() - 1);
        }
    }

    public class EstimatedDepartureTimeResponse {
        private String date;
        private String time;
        private String message;
        private String source;
        private String sourceCode;
        private List<Station> stations;
        private List<Error> errors;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<Station> getStations() {
            return stations;
        }

        public void setStations(List<Station> stations) {
            this.stations = stations;
        }

        public List<Error> getErrors() {
            return errors;
        }

        public void setErrors(List<Error> errors) {
            this.errors = errors;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourceCode() {
            return sourceCode;
        }

        public void setSourceCode(String sourceCode) {
            this.sourceCode = sourceCode;
        }
    }

    public class Error {
        private String message;
        private String errorCode;

        public Error(String message, String errorCode) {
            this.message = message;
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public String getErrorCode() {
            return errorCode;
        }
    }

    public class Station {
        private String destination;
        private String destinationCode;
        private Estimate estimate;

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDestinationCode() {
            return destinationCode;
        }

        public void setDestinationCode(String destinationCode) {
            this.destinationCode = destinationCode;
        }

        public Estimate getEstimate() {
            return estimate;
        }

        public void setEstimate(Estimate estimate) {
            this.estimate = estimate;
        }
    }

    public class Estimate {
        private Integer delay;
        private Integer minutes;
        private String platform;
        private String direction;
        private Integer length;
        private String color;
        private String hexColor;
        private String bikeFlag;

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getHexColor() {
            return hexColor;
        }

        public void setHexColor(String hexColor) {
            this.hexColor = hexColor;
        }

        public String getBikeFlag() {
            return bikeFlag;
        }

        public void setBikeFlag(String bikeFlag) {
            this.bikeFlag = bikeFlag;
        }

        public int getEstimatedTimeOfDepartureInSeconds() {
            return minutes * 60 + delay;
        }
    }

    public class Constants {
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String MESSAGE = "message";
        public static final String WARNING_MESSAGE = "warning";
        public static final String ERROR = "error";
        public static final String STATION = "station";
        public static final String NAME = "name";
        public static final String ETD = "etd";
        public static final String ABBR = "abbr";
        public static final String ESTIMATE = "estimate";
        public static final String DESTINATION = "destination";
        public static final String ABBREVIATION = "abbreviation";
        public static final String DELAY = "delay";
        public static final String COLOR = "color";
        public static final String HEXCOLOR = "hexcolor";
        public static final String MINUTES = "minutes";
        public static final String LENGTH = "length";
        public static final String BIKEFLAG = "bikeflag";
        public static final String PLATFORM = "platform";
        public static final String DIRECTION = "direction";

        public static final String CMD = "cmd";
        public static final String ORIGIN = "orig";
        public static final String KEY = "key";
        public static final String JSON = "json";
        public static final String Y = "y";
        public static final String ROOT = "root";

        public static final String GET = "GET";

        //Validation Messages
        public static final String STATION_CODE_INVALID = "Station is invalid";
    }

    public enum CMD {
        etd
    }

    public enum ErrorCode {

        SERVICE_UNAVAILABLE("Service is unavailable", "400.ETD.SERVICE_UNAVAILABLE"),
        UNEXPECTED_ERROR("Unexpected error occurred", "500.ETD.ERROR"),
        VALIDATION_ERROR("Input validation error", "400.ETD.VALIDATION_ERROR"),
        DATA_NOT_AVAILABLE("Data not available", "400.ETD.SERVICE_UNAVAILABLE");

        private String message;
        private String code;

        ErrorCode(String message, String code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }


    /**
     * Object Mapper class to convert String to required Response class
     */
    public class ObjectMapper {

        public EstimatedDepartureTimeResponse map(String content) throws ParseException {
            EstimatedDepartureTimeResponse estimatedDepartureTimeResponse = new EstimatedDepartureTimeResponse();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(content);
            JSONObject root = (JSONObject) jsonObject.get(Constants.ROOT);
            estimatedDepartureTimeResponse.setDate(root.get(Constants.DATE) != null ? convertToString(root.get(Constants.DATE)) : TimeFormatUtil.getCurrentDateAndTime(StringUtils.EMPTY).getKey());
            estimatedDepartureTimeResponse.setTime(root.get(Constants.TIME) != null ? convertToString(root.get(Constants.TIME)) : TimeFormatUtil.getCurrentDateAndTime(StringUtils.EMPTY).getValue());
            buildMessageAndError(estimatedDepartureTimeResponse, root);

            JSONArray jsonArray = (JSONArray) root.get(Constants.STATION);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                buildStationAndEstimation(estimatedDepartureTimeResponse, jsonArray);
            } else {
                Error error = new Error(ErrorCode.DATA_NOT_AVAILABLE.getMessage(), ErrorCode.DATA_NOT_AVAILABLE.getCode());
                estimatedDepartureTimeResponse.getErrors().add(error);
            }
            return estimatedDepartureTimeResponse;
        }

        private void buildStationAndEstimation(EstimatedDepartureTimeResponse estimatedDepartureTimeResponse, JSONArray jsonArray) {
            jsonArray.stream().forEach(jsonObj -> {
                JSONObject stationJson = (JSONObject) jsonObj;
                estimatedDepartureTimeResponse.setSource(convertToString(stationJson.get(Constants.NAME)));
                estimatedDepartureTimeResponse.setSourceCode(convertToString(stationJson.get(Constants.ABBR)));

                JSONObject errorMessage = (JSONObject) ((JSONObject) jsonObj).get(Constants.MESSAGE);
                if (errorMessage != null) {
                    String errorString = convertToString(errorMessage.get(Constants.ERROR));
                    Error error = new Error(errorString, ErrorCode.SERVICE_UNAVAILABLE.getCode());
                    estimatedDepartureTimeResponse.setErrors(Arrays.asList(error));
                }

                JSONArray estimates = (JSONArray) stationJson.get(Constants.ETD);
                if (estimates != null && !estimates.isEmpty()) {
                    List<Station> stations = new ArrayList<>();
                    estimates.stream().forEach(estimate -> {
                        JSONObject estimateObject = (JSONObject) estimate;
                        JSONArray etdEstimates = (JSONArray) estimateObject.get(Constants.ESTIMATE);
                        if (etdEstimates != null)
                            buildStationEstimatedDepartureList(stations, estimateObject, etdEstimates);
                    });
                    estimatedDepartureTimeResponse.setStations(stations);
                }
            });
        }

        private void buildMessageAndError(EstimatedDepartureTimeResponse estimatedDepartureTimeResponse, JSONObject root) {
            if (!(root.get(Constants.MESSAGE) instanceof String)) {
                JSONObject message = (JSONObject) root.get(Constants.MESSAGE);
                if (message != null) {
                    estimatedDepartureTimeResponse.setMessage(convertToString(message.get(Constants.WARNING_MESSAGE)));
                    if (message.get(Constants.ERROR) != null) {
                        Error error = new Error(convertToString(message.get(Constants.ERROR)), ErrorCode.UNEXPECTED_ERROR.getCode());
                        estimatedDepartureTimeResponse.setErrors(Arrays.asList(error));
                    }
                }
            }
        }

        private void buildStationEstimatedDepartureList(List<Station> stations, JSONObject estimateObject, JSONArray etdEstimates) {
            etdEstimates.stream().forEach(etd -> {
                JSONObject etdObject = (JSONObject) etd;
                Station station = new Station();
                station.setDestination((String) estimateObject.get(Constants.DESTINATION));
                station.setDestinationCode((String) estimateObject.get(Constants.ABBREVIATION));

                Estimate est = new Estimate();
                est.setDelay(convertToInteger(etdObject.get(Constants.DELAY)));
                est.setColor(convertToString(etdObject.get(Constants.COLOR)));
                est.setHexColor(convertToString(etdObject.get(Constants.HEXCOLOR)));
                est.setMinutes(convertToInteger(etdObject.get(Constants.MINUTES)));
                est.setLength(convertToInteger(etdObject.get(Constants.LENGTH)));
                est.setBikeFlag(convertToString(etdObject.get(Constants.BIKEFLAG)));
                est.setPlatform(convertToString(etdObject.get(Constants.PLATFORM)));
                est.setDirection(convertToString(etdObject.get(Constants.DIRECTION)));
                station.setEstimate(est);
                stations.add(station);
            });
        }

        private Integer convertToInteger(Object data) {
            String strData = convertToString(data);
            return NumberUtils.isNumber(strData) ? Integer.parseInt(strData) : 0;
        }

        private String convertToString(Object data) {
            return data != null ? (String) data : StringUtils.EMPTY;
        }
    }

    /**
     * Config to hold API URl details, key etc
     */
    public class Config {
        private String realTimeUpdateServiceUrl;
        private String apiKey;
        private Integer maxTrainsToDisplay;
        private Integer retryLimit;

        public String getRealTimeUpdateServiceUrl() {
            return realTimeUpdateServiceUrl;
        }

        public void setRealTimeUpdateServiceUrl(String realTimeUpdateServiceUrl) {
            this.realTimeUpdateServiceUrl = realTimeUpdateServiceUrl;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public Integer getMaxTrainsToDisplay() {
            return maxTrainsToDisplay;
        }

        public void setMaxTrainsToDisplay(Integer maxTrainsToDisplay) {
            this.maxTrainsToDisplay = maxTrainsToDisplay;
        }

        public Integer getRetryLimit() {
            return retryLimit;
        }

        public void setRetryLimit(Integer retryLimit) {
            this.retryLimit = retryLimit;
        }
    }

    /**
     * Writes data to console
     */
    public class ConsoleWriter {

        public void printOutputData(EstimatedDepartureTimeResponse departureTimeResponse) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("------------------------------------------------------");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(StringUtils.isNotEmpty(departureTimeResponse.getSource()) ? departureTimeResponse.getSource() : StringUtils.EMPTY).append(" ::: ");
            stringBuilder.append(StringUtils.isNotEmpty(departureTimeResponse.getDate()) ? departureTimeResponse.getDate() : StringUtils.EMPTY).append("\t");
            stringBuilder.append(StringUtils.isNotEmpty(departureTimeResponse.getTime()) ? departureTimeResponse.getTime() : StringUtils.EMPTY);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("------------------------------------------------------");
            stringBuilder.append(System.lineSeparator());
            if (departureTimeResponse.getStations() == null || departureTimeResponse.getStations().isEmpty()) {
                stringBuilder.append("Updates are temporarily unavailable");
                stringBuilder.append(System.lineSeparator());
            } else {
                departureTimeResponse.getStations().stream().forEach(station -> {
                    stringBuilder.append(TimeFormatUtil.convertSecondsToStandardTimeFormat(station.getEstimate().getEstimatedTimeOfDepartureInSeconds())).append("\t");
                    stringBuilder.append(station.getDestination()).append("\t");
                    stringBuilder.append(station.getEstimate().getPlatform());
                    stringBuilder.append(System.lineSeparator());
                });
            }
            System.out.println(stringBuilder.toString());
        }
    }

    public static class TimeFormatUtil {
        public static String convertSecondsToStandardTimeFormat(int seconds) {
            int second = seconds % 60;
            int hour = seconds / 60;
            int minute = hour % 60;
            hour = hour / 60;
            return StringUtils.join(String.format("%02d", hour), ":", String.format("%02d", minute), ":", String.format("%02d", second));
        }

        public static Pair<String, String> getCurrentDateAndTime(String timeZone) {
            String zoneName = StringUtils.EMPTY;
            if (StringUtils.isEmpty(timeZone)) {
                timeZone = TimeZoneEnum.AMERICAS_PDT.getTimeZone();
                zoneName = TimeZoneEnum.AMERICAS_PDT.getZoneName();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            dateFormatter.setTimeZone(TimeZone.getTimeZone(timeZone));

            SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");
            timeFormatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            timeFormatter.format(calendar.getTime());

            return new ImmutablePair<>(dateFormatter.format(calendar.getTime()), StringUtils.join(timeFormatter.format(calendar.getTime()), " ", zoneName));
        }
    }

    public enum TimeZoneEnum {
        AMERICAS_PDT("America/Los_Angeles", "PDT");

        private String timeZone;
        private String zoneName;

        TimeZoneEnum(String timeZone, String zoneName) {
            this.timeZone = timeZone;
            this.zoneName = zoneName;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getZoneName() {
            return zoneName;
        }

        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }
    }
}
