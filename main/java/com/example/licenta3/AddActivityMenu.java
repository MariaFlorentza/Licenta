package com.example.licenta3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivityMenu extends AppCompatActivity {

    FloatingActionButton fabBack;
    Button btn_add_car;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        fabBack = findViewById(R.id.add_fabBack);
        btn_add_car = findViewById(R.id.btn_add_car);

        fabBack.setOnClickListener(getBack());
        btn_add_car.setOnClickListener(getAddCar());
    }

    private View.OnClickListener getAddCar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarActivity.class);
                startActivity(intent);

            }
        };
    }

    private View.OnClickListener getBack() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
    }
}