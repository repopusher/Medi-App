//Tobias Lennon X Brian Murphy
package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.medi_app.model.Form;
import com.example.medi_app.model.Prediction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import org.tensorflow.lite.Interpreter;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.Objects;

public class MediPredict extends AppCompatActivity {

    TextView diabetesRes, heartRes, alzheimersRes;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/");
    private FirebaseAuth mAuth;
    private Prediction prediction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_predict);

        diabetesRes = findViewById(R.id.diabetes);
        heartRes = findViewById(R.id.heartDisease);
        alzheimersRes = findViewById(R.id.alzheimers);
        mAuth         = FirebaseAuth.getInstance();
        prediction = new Prediction();


        //PatientForm has all form for AIs, just use .getWhatever()
        Form patientForm = getIntent().getExtras().getParcelable("patientForm");

        float[] diabetesInput = {patientForm.getPregnancies(), patientForm.getGlucose(), patientForm.getBloodPressure(), patientForm.getSkinThickness(), patientForm.getInsulin(), patientForm.getBmi(), patientForm.getDiabetesPedigreeFunction(), patientForm.getAge()};
        float[] heartDiseaseInput = {patientForm.getZero(), patientForm.getOne(), patientForm.getTwo(), patientForm.getThree(), patientForm.getFour(), patientForm.getFive(), patientForm.getSix(), patientForm.getSeven(), patientForm.getEight(), patientForm.getNine(), patientForm.getTen(), patientForm.getEleven(), patientForm.getTwelve()};
        float[] alzheimerInput = {patientForm.getGender(), patientForm.getHand(), patientForm.getAge(), patientForm.getEduc(), patientForm.getSes(), patientForm.getMmse(), patientForm.getEtiv(), patientForm.getNwbv(), patientForm.getAsf()};

        diabetesAi(diabetesInput);
        heartDiseaseAi(heartDiseaseInput);
        alzheimersAi(alzheimerInput);

    }

    public void diabetesAi(float[] userData) {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder().requireWifi().build();
        FirebaseModelDownloader.getInstance().getModel("DiabetesAi", DownloadType.LATEST_MODEL, conditions).addOnSuccessListener(model -> {
            File modelFile = model.getFile();
            try (Interpreter interpreter = new Interpreter(modelFile)) {
                interpreter.allocateTensors();
                FloatBuffer input = FloatBuffer.allocate(interpreter.getInputTensor(0).numElements());
                input.put(userData[0]);
                input.put(userData[1]);
                input.put(userData[2]);
                input.put(userData[3]);
                input.put(userData[4]);
                input.put(userData[5]);
                input.put(userData[6]);
                input.put(userData[7]);
                input.rewind();

                FloatBuffer output = FloatBuffer.allocate(interpreter.getOutputTensor(0).numElements());

                interpreter.run(input, output);

                float[] fb = output.array();
                Log.d("RESULT", String.valueOf(fb[0]));
                Log.d("RESULT", "Diabetes ^");
                prediction.setDiabetes(fb[0]);
                if (fb[0] < 0.5) {
                    diabetesRes.setText("😁");
                }
                else {
                    diabetesRes.setText("😟");
                }
                interpreter.close();
                input.clear();
                output.clear();
            } catch (Exception e) {
                Log.d("RESULT", String.valueOf(e));
            }
        });
    }

        public void heartDiseaseAi(float[] userData) {
            CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder().requireWifi().build();
            FirebaseModelDownloader.getInstance().getModel("HeartDiseaseAi", DownloadType.LATEST_MODEL, conditions).addOnSuccessListener(model -> {
                File modelFile = model.getFile();
                try (Interpreter interpreter = new Interpreter(modelFile)) {
                    interpreter.allocateTensors();
                    FloatBuffer input = FloatBuffer.allocate(interpreter.getInputTensor(0).numElements());
                    input.put(userData[0]);
                    input.put(userData[1]);
                    input.put(userData[2]);
                    input.put(userData[3]);
                    input.put(userData[4]);
                    input.put(userData[5]);
                    input.put(userData[6]);
                    input.put(userData[7]);
                    input.put(userData[8]);
                    input.put(userData[9]);
                    input.put(userData[10]);
                    input.put(userData[11]);
                    input.put(userData[12]);
                    input.rewind();

                    FloatBuffer output = FloatBuffer.allocate(interpreter.getOutputTensor(0).numElements());

                    interpreter.run(input, output);

                    float[] fb = output.array();
                    Log.d("RESULT", String.valueOf(fb[0]));
                    Log.d("RESULT", "Heart Disease ^");
                    prediction.setHeartDisease(fb[0]);
                    if (fb[0] < 0.5) {
                        heartRes.setText("😀");
                    }
                    else {
                        heartRes.setText("😟");
                    }
                    interpreter.close();
                    input.clear();
                    output.clear();
                } catch (Exception e) {
                    Log.d("RESULT", String.valueOf(e));
                }
            });
        }

    public void alzheimersAi(float[] userData) {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder().requireWifi().build();
        FirebaseModelDownloader.getInstance().getModel("AlzhemersAi", DownloadType.LATEST_MODEL, conditions).addOnSuccessListener(model -> {
            File modelFile = model.getFile();
            try (Interpreter interpreter = new Interpreter(modelFile)) {
                interpreter.allocateTensors();
                FloatBuffer input = FloatBuffer.allocate(interpreter.getInputTensor(0).numElements());
                input.put(userData[0]);
                input.put(userData[1]);
                input.put(userData[2]);
                input.put(userData[3]);
                input.put(userData[4]);
                input.put(userData[5]);
                input.put(userData[6]);
                input.put(userData[7]);
                input.put(userData[8]);
                input.rewind();

                FloatBuffer output = FloatBuffer.allocate(interpreter.getOutputTensor(0).numElements());

                interpreter.run(input, output);

                float[] fb = output.array();
                Log.d("RESULT", String.valueOf(fb[0]));
                Log.d("RESULT", "Alzheimer's ^");
                prediction.setAlzheimers(fb[0]);
                if (fb[0] < 0.5) {
                    alzheimersRes.setText("😀");
                }
                else {
                    alzheimersRes.setText("😟");
                }
                database.getReference("Predictions").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(prediction).addOnCompleteListener(task -> {
                    Log.d("TEST", "Stored in database");
                });
                interpreter.close();
                input.clear();
                output.clear();
            } catch (Exception e) {
                Log.d("RESULT", String.valueOf(e));
            }
        });
    }



    }