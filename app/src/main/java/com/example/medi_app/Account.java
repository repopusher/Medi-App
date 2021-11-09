// Tobias Lennon
package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Account extends AppCompatActivity {

    ListView accountLv;
    String[] optionList = {"Details", "Health Insurance"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accountLv = findViewById(R.id.accountListView);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.account_adapter, R.id.accountTextView, optionList);
        accountLv.setAdapter(adapter);

        accountLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });
    }
}