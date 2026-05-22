package com.itn.smartitn.auth.models;


import com.google.gson.annotations.SerializedName;

public class Etudiant {
    private int id;
    private String nom, prenom, email, telephone, gender, adresse, niveau;
    @SerializedName("created_at") private String createdAt;

    public Etudiant(int id, String nom, String prenom, String email, String telephone,
                    String gender, String adresse, String niveau, String createdAt) {
        this.id = id; this.nom = nom; this.prenom = prenom; this.email = email;
        this.telephone = telephone; this.gender = gender; this.adresse = adresse;
        this.niveau = niveau; this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
    public String getGender() { return gender; }
    public String getAdresse() { return adresse; }
    public String getNiveau() { return niveau; }
    public String getCreatedAt() { return createdAt; }
}
