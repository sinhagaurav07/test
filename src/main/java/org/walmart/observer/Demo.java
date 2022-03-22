package org.walmart.observer;

public class Demo {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay();
        TemperatureDisplay temperatureDisplay = new TemperatureDisplay();
        weatherData.addObserver(currentConditionDisplay);
        weatherData.addObserver(temperatureDisplay);
        weatherData.setMeasurements(20.0f, 86.1f);
        System.out.println("----------------------");
        weatherData.setMeasurements(25.0f, 90.1f);
    }
}
