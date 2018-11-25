package com.pacinho.doctorapp.WS;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.pacinho.doctorapp.GlobalParameters.getAddPhotoFinish;
import static com.pacinho.doctorapp.GlobalParameters.selectedExamination;

/**
 * Created by patryk on 2018-11-22.
 */

public class AddPhotoTask extends AsyncTask {

    private static final String PHOTO_ENDPOINT = "/services/apexrest/Photo/";

    private void putImageToWS(String name, String source, String description) {

        try {
            getAddPhotoFinish = false;
            URL endpoint = new URL(WSParams.instanceURL + PHOTO_ENDPOINT + selectedExamination.getId());

            HttpURLConnection conn = (HttpURLConnection) endpoint.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + WSParams.sessionId);

            JSONObject params = new JSONObject();
            params.put("name", name);
            params.put("content", "data:image/jpeg;base64,"+source);
            params.put("description", description);

            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(params.toString());
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println(response.toString());
            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        getAddPhotoFinish = true;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        putImageToWS((String) params[0], (String) params[1], (String) params[2]);
        return null;
    }
}
