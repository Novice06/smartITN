package com.itn.smartitn.auth.models;


public class RegisterRequest {
    private String nom, prenom, email, telephone, gender, adresse, niveau, password;

    public RegisterRequest(String nom, String prenom, String email, String telephone,
                           String gender, String adresse, String niveau, String password) {
        this.nom = nom; this.prenom = prenom; this.email = email;
        this.telephone = telephone; this.gender = gender; this.adresse = adresse;
        this.niveau = niveau; this.password = password;
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getGender() { return gender; }
    public String getAdresse() { return adresse; }
    public String getNiveau() { return niveau; }
    public String getPassword() { return password; }
}
