package com.example.seatwork;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainContent extends AppCompatActivity {
    private TextView usersGmail;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button LogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usersGmail = findViewById(R.id.usersGmail);
        LogoutButton = findViewById(R.id.LogoutButton);
        usersGmail.setText(Login.getUsersEmail());


        LogoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent nav = new Intent(this, Login.class);
            startActivity(nav);
            finish();
        });
    }

}