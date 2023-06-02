package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;

public class CICreationActivity extends AppCompatActivity {
    ArrayList<CI> globalCIList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicreation);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            globalCIList = (ArrayList<CI>) getIntent().getSerializableExtra("globalCIList");
        }
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Erstellen)", Toast.LENGTH_SHORT).show();
    }
}