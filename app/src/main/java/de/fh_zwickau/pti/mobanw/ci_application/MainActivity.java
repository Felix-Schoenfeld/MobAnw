package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;
import de.fh_zwickau.pti.mobanw.ci_application.util.SetupUtil;

public class MainActivity extends AppCompatActivity {

    /** Liste von allen CIs, wird allen Aktivitäten mitgegeben. */
    private ArrayList<CI> globalCIList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setup
        if (CIRepository.getGlobalCIList().size() < 1) {
            for (CI ci : SetupUtil.loadCIListFromJson(this.getBaseContext())) {
                CIRepository.addCIGlobally(ci);
            }
            globalCIList = CIRepository.getGlobalCIList();
            SetupUtil.ciListDebugPrint(globalCIList);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List CIs
        Button buttonCIList = (Button) findViewById(R.id.buttonCIList);
        buttonCIList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CIListActivity.class);
            startActivity(intent);
        });

        // Fav List
        Button buttonFavs = (Button) findViewById(R.id.buttonFavs);
        buttonFavs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavListActivity.class);
            startActivity(intent);
        });

        // CI Creation
        Button buttonCreateCI = (Button) findViewById(R.id.buttonCreateCI);
        buttonCreateCI.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CICreationActivity.class);
            startActivity(intent);
        });
    }
}