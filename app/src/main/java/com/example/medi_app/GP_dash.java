package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        header   = findViewById(R.id.GPusername);
        mAuth    = FirebaseAuth.getInstance();
        rv       = findViewById(R.id.recyclerViewGP);
        database = FirebaseDatabase.getInstance("https://medi-check-76263-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Patients");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        mAdapter = new RvAdapter(this, list);
        rv.setAdapter(mAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    if(Objects.equals(dataSnapshot.child("gpid").getValue(), mAuth.getUid())){
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
}