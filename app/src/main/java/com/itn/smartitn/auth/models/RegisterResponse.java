package com.itn.smartitn.auth.models;


public class RegisterResponse {
    private boolean status;
    private String message;
    private int id;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public int getId() { return id; }
}
