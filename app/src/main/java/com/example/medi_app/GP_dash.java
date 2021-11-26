package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.model.GP;
import com.example.medi_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class GP_dash extends AppCompatActivity {

    private RecyclerView rv;
    private DatabaseReference database;
    private RvAdapter mAdapter;
    private ArrayList<User> list;
    private FirebaseAuth mAuth;
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_dash);
        header = findViewById(R.id.GPusername);
        mAuth = FirebaseAuth.getInstance();
        rv = findViewById(R.id.recyclerViewGP);
        database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Patients");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        header.setText("Doctor");
        list = new ArrayList<>();
        mAdapter = new RvAdapter(this, list);
        rv.setAdapter(mAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //If patient's gpid matches with current GP then add to list and display.
                    if (Objects.equals(dataSnapshot.child("gpid").getValue(), mAuth.getUid())) {
                        User patient = dataSnapshot.getValue(User.class);
                        list.add(patient);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GP_dash.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                startActivity(new Intent(GP_dash.this, Index.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}