package com.pacinho.doctorapp.WS;

import android.os.AsyncTask;

import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.POJO.Examination;
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
import static com.pacinho.doctorapp.GlobalParameters.getExaminationsFinish;
import static com.pacinho.doctorapp.GlobalParameters.getPatientsFinish;
import static com.pacinho.doctorapp.GlobalParameters.selectedPatient;

/**
 * Created by patryk on 2018-11-20.
 */

public class ExaminationTask extends AsyncTask {

    private static final String EXAMINAION_ENDPOINT = "/services/apexrest/Examinations/";

    private void getData() {
        try {
            getExaminationsFinish = false;
            GlobalParameters.examinations = null;
            URL endpoint = new URL(WSParams.instanceURL + EXAMINAION_ENDPOINT + selectedPatient.getID());

            HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + WSParams.sessionId);

            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String apiOutput = br.readLine();
            conn.disconnect();


            try {

                JSONArray examinations = new JSONArray(apiOutput);

                ArrayList<Examination> examinationArr = new ArrayList<>();

                for (int i = 0; i < examinations.length(); i++) {
                    JSONObject exam = examinations.getJSONObject(i);
                    if (exam != null) {
                        Examination examObj = new Examination();
                        examObj.setId((String) exam.get("Id"));
                        examObj.setName((String) exam.get("Name"));
                        examObj.setCustomName((String) exam.get("Name__c"));
                        examObj.setDescription((String) exam.get("Description__c"));
                        examinationArr.add(examObj);
                    }
                }
                GlobalParameters.examinations=examinationArr;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        getExaminationsFinish = true;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        getData();
        return null;
    }
}