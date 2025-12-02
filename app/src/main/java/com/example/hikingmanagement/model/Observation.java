package com.example.hikingmanagement.model;

public class Observation {
    private long id;
    private long hikeId; // foreign key linking to Hike
    private String observation;
    private String time; // store as string for simplicity
    private String comments;

    public Observation() {}

    public Observation(long id, long hikeId, String observation, String time, String comments) {
        this.id = id;
        this.hikeId = hikeId;
        this.observation = observation;
        this.time = time;
        this.comments = comments;
    }


    public Observation(long hikeId, String observation, String time, String comments) {
        this.hikeId = hikeId;
        this.observation = observation;
        this.time = time;
        this.comments = comments;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getHikeId() { return hikeId; }
    public void setHikeId(long hikeId) { this.hikeId = hikeId; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "Observation{" +
                "id=" + id +
                ", hikeId=" + hikeId +
                ", observation='" + observation + '\'' +
                ", time='" + time + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
