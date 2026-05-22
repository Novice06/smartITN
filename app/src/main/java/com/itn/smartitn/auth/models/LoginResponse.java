package com.itn.smartitn.auth.models;


public class LoginResponse {
    private boolean status;
    private String message;
    private Etudiant etudiant;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public Etudiant getEtudiant() { return etudiant; }
}
