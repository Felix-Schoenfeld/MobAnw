package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;

public class CIDetailActivity extends AppCompatActivity {

    int selectedCiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedCiId = getIntent().getIntExtra("selectedCiId",0);
        }
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Detail)", Toast.LENGTH_SHORT).show();

        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView authorText = (TextView) findViewById(R.id.tvAutor);
        TextView dateText = (TextView) findViewById(R.id.tvDatum);
        TextView placeText = (TextView) findViewById(R.id.tvOrt);
        TextView languageText = (TextView) findViewById(R.id.tvSprache);
        TextView storyText = (TextView) findViewById(R.id.mltStory);

        CheckBox checkBoxFav = (CheckBox) findViewById(R.id.checkBoxFav);

        CI ci = CIRepository.getCIById(selectedCiId);

        titleText.setText(ci.getTitle());
        authorText.setText(ci.getAuthor().toString());
        dateText.setText(ci.getRecordedDate().toString());
        placeText.setText(ci.getPlace());
        languageText.setText(ci.getLanguage().toString());
        storyText.setText(ci.getStory());
        storyText.setFocusable(false);


        if (CIRepository.isFavorite(selectedCiId))
            checkBoxFav.setChecked(true);
        else
            checkBoxFav.setChecked(false);

        checkBoxFav.setOnCheckedChangeListener((a,b) -> {
            Log.d("CI Fav Button", "Pressed!");
            if (checkBoxFav.isChecked() && !(CIRepository.isFavorite(selectedCiId))) {
                CIRepository.addCIToFavs(selectedCiId);
                Log.d("CI Fav Button","CI added");
            } else if (!(checkBoxFav.isChecked()) && CIRepository.isFavorite(selectedCiId)) {
                CIRepository.removeCIFromFavs(selectedCiId);
                Log.d("CI Fav Button","CI removed");
            }
        });

    }
}