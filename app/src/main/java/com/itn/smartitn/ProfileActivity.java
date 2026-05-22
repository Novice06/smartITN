package com.itn.smartitn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.itn.smartitn.auth.LoginActivity;
import com.itn.smartitn.auth.models.Etudiant;
import com.itn.smartitn.auth.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvWelcome, tvNom, tvPrenom, tvEmail, tvTelephone, tvGender, tvNiveau, tvAdresse;
    private Button btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            goToLogin();
            return;
        }

        tvWelcome = findViewById(R.id.tvWelcome);
        tvNom = findViewById(R.id.tvNom);
        tvPrenom = findViewById(R.id.tvPrenom);
        tvEmail = findViewById(R.id.tvEmail);
        tvTelephone = findViewById(R.id.tvTelephone);
        tvGender = findViewById(R.id.tvGender);
        tvNiveau = findViewById(R.id.tvNiveau);
        tvAdresse = findViewById(R.id.tvAdresse);
        btnLogout = findViewById(R.id.btnLogout);

        Etudiant user = sessionManager.getUser();
        if (user != null) {
            tvWelcome.setText("Bienvenue " + user.getPrenom() + " " + user.getNom() + " !");
            tvNom.setText(user.getNom());
            tvPrenom.setText(user.getPrenom());
            tvEmail.setText(user.getEmail());
            tvTelephone.setText(user.getTelephone().isEmpty() ? "Non renseigné" : user.getTelephone());
            tvGender.setText(user.getGender().equals("M") ? "Masculin" : "Féminin");
            tvNiveau.setText(user.getNiveau());
            tvAdresse.setText(user.getAdresse().isEmpty() ? "Non renseignée" : user.getAdresse());
        }

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(ProfileActivity.this, "Déconnecté", Toast.LENGTH_SHORT).show();
            goToLogin();
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
