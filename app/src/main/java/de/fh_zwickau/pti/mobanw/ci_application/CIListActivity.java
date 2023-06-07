package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;

public class CIListActivity extends AppCompatActivity {

    ArrayList<CI> globalCIList;
    ArrayList<CI> filteredCIList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cilist);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            globalCIList = (ArrayList<CI>) getIntent().getSerializableExtra("globalCIList");
        }
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Listen/Suchen)", Toast.LENGTH_SHORT).show();

        filteredCIList = new ArrayList<>(globalCIList);
        viewCIList();;
    }

    protected void filterCIList() {
        // TODO
        filteredCIList = new ArrayList<>(globalCIList);
        viewCIList();
    }

    private void viewCIList() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ciListLinearLayout);
        linearLayout.removeAllViews();
        for (CI ci : filteredCIList) {
            addCIToUI(ci,linearLayout);
        }
    }

    private void addCIToUI(CI ci, LinearLayout linearLayout) {
        TextView textView = new TextView(this);
        int backgroundColor = linearLayout.getBaseline();
        textView.setTextColor(backgroundColor);

        SpannableString titleSpannable = new SpannableString(ci.getTitle());
        titleSpannable.setSpan(new RelativeSizeSpan(1.2f), 0, ci.getTitle().length(), 0); // Set large font size

        SpannableString authorSpannable = new SpannableString(ci.getLanguage().toString());
        authorSpannable.setSpan(new RelativeSizeSpan(0.8f), 0, ci.getLanguage().toString().length(), 0); // Set small font size

        CharSequence combinedText = TextUtils.concat(titleSpannable, "\n", authorSpannable);

        textView.setText(combinedText);
        linearLayout.addView(textView);

        // On click
        textView.setOnClickListener( v -> {
            Intent intent = new Intent(CIListActivity.this, CIDetailActivity.class);
            intent.putExtra("selectedCI", ci);
            startActivity(intent);
        });
    }
}