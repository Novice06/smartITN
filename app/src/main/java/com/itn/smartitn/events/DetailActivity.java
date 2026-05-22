package com.itn.smartitn.events;

import com.itn.smartitn.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitre, tvDate, tvLieu, tvDescription;
    ProgressBar progressBar;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        tvTitre       = findViewById(R.id.tv_titre);
        tvDate        = findViewById(R.id.tv_date);
        tvLieu        = findViewById(R.id.tv_lieu);
        tvDescription = findViewById(R.id.tv_description);
        progressBar   = findViewById(R.id.progress_bar);
        ivImage       = findViewById(R.id.iv_image);

        TextView btnRetour = findViewById(R.id.btn_retour);
        btnRetour.setOnClickListener(v -> finish());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        int id = getIntent().getIntExtra("EVENT_ID", -1);
        if (id == -1) { finish(); return; }

        chargerDetail(id);
    }

    void chargerDetail(int id) {
        progressBar.setVisibility(View.VISIBLE);

        RetrofitClient.get().getEvent(id).enqueue(new Callback<Event.DetailResponse>() {
            @Override
            public void onResponse(Call<Event.DetailResponse> call, Response<Event.DetailResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    Event event = response.body().evenement;
                    tvTitre.setText(event.titre);
                    tvDate.setText("📅 " + event.dateFormatee());
                    tvLieu.setText("📍 " + event.lieu);
                    tvDescription.setText(event.description);

                    if (event.imageUrl != null && !event.imageUrl.isEmpty()) {
                        Glide.with(DetailActivity.this)
                                .load(event.imageUrl)
                                .centerCrop()
                                .into(ivImage);
                    } else {
                        ivImage.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "Événement introuvable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Event.DetailResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}