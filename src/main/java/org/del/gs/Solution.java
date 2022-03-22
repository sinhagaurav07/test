package org.del.gs;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Solution {

    public static void main(String[] args) {
        /*Config config = new Config();
        config.setApiKey("MW9S-E7SL-26DU-VV8V");
        config.setRealTimeUpdateServiceUrl("http://api.bart.gov/api/etd.aspx");
        config.setMaxTrainsToDisplay(10);
        config.setRetryLimit(3);

        String stationCode = "MONT";
        Solution solution = new Solution();
        EstimatedDepartureTimeResponse departureTimeResponse = solution.call(stationCode, config);
        printOutputData(departureTimeResponse);*/
    }

    /*private static void printOutputData(EstimatedDepartureTimeResponse departureTimeResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("------------------------------------------------------");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(StringUtils.isNotEmpty(departureTimeResponse.getSource()) ? departureTimeResponse.getSource() : StringUtils.EMPTY).append(" ::: ");
        stringBuilder.append(StringUtils.isNotEmpty(departureTimeResponse.getDate()) ? departureTimeResponse.getDate() : StringUtils.EMPTY).append("\t");
        stringBuilder.append(StringUtils.isNotEmpty(departureTimeResponse.getTime()) ? departureTimeResponse.getTime() : StringUtils.EMPTY);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("------------------------------------------------------");
        stringBuilder.append(System.lineSeparator());
        if (departureTimeResponse.getErrors() != null && !departureTimeResponse.getErrors().isEmpty()) {
            stringBuilder.append(departureTimeResponse.getErrors().get(0).getMessage());
            stringBuilder.append(System.lineSeparator());
        } else if (departureTimeResponse.getStations() == null || departureTimeResponse.getStations().isEmpty()) {
            stringBuilder.append("Updates are temporarily unavailable");
            stringBuilder.append(System.lineSeparator());
        } else {
            departureTimeResponse.getStations().stream().forEach(station -> {
                stringBuilder.append(String.format("%.2f", station.getEstimate().getEstimatedTimeOfDepartureInMinutes())).append("\t");
                stringBuilder.append(station.getDestination()).append("\t");
                stringBuilder.append(station.getEstimate().getPlatform());
                stringBuilder.append(System.lineSeparator());
            });
        }
        System.out.println(stringBuilder.toString());
    }

    private EstimatedDepartureTimeResponse call(String stationCode, Config config) {
        RealTimeEstimatedDataService realTimeEstimatedDataService = new RealTimeEstimatedDataService(config);
        return realTimeEstimatedDataService.getRealTimeData(stationCode);
    }

    public class RealTimeEstimatedDataService {

        private Config config;

        public RealTimeEstimatedDataService(Config config) {
            this.config = config;
        }

        public EstimatedDepartureTimeResponse getRealTimeData(String stationCode) {
            List<Error> validationErrors = validateStationCode(stationCode);
            if (!validationErrors.isEmpty()) {
                EstimatedDepartureTimeResponse estimatedDepartureTimeResponse = new EstimatedDepartureTimeResponse();
                estimatedDepartureTimeResponse.setErrors(validationErrors);
                return estimatedDepartureTimeResponse;
            }
            //prepare query string
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put(Constants.CMD, CMD.etd.name());
            queryParams.put(Constants.ORIGIN, stationCode);
            queryParams.put(Constants.KEY, this.config.getApiKey());
            queryParams.put(Constants.JSON, Constants.Y);

            HttpConnector httpConnector = new HttpConnector(this.config.getRetryLimit());
            String contents = httpConnector.getContents(this.config.getRealTimeUpdateServiceUrl(), queryParams);
            ObjectMapper responseObjectMapper = new ObjectMapper();
            EstimatedDepartureTimeResponse estimatedDepartureTimeResponse = responseObjectMapper.map(contents);
            return filterRequiredStationsForDisplayInSortedOrder(estimatedDepartureTimeResponse, this.config);
        }

        private EstimatedDepartureTimeResponse filterRequiredStationsForDisplayInSortedOrder(EstimatedDepartureTimeResponse estimatedDepartureTimeResponse, Config config) {
            if (estimatedDepartureTimeResponse != null && estimatedDepartureTimeResponse.getStations() != null &&
                                                        !estimatedDepartureTimeResponse.getStations().isEmpty()) {
                PriorityQueue<Station> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(s -> s.getEstimate().getEstimatedTimeOfDepartureInMinutes()));
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
                errors.add(new Error("StationCode is invalid", ErrorCode.VALIDATION_ERROR.getCode()));
            }
            return errors;
        }
    }

    public class HttpConnector {

        private int retryNumbers;

        public HttpConnector(int retryNumbers) {
            this.retryNumbers = retryNumbers;
        }

        public String getContents(String apiUrl, Map<String, String> queryParams) {
            apiUrl = StringUtils.join(apiUrl, buildQueryPathParams(queryParams));
            StringBuilder content = new StringBuilder();
            int retry = 0;
            while (retry < retryNumbers) {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(Constants.GET);
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                            String line;
                            while ((line = input.readLine()) != null) {
                                content.append(line);
                            }
                        } finally {
                            connection.disconnect();
                            break;
                        }
                    } else {
                        ++retry;
                    }
                    if (retry == retryNumbers) {
                        content.append(buildErrorResponse(Integer.toString(responseCode), connection.getResponseMessage()));
                    }
                } catch (IOException e) {
                    content.append(buildErrorResponse(ErrorCode.UNEXPECTED_ERROR.getCode(), ErrorCode.UNEXPECTED_ERROR.getMessage()));
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

        public List<Station> getStations() {
            return stations;
        }

        public void setStations(List<Station> stations) {
            this.stations = stations;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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

        public double getEstimatedTimeOfDepartureInMinutes() {
            return (double) delay + minutes % 60;
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
        public static final String ABBR = "abbr";
        public static final String ETD = "etd";
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

    public class ObjectMapper {

        public EstimatedDepartureTimeResponse map(String content) {
            EstimatedDepartureTimeResponse estimatedDepartureTimeResponse = new EstimatedDepartureTimeResponse();
            JSONObject jsonObject;
            try {
                jsonObject = (JSONObject) new JSONParser().parse(content);
            } catch (ParseException exp) {
                //If an invalid station code is passed, error response is not a json.
                estimatedDepartureTimeResponse.setErrors(Arrays.asList(new Error(ErrorCode.UNEXPECTED_ERROR.getMessage(), ErrorCode.UNEXPECTED_ERROR.getCode())));
                return estimatedDepartureTimeResponse;
            }

            JSONObject root = (JSONObject) jsonObject.get(Constants.ROOT);
            estimatedDepartureTimeResponse.setDate(convertToString(root.get(Constants.DATE))); // convert to date object and NPE
            estimatedDepartureTimeResponse.setTime(convertToString(root.get(Constants.TIME))); // convert to timeStamp and check NPE
            if(StringUtils.isNotEmpty((String)root.get(Constants.MESSAGE))) {
                JSONObject message = (JSONObject) root.get(Constants.MESSAGE);
                if (message != null) {
                    estimatedDepartureTimeResponse.setMessage(convertToString(message.get(Constants.WARNING_MESSAGE)));
                    if (message.get(Constants.ERROR) != null) {
                        Error error = new Error(convertToString(message.get(Constants.ERROR)), ErrorCode.UNEXPECTED_ERROR.getCode());
                        estimatedDepartureTimeResponse.setErrors(Arrays.asList(error));
                    }
                }
            }

            JSONArray jsonArray = (JSONArray) root.get(Constants.STATION);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                jsonArray.stream().forEach(jsonObj -> {
                    JSONObject stationJson = (JSONObject) jsonObj;
                    estimatedDepartureTimeResponse.setSource(convertToString(stationJson.get(Constants.NAME)));
                    estimatedDepartureTimeResponse.setSourceCode(convertToString(stationJson.get(Constants.ABBR)));

                    JSONObject errorMessage = (JSONObject) ((JSONObject) jsonObj).get(Constants.MESSAGE);
                    if (errorMessage != null) {
                        String errorString = convertToString(errorMessage.get(Constants.ERROR));
                        Error error = new Error(errorString, ErrorCode.SERVICE_UNAVAILABLE.getCode()); // create an enum
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
            } else {
                Error error = new Error(ErrorCode.DATA_NOT_AVAILABLE.getMessage(), ErrorCode.DATA_NOT_AVAILABLE.getMessage());
                estimatedDepartureTimeResponse.getErrors().add(error);
            }
            return estimatedDepartureTimeResponse;
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

    public static class Config {
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
    }*/
}


