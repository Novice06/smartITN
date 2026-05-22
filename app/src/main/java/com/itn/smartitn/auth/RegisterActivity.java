package com.itn.smartitn.auth;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.itn.smartitn.R;
import com.itn.smartitn.auth.models.RegisterRequest;
import com.itn.smartitn.auth.models.RegisterResponse;
import com.itn.smartitn.auth.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etNom, etPrenom, etEmail, etTelephone, etAdresse, etPassword;
    private TextInputLayout tilNom, tilPrenom, tilEmail, tilPassword, tilNiveau;
    private RadioGroup rgGender;
    private AutoCompleteTextView etNiveau;
    private Button btnRegister;
    private TextView tvError, tvLogin;
    private ProgressBar progressBar;

    private String[] niveaux = {"Bac1", "Bac2", "Bac3", "Bac4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_register);

        // Initialisation des vues
        tilNom = findViewById(R.id.tilNom);
        tilPrenom = findViewById(R.id.tilPrenom);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilNiveau = findViewById(R.id.tilNiveau);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);
        etAdresse = findViewById(R.id.etAdresse);
        etPassword = findViewById(R.id.etPassword);

        rgGender = findViewById(R.id.rgGender);
        etNiveau = findViewById(R.id.etNiveau);
        btnRegister = findViewById(R.id.btnRegister);
        tvError = findViewById(R.id.tvError);
        tvLogin = findViewById(R.id.tvLogin);
        progressBar = findViewById(R.id.progressBar);

        // Configuration du spinner Niveau
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, niveaux);
        etNiveau.setAdapter(adapter);

        // Au clic sur le champ Niveau, afficher la liste
        etNiveau.setOnClickListener(v -> etNiveau.showDropDown());

        // Bouton inscription
        btnRegister.setOnClickListener(v -> register());

        // Lien retour vers connexion
        tvLogin.setOnClickListener(v -> finish());
    }

    private void register() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String telephone = etTelephone.getText().toString().trim();
        String adresse = etAdresse.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Récupération du genre
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = "";
        if (selectedGenderId == R.id.rbMale) {
            gender = "M";
        } else if (selectedGenderId == R.id.rbFemale) {
            gender = "F";
        }

        String niveau = etNiveau.getText().toString().trim();

        // ========== VALIDATIONS ==========
        boolean isValid = true;

        if (TextUtils.isEmpty(nom)) {
            tilNom.setError("Nom requis");
            isValid = false;
        } else {
            tilNom.setError(null);
        }

        if (TextUtils.isEmpty(prenom)) {
            tilPrenom.setError("Prénom requis");
            isValid = false;
        } else {
            tilPrenom.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email requis");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Email invalide");
            isValid = false;
        } else {
            tilEmail.setError(null);
        }

        if (selectedGenderId == -1) {
            Toast.makeText(this, "Veuillez sélectionner votre genre", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (TextUtils.isEmpty(niveau)) {
            tilNiveau.setError("Niveau requis");
            isValid = false;
        } else {
            tilNiveau.setError(null);
        }

        if (password.length() < 6) {
            tilPassword.setError("Mot de passe doit contenir au moins 6 caractères");
            isValid = false;
        } else {
            tilPassword.setError(null);
        }

        if (!isValid) {
            return;
        }

        // ========== APPEL API ==========

        progressBar.setVisibility(View.VISIBLE);
        btnRegister.setEnabled(false);
        tvError.setVisibility(View.GONE);

        RegisterRequest request = new RegisterRequest(nom, prenom, email, telephone,
                gender, adresse, niveau, password);
        Call<RegisterResponse> call = RetrofitClient.getApiService().register(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnRegister.setEnabled(true);

                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(RegisterActivity.this,
                            "Inscription réussie ! Veuillez vous connecter", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String message = response.body() != null ? response.body().getMessage() : "Erreur d'inscription";
                    tvError.setText(message);
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnRegister.setEnabled(true);
                tvError.setText("Erreur réseau: " + t.getMessage());
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}
