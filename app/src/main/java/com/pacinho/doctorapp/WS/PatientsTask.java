package com.pacinho.doctorapp.WS;

import android.os.AsyncTask;

import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.POJO.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.pacinho.doctorapp.GlobalParameters.doctor;
import static com.pacinho.doctorapp.GlobalParameters.getPatientsFinish;

/**
 * Created by patryk on 2018-11-20.
 */

public class PatientsTask extends AsyncTask {

    private static final String PATIENTS_ENDPOINT = "/services/apexrest/MyPatients/";

    private void getData() {
        try {
            getPatientsFinish = false;
            GlobalParameters.patients = null;
            URL endpoint = new URL(WSParams.instanceURL + PATIENTS_ENDPOINT + doctor.getID());

            HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + WSParams.sessionId);

            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String apiOutput = br.readLine();
            conn.disconnect();


            try {

                JSONArray patients = new JSONArray(apiOutput);

                ArrayList<Person> patientsArr = new ArrayList<>();

                for (int i = 0; i < patients.length(); i++) {
                    JSONObject patient = patients.getJSONObject(i);
                    if (patient != null) {
                        Person patientObj = new Person();
                        patientObj.setFirstName((String) patient.get("First_Name__c"));
                        patientObj.setLastName((String) patient.get("Last_name__c"));
                        patientObj.setID((String) patient.get("Id"));
                        patientObj.setName((String) patient.get("Name"));
                        patientsArr.add(patientObj);
                    }
                }
                GlobalParameters.patients=patientsArr;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        getPatientsFinish = true;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        getData();
        return null;
    }
}
