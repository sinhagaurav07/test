package org.walmart.observer;

import java.util.Observable;
import java.util.Observer;

public class TemperatureDisplay implements Display, Observer {

    private float temperature;

    @Override
    public void update(Observable o, Object arg) {
        WeatherData weatherData = (WeatherData)o;
        this.temperature = weatherData.getTemperature();
        display();
    }

    @Override
    public void display() {
        System.out.println("Temperature: " + temperature  + "F degrees");
    }
}
