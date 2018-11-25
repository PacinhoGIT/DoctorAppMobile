package com.pacinho.doctorapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.POJO.Person;
import com.pacinho.doctorapp.R;
import com.pacinho.doctorapp.WS.WSConnector;

import java.util.ArrayList;

/**
 * Created by patryk on 2018-11-20.
 */

public class Main extends AppCompatActivity {

    private ListView patientsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView loginAsTV = (TextView) findViewById(R.id.loginAsTV);
        loginAsTV.setText("Login as " + GlobalParameters.doctor.getName());

        patientsLV = (ListView) findViewById(R.id.patientsLV);

        //Get patients by doctor ID
        WSConnector.getPatientsByDoctor();
        ArrayList<String> patients = new ArrayList<>();

        for (Person p : GlobalParameters.patients) {
            patients.add(p.getName() + " | " + p.getFirstName() + " " + p.getLastName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                patients);

        patientsLV.setAdapter(arrayAdapter);

        patientsLV.setOnItemClickListener((parent, view, position, id) -> {
                    GlobalParameters.selectedPatient = GlobalParameters.patients.get(position);
                    Intent intent = new Intent(Main.this, PatentDetails.class);
                    Main.this.startActivity(intent);
                }
        );

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getBaseContext(), "Cannot back !", Toast.LENGTH_LONG).show();
    }
}
