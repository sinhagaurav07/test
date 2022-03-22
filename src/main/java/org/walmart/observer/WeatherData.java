package org.walmart.observer;

import java.util.Observable;

public class WeatherData extends Observable {

    private float temperature;
    private float humidity;

    public void setMeasurements(float temperature, float humidity) {
        this.humidity = humidity;
        this.temperature = temperature;
        setChanged();
        notifyObservers();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }
}
