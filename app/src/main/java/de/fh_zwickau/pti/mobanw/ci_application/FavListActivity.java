package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;

public class FavListActivity extends AppCompatActivity {

    ArrayList<CI> globalCIList;
    ArrayList<CI> favoriteCIList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            globalCIList = (ArrayList<CI>) getIntent().getSerializableExtra("globalCIList");
            favoriteCIList = (ArrayList<CI>) getIntent().getSerializableExtra("favoriteCIList");
        }
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Favoriten)", Toast.LENGTH_SHORT).show();
    }
}