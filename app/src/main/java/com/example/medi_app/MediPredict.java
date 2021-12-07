package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.medi_app.model.Form;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MediPredict extends AppCompatActivity {

    private Interpreter interpreter;
    private CustomModelDownloadConditions conditions;
    private Form patientForm;
//    private Map diabetes, output;
    private int outputs;

//    private ArrayList diabetes, alzheimers, heartDisease, output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_predict);

        //PatientForm has all form for AIs, just use .getWhatever()
        patientForm = getIntent().getExtras().getParcelable("patientForm");

        Float[] diabetes = {(float) patientForm.getPregnancies(), (float) patientForm.getGlucose(), (float) patientForm.getBloodPressure(), (float) patientForm.getInsulin(), patientForm.getBmi(), patientForm.getDiabetesPedigreeFunction(), (float) patientForm.getAge()};
        Integer[] output = {outputs};

        conditions = new CustomModelDownloadConditions.Builder().requireWifi().build();
        
        FirebaseModelDownloader.getInstance().getModel("DiabetesAi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                .addOnSuccessListener(model -> {
                    File modelFile = model.getFile();
                    if (modelFile != null) {
                        interpreter = new Interpreter(modelFile);
                    }
                });
    }
}