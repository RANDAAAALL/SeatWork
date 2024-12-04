package com.example.seatwork;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {
    private Button RegisterButton;
    private EditText editTextTextEmailAddress, editTextTextPassword;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RegisterButton = findViewById(R.id.RegisterButton);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);

        RegisterButton.setOnClickListener(v -> {
//            Toast.makeText(this, "Register Button was clicked!", Toast.LENGTH_SHORT).show();\

            String email = editTextTextEmailAddress.getText().toString();
            String pass = editTextTextPassword.getText().toString();

            if(email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Field must not be empty!", Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // successfully created if there was no email's duplicate
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Account Successfully Created!.",Toast.LENGTH_SHORT).show();
                                    Intent nav = new Intent(MainActivity.this,Login.class);
                                    startActivity(nav);
                                    finish();
                                }
                                else {
                                    try{
                                        throw task.getException();
                                    }catch(FirebaseAuthUserCollisionException e){
                                        // 1. Signup failed if the email is already registered
                                        Toast.makeText(MainActivity.this, "This email is already registered. Please use a different email.", Toast.LENGTH_SHORT).show();
                                    }catch(Exception e){
                                        // 2. Signup failed if email or password doesn't matched the requirements!
                                        Toast.makeText(MainActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

    }
}