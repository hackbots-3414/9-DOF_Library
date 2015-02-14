package com.adafruit.sensor;

public class SensorEvent
{
    private int version;                          /* must be sizeof(struct sensors_event_t) */
    private int sensor_id;                        /* unique sensor identifier */
    private int type;                             /* sensor type */
    private int reserved0;                        /* reserved */
    private long timestamp;                        /* time is in milliseconds */
	private SensorVector vector;
}
