package com.example.mentalhealthlogin;

public class Activityinfo {
    private String what;
    private String duration;
    private String timestamp;

    public Activityinfo(String what, String duration, String timestamp) {
        this.what = what;
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
