//Tobias Lennon
//R00191512
package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.medi_app.model.Form;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import org.tensorflow.lite.Interpreter;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class MediPredict extends AppCompatActivity {

    private CustomModelDownloadConditions conditions;
    private Form patientForm;
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_predict);

        //PatientForm has all form for AIs, just use .getWhatever()
        patientForm = getIntent().getExtras().getParcelable("patientForm");

        setInterpreter();

    }

    public void setInterpreter(){
        FirebaseModelDownloader.getInstance().getModel("DiabetesAi", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions).addOnSuccessListener(model -> {
            File modelFile = model.getFile();
            try (Interpreter interpreter = new Interpreter(modelFile)) {
                    Log.d("TEST", String.valueOf(interpreter.getInputTensor(0).dataType()));
                    Log.d("TEST", String.valueOf(interpreter.getInputTensorCount()));
                    Log.d("TEST", String.valueOf(interpreter.getOutputTensor(0).dataType()));
                    Log.d("TEST", String.valueOf(interpreter.getOutputTensorCount()));
                    interpreter.allocateTensors();
                    FloatBuffer input = FloatBuffer.allocate(interpreter.getInputTensor(0).numElements());
                    input.put(2);
                    input.put(94);
                    input.put(76);
                    input.put(18);
                    input.put(66);
                    input.put((float) 31.6);
                    input.put((float) 0.649);
                    input.put(23);

                    FloatBuffer output = FloatBuffer.allocate(interpreter.getOutputTensor(0).numElements());

                    interpreter.run(input, output);

                    Log.d("TEST", output.toString());
                    Log.d("TEST", String.valueOf(output.get(0)));
                    Log.d("TEST", Arrays.toString(output.array()));

                    input.clear();
                    output.clear();
                }
            });
        }
    }
