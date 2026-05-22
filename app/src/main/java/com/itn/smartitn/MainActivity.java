package com.itn.smartitn;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.itn.smartitn.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Démarrer directement LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }
}