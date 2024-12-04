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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button LoginButton;
    private EditText editTextTextEmailAddress, editTextTextPassword;
    static String getUsersGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LoginButton = findViewById(R.id.LoginButton);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);


        LoginButton.setOnClickListener(v -> {
            String email = editTextTextEmailAddress.getText().toString();
            String pass = editTextTextPassword.getText().toString();

            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Field must not be empty!", Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Retrieve the current user after successful sign-in
                                    FirebaseUser user = mAuth.getCurrentUser();


                                    setUsersEmail(user.getEmail());

                                    if (user != null) {
                                        Toast.makeText(Login.this, "Sign in Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent nav = new Intent(Login.this, MainContent.class);
                                        startActivity(nav);
                                        finish();
                                    } else {
                                        // If user is null after successful sign-in
                                        Toast.makeText(Login.this, "User not found!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    // Sign-in failed
                                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
    public static void setUsersEmail(String e){
         getUsersGmail = e;
    }

    public static String getUsersEmail(){
        return getUsersGmail;
    }
}
