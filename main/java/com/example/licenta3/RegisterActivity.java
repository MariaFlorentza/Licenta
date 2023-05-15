package com.example.licenta3;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText last_name;
    private TextInputEditText email;
    private TextInputEditText phone;
    private TextInputEditText password;

    Button btn_create, verifyCode;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();

        firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            Toast.makeText(getApplicationContext(),firebaseAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
//            finish();
//        }

        verifyCode = findViewById(R.id.verify_email);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(!user.isEmailVerified()) {
            verifyCode.setVisibility(View.VISIBLE);
            verifyCode.setVisibility(View.VISIBLE);
            verifyCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "Verificare e-mail trimisa", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: E-mail netrimis" + e.getMessage());
                        }
                    });
                }
            });
        }
    }

    private void initComponents() {
        name = findViewById(R.id.name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        btn_create = findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String passwordUser = password.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email.setError("Este necesar email-ul.");
                    return;
                }

                if (TextUtils.isEmpty(passwordUser)) {
                    password.setError("Este necesara parola.");
                    return;
                }

                if (password.length() < 6) {
                    password.setError("Parola trebuie sa aiba mai mult de 6 caractere.");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(mail, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this, "Verificare email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email-ul nu a fost trimis" + e.getMessage());
                                }
                            });


                            Toast.makeText(RegisterActivity.this, "Utilizator creat", Toast.LENGTH_SHORT)
                                    .show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Eroare" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }

}