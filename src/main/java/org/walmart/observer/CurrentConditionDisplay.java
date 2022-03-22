package org.walmart.observer;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionDisplay implements Display, Observer {

    private float temperature;
    private float humidity;

    @Override
    public void update(Observable o, Object arg) {
        WeatherData weatherData = (WeatherData)o;
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        display();
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature  + "F degrees and " + humidity + "% humidity");
    }
}
