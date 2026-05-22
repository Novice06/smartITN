package com.itn.smartitn.auth.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.itn.smartitn.auth.models.Etudiant;
import com.google.gson.Gson;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER = "user_data";

    private SharedPreferences pref;
    private Gson gson;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveUser(Etudiant user) {
        pref.edit()
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .putString(KEY_USER, gson.toJson(user))
                .apply();
    }

    public Etudiant getUser() {
        String json = pref.getString(KEY_USER, null);
        return json != null ? gson.fromJson(json, Etudiant.class) : null;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        pref.edit().clear().apply();
    }
}
