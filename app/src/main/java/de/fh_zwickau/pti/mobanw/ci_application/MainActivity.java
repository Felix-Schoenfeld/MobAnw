package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List CIs
        Button buttonCIList = (Button) findViewById(R.id.buttonCIList);
        buttonCIList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CIListActivity.class);
                startActivity(intent);
            }
        });

        // Fav List
        Button buttonFavs = (Button) findViewById(R.id.buttonFavs);
        buttonFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavListActivity.class);
                startActivity(intent);
            }
        });

        // CI Creation
        Button buttonCreateCI = (Button) findViewById(R.id.buttonCreateCI);
        buttonCreateCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CICreationActivity.class);
                startActivity(intent);
            }
        });
    }
}