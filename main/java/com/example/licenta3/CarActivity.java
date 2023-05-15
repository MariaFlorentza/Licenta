package com.example.licenta3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarActivity extends AppCompatActivity {

    private Spinner spn_marca;
    private Spinner spn_model;
    private Map<String, List<String>> carModelsMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        spn_marca = findViewById(R.id.marca_masina);
        spn_model=findViewById(R.id.model_masina);

        initializeMap();
        initilizeSpinner();


    }

    private void initializeMap() {
        carModelsMap = new HashMap<>();

        carModelsMap.put("Audi", Arrays.asList(getResources().getStringArray(R.array.model_masina)));
        //carModelsMap.put("Bentley", Arrays.asList(getResources().getStringArray(R.array.model_masina_bentley)));

    }

    private void initilizeSpinner() {
        ArrayAdapter<String> adapterCars = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.marca_masina));
        adapterCars.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_marca.setAdapter(adapterCars);

        spn_marca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = adapterView.getItemAtPosition(position).toString();
                List<String> models = carModelsMap.get(selectedItem);

                if (models != null) {
                    ArrayAdapter<String> adapterModels = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, models);
                    spn_model.setAdapter(adapterModels);

                    spn_marca.setVisibility(View.GONE);
                    spn_model.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        spn_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedModel = parent.getItemAtPosition(position).toString();
                Toast.makeText(CarActivity.this, "Model selectat: " + selectedModel, Toast.LENGTH_SHORT).show();


                List<String> models = carModelsMap.get(selectedModel);
                ArrayAdapter<String> modelsAdapter = new ArrayAdapter<String>(CarActivity.this, android.R.layout.simple_spinner_item, models);
                spn_model.setAdapter(modelsAdapter);




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}