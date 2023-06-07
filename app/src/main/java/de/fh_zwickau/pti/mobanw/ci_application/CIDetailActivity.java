package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;

public class CIDetailActivity extends AppCompatActivity {

    CI ci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ci = (CI) getIntent().getSerializableExtra("selectedCI");
        }
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Detail)", Toast.LENGTH_SHORT).show();

        TextView titleText = (TextView) findViewById(R.id.titleText);
        TextView authorText = (TextView) findViewById(R.id.tvAutor);
        TextView dateText = (TextView) findViewById(R.id.tvDatum);
        TextView locationText = (TextView) findViewById(R.id.tvOrt);
        TextView languageText = (TextView) findViewById(R.id.tvSprache);

        titleText.setText(ci.getTitle());

        dateText.setText(ci.getRecordedDate().toString());
        locationText.setText(ci.getPlace());
        languageText.setText(ci.getLanguage().toString());
        authorText.setText(ci.getAuthor().toString());

    }
}