package com.pacinho.doctorapp.WS;

import java.io.IOException;

import static com.pacinho.doctorapp.GlobalParameters.getAddPhotoFinish;
import static com.pacinho.doctorapp.GlobalParameters.getDoctorFinish;
import static com.pacinho.doctorapp.GlobalParameters.getExaminationsFinish;
import static com.pacinho.doctorapp.GlobalParameters.getPatientsFinish;


/**
 * Created by patryk on 2018-11-18.
 */

public class WSConnector {

    private static void getSessionID() {
        if (WSParams.sessionId == null) {
            SessionID conn = new SessionID();
            conn.execute();
            while (WSParams.sessionId == null) {
            }
            System.out.println("Finish get Session ID");
        }
    }

    public static void getDoctorByLogin(String login) throws IOException {

        getSessionID();
        getDoctorFinish=false;
        LoginTask lt = new LoginTask();
        lt.execute(login);
        while (!getDoctorFinish) {}
        System.out.println("Doctor Finish");

    }

    public static void getPatientsByDoctor(){
        getSessionID();
        getPatientsFinish=false;
        PatientsTask lt = new PatientsTask();
        lt.execute();
        while (!getPatientsFinish) {}
        System.out.println("Patients Finish");
    }

    public static void getExaminationsByPatient(){
        getSessionID();
        getExaminationsFinish=false;
        ExaminationTask lt = new ExaminationTask();
        lt.execute();
        while (!getExaminationsFinish) {}
        System.out.println("Examinations Finish");
    }

    public static void addPhoto(String name, String source, String description){
        getSessionID();
        getAddPhotoFinish=false;
        AddPhotoTask lt = new AddPhotoTask();
        lt.execute(name, source, description);
        while (!getAddPhotoFinish) {}
        System.out.println("Add photo Finish");
    }
}
