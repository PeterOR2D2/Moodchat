package com.example.mentalhealthlogin;

public class SleepInfo {
    private String sleep_quality;
    private String hours_of_sleep;
    private String slept_time;
    private String sleep_timestamp;

    public SleepInfo(String sleep_quality, String hours_of_sleep, String slept_time, String sleep_timestamp) {
        this.sleep_quality = sleep_quality;
        this.hours_of_sleep = hours_of_sleep;
        this.slept_time = slept_time;
        this.sleep_timestamp = sleep_timestamp;
    }

    public String getSleep_quality() {
        return sleep_quality;
    }

    public void setSleep_quality(String sleep_quality) {
        this.sleep_quality = sleep_quality;
    }

    public String getHours_of_sleep() {
        return hours_of_sleep;
    }

    public void setHours_of_sleep(String hours_of_sleep) {
        this.hours_of_sleep = hours_of_sleep;
    }

    public String getSlept_time() {
        return slept_time;
    }

    public void setSlept_time(String slept_time) {
        this.slept_time = slept_time;
    }

    public String getSleep_timestamp() {
        return sleep_timestamp;
    }

    public void setSleep_timestamp(String sleep_timestamp) {
        this.sleep_timestamp = sleep_timestamp;
    }
}
