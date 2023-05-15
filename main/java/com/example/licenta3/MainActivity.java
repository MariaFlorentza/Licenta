package com.example.licenta3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private EditText emailField, passwordField;
    private TextView forgotTextLink;
    private Button loginBtn, btnLog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initcomponents();

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void initcomponents() {
        fabAdd = findViewById(R.id.main_fab_add);
        fabAdd.setOnClickListener(getAddEvent());
        emailField = findViewById(R.id.mail_field);
        passwordField = findViewById(R.id.password_field);
        loginBtn = findViewById(R.id.login_button);
        btnLog = findViewById(R.id.btn_log);
        

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });



        forgotTextLink = findViewById(R.id.tv_forgotPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    emailField.setError("Este necesar un nume de utilizator.");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    passwordField.setError("Este necesara parola.");
                    return;
                }

                if(password.length() < 6) {
                    passwordField.setError("Parola trebuie sa aiba mai mult de 6 caractere.");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Logare cu succes", Toast.LENGTH_SHORT)
                                            .show();
                                    startActivity(new Intent(getApplicationContext(), AddActivityMenu.class));

                                }else {
                                    Toast.makeText(MainActivity.this, "Eroare" + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                );
            }

        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Resetati parola?");
                passwordResetDialog.setMessage("Adauga email-ul tau pentru a primi link-ul de resetare");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Reseteaza link-ul", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Eroare! Resetare incorecta" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

    }



    private View.OnClickListener getAddEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivityMenu.class);
                startActivity(intent);
            }
        };
    }
}
