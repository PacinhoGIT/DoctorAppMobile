package com.pacinho.doctorapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.POJO.Examination;
import com.pacinho.doctorapp.POJO.Person;
import com.pacinho.doctorapp.R;
import com.pacinho.doctorapp.WS.WSConnector;

import java.util.ArrayList;

/**
 * Created by patryk on 2018-11-20.
 */

public class PatentDetails extends AppCompatActivity {

    private ListView examLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_details);

        final TextView loginAsTV = (TextView) findViewById(R.id.patientNameTV);
        Person patient = GlobalParameters.selectedPatient;
        loginAsTV.setText("Patient " + patient.getFirstName() + " " + patient.getLastName());

        examLV = (ListView) findViewById(R.id.examLV);

        //Get Patient Examinations
        WSConnector.getExaminationsByPatient();

        ArrayList<String> examinations = new ArrayList<>();

        for (Examination p : GlobalParameters.examinations) {
            examinations.add(p.getName() + " | " + p.getCustomName() + "| " + p.getDescription());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                examinations);

        examLV.setAdapter(arrayAdapter);

        examLV.setOnItemClickListener((parent, view, position, id) -> {
            GlobalParameters.selectedExamination = GlobalParameters.examinations.get(position);
            Intent intent = new Intent(PatentDetails.this, ExaminationActivity.class);
            PatentDetails.this.startActivity(intent);
                }
        );
    }
}
