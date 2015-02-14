package com.adafruit.sensor;

public class Sensor
{
    private String name;                // sensor name
    private int version;                // version of the hardware + driver
    private int sensorId;              // unique sensor identifier
    private int type;                   // this sensor's type (ex. SENSOR_TYPE_LIGHT)
    private float maxValue;            // maximum value of this sensor's value in SI units
    private float minValue;            // minimum value of this sensor's value in SI units
    private float resolution;			// smallest difference between two values reported by this sensor
    private long minDelay;             // min delay in microseconds between events. zero = not a constant rate

}
