package com.itn.smartitn.events;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Event {

    @SerializedName("id")
    public int id;

    @SerializedName("titre")
    public String titre;

    @SerializedName("description")
    public String description;

    @SerializedName("image_url")
    public String imageUrl;

    @SerializedName("date_evenement")
    public String date;

    @SerializedName("lieu")
    public String lieu;

    // Classe interne pour la réponse liste
    public static class ListResponse {
        @SerializedName("status")
        public boolean status;
        @SerializedName("evenements")
        public List<Event> evenements;
    }

    // Classe interne pour la réponse détail
    public static class DetailResponse {
        @SerializedName("status")
        public boolean status;
        @SerializedName("evenement")
        public Event evenement;
    }

    // Formate "2026-04-15 14:00:00" → "15 Avr 2026 à 14:00"
    public String dateFormatee() {
        try {
            String[] parts = date.split(" ");
            String[] d = parts[0].split("-");
            String[] mois = {"","Jan","Fév","Mar","Avr","Mai","Juin","Juil","Aoû","Sep","Oct","Nov","Déc"};
            int m = Integer.parseInt(d[1]);
            String heure = parts.length > 1 ? parts[1].substring(0, 5) : "";
            return d[2] + " " + mois[m] + " " + d[0] + " à " + heure;
        } catch (Exception e) {
            return date;
        }
    }
}