package com.pacinho.doctorapp.WS;

import android.os.AsyncTask;

import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.POJO.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.pacinho.doctorapp.GlobalParameters.getDoctorFinish;

/**
 * Created by patryk on 2018-11-19.
 */

public class LoginTask extends AsyncTask {

    private static final String DOCTOR_ENDPOINT = "/services/apexrest/Doctor/";

    private void getData(String login) {
        try {
            getDoctorFinish = false;
            GlobalParameters.doctor = null;
            URL endpoint = new URL(WSParams.instanceURL + DOCTOR_ENDPOINT + login);

            HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + WSParams.sessionId);

            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));


            String apiOutput = br.readLine();
            conn.disconnect();


            try {

                JSONObject doctor = new JSONObject(apiOutput);
                System.out.println(doctor);
                if (doctor != null) {
                    GlobalParameters.doctor = new Person();
                    GlobalParameters.doctor.setFirstName((String) doctor.get("First_Name__c"));
                    GlobalParameters.doctor.setLastName((String) doctor.get("Last_name__c"));
                    GlobalParameters.doctor.setPassword((String) doctor.get("Password__c"));
                    GlobalParameters.doctor.setID((String) doctor.get("Id"));
                    GlobalParameters.doctor.setName((String) doctor.get("Name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();


            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        getDoctorFinish = true;

    }

    @Override
    protected Object doInBackground(Object[] params) {
        getData((String) params[0]);
        return null;
    }
}
