package com.seanlubbers.stacker.rest;

// Class to allow Jackson to convert something to JSON.
// Jackson will automatically take the fields here and return JSON.
public class PokemonErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public PokemonErrorResponse() {
    }

    public PokemonErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}