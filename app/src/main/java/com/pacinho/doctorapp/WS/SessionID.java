package com.pacinho.doctorapp.WS;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by patryk on 2018-11-19.
 */

public class SessionID  extends AsyncTask {

    private static final String REFRESH_TOKEN = "5Aep861j70kdjF1ZXNcbSJJv.DzJiEpjKhNJeX5aOJVn7AfQ837nFTWOUgzihrkFdtjnxtL13zi6g68NM2lpVQ2";
    private static final String CLIENT_ID = "3MVG9KsVczVNcM8xdsl9J1_WjAhjb022ysL_hvbUQdymFW6VwuSgMxMhA7i8wldx5QUCIGadYzNZTw5CxiQap";
    private static final String CLIENT_SECRET = "4017820097319317704";
    private static final String LOGIN_ENDPOINT = "https://login.salesforce.com/services/oauth2/token";

    private static void getSessionId() throws IOException {
        URL endpoint = new URL(LOGIN_ENDPOINT);
        HttpsURLConnection conn = (HttpsURLConnection) endpoint.openConnection();
        conn.setRequestMethod("POST");
        String params = "grant_type=refresh_token&refresh_token=" + REFRESH_TOKEN + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;

        conn.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(conn.getOutputStream());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        wr.writeBytes(params);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = null;
        try {
            json = new JSONObject(response.toString());
            WSParams.sessionId = (String) json.get("access_token");
            WSParams.instanceURL = (String) json.get("instance_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        conn.disconnect();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            getSessionId();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //return new String[]{sessionId,instanceURL};
        return null;
    }
}
