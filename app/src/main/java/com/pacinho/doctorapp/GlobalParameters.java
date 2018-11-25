package com.pacinho.doctorapp;

import com.pacinho.doctorapp.POJO.Examination;
import com.pacinho.doctorapp.POJO.Person;

import java.util.ArrayList;

/**
 * Created by patryk on 2018-11-19.
 */

public class GlobalParameters {

    public static Person doctor;
    public static Person selectedPatient;
    public static Examination selectedExamination;
    public static ArrayList<Person> patients;
    public static ArrayList<Examination> examinations;

    public static boolean getAddPhotoFinish;
    public static boolean getExaminationsFinish;
    public static boolean getDoctorFinish;
    public static boolean getPatientsFinish;
}
