//Tobias Lennon X Brian Murphy
package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.medi_app.model.Form;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import org.tensorflow.lite.Interpreter;
import java.io.File;
import java.nio.FloatBuffer;

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

        float[] diabetesInput = {patientForm.getPregnancies(), patientForm.getGlucose(), patientForm.getBloodPressure(), patientForm.getSkinThickness(), patientForm.getInsulin(), patientForm.getBmi(), patientForm.getDiabetesPedigreeFunction(), patientForm.getAge()};
        float[] heartDiseaseInput = {patientForm.getZero(), patientForm.getOne(), patientForm.getTwo(), patientForm.getThree(), patientForm.getFour(), patientForm.getFive(), patientForm.getSix(), patientForm.getSeven(), patientForm.getEight(), patientForm.getNine(), patientForm.getTen(), patientForm.getEleven(), patientForm.getTwelve()};
        float[] alzheimerInput = {patientForm.getGender(), patientForm.getHand(), patientForm.getAge(), patientForm.getEduc(), patientForm.getSes(), patientForm.getMmse(), patientForm.getEtiv(), patientForm.getNwbv(), patientForm.getAsf()};

        float[] altInput = {0, 1, 34, 5, 1, 29, 1647, (float) 0.721, (float) 1.066};

        diabetesAi(diabetesInput);
        heartDiseaseAi(heartDiseaseInput);
        alzheimersAi(altInput);

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
                interpreter.close();
                input.clear();
                output.clear();
            } catch (Exception e) {
                Log.d("RESULT", String.valueOf(e));
            }
        });
    }


    }