package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.medi_app.model.GP;
import com.example.medi_app.model.InsuranceCompany;
import java.util.ArrayList;

public class Contact extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<String> spinnerView = new ArrayList<>();
    private static final int REQUEST_CALL = 1;
    private InsuranceCompany insurance;
    private EditText subject, contents;
    private int spinnerPos;
    private GP gp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        subject                = findViewById(R.id.editTextSubject);
        contents               = findViewById(R.id.editTextContact);
        insurance              = getIntent().getParcelableExtra("Insurance");
        gp                     = getIntent().getParcelableExtra("GP");
        ImageView callBtn      = findViewById(R.id.callContactIv);
        Button sendBtn         = findViewById(R.id.sendContactBtn);
        Spinner spinnerContact = findViewById(R.id.spinnerContact);

        callBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        String gpString = gp.getName();
        spinnerView.add(insurance.getHcName());
        spinnerView.add(gpString);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Contact.this, android.R.layout.simple_list_item_1, spinnerView);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContact.setAdapter(adapter);

        spinnerContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callContactIv:
                if (spinnerPos == 0) {
                    makePhoneCall(insurance.getPhone());
                }
                else {
                    makePhoneCall(gp.getPhone());
                }
                break;
            case R.id.sendContactBtn:
                if (spinnerPos == 0) {
                    sendEmail(insurance.getEmail(), subject.getText().toString(), contents.getText().toString());
                }
                else {
                    sendEmail(gp.getEmail(), subject.getText().toString(), contents.getText().toString());
                }
                break;
        }
    }

    //Creates email with passed params.
    private void sendEmail(String email, String subject, String contents) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, contents);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email application"));
    }

    //Checks permissions and makes phone call with passed number
    private void makePhoneCall(String phone) {
        if (ContextCompat.checkSelfPermission(Contact.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Contact.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else {
            String dial = "tel:" + phone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (spinnerPos == 0) {
                    makePhoneCall(insurance.getPhone());
                } else {
                    makePhoneCall(gp.getPhone());
                }
            } else {
                Toast.makeText(Contact.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}