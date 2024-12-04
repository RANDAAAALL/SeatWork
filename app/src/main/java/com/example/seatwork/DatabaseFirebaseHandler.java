package com.example.seatwork;
import static java.lang.System.err;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class DatabaseFirebaseHandler {

        private FirebaseAuth mAuth = FirebaseAuth.getInstance();


        public void SignUp(Activity c, String e, String p){

            // SignOuts the user when creating new account
            mAuth.signOut();

            mAuth.createUserWithEmailAndPassword(e, p)
                    .addOnCompleteListener(c, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // successfully created if there was no email's duplicate
                            if (task.isSuccessful()) {
                                Toast.makeText(c, "Account Successfully Created!.",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                try{
                                    throw task.getException();
                                }catch(FirebaseAuthUserCollisionException e){
                                    // 1. Signup failed if the email is already registered
                                    Toast.makeText(c, "This email is already registered. Please use a different email.", Toast.LENGTH_SHORT).show();
                                }catch(Exception e){
                                    // 2. Signup failed if email or password doesn't matched the requirements!
                                    Toast.makeText(c, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

        public void SignIn(Activity c, String e, String p) {
            mAuth.signInWithEmailAndPassword(e, p)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Retrieve the current user after successful sign-in
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    Toast.makeText(c, "Sign in Successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If user is null after successful sign-in
                                    Toast.makeText(c, "User not found!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // Sign-in failed
                                Toast.makeText(c, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        public void Logout(Activity c){
            mAuth.signOut();
        }
    }

