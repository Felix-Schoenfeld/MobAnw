package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;

public class CIListActivity extends AppCompatActivity {

    ArrayList<CI> globalCIList;
    ArrayList<CI> filteredCIList;
    ArrayList<CI> favoriteCIList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cilist);

        globalCIList = CIRepository.getGlobalCIList();
        favoriteCIList = CIRepository.getFavCIList();
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Listen/Suchen)", Toast.LENGTH_SHORT).show();

        filteredCIList = new ArrayList<>(globalCIList);
        viewCIList();

        Button buttonFilterLanguage = (Button) findViewById(R.id.btFilter);
        Button buttonSort = (Button) findViewById(R.id.btSort);

    }

    // Called by filter UI element
    protected void filterCIList() {
        Spinner langFilterSpinner = (Spinner) findViewById(R.id.filterSpinner);
        filteredCIList = (ArrayList<CI>) CIRepository.getGlobalCIList().clone();
        String languageSelected = ((String) langFilterSpinner.getSelectedItem()).toLowerCase();
        Log.d("Filter", "Selected: "+languageSelected);
        if (languageSelected.equals("any")) {
            viewCIList();
            Log.d("Filter","None removed. Size: "+filteredCIList.size());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filteredCIList.removeIf(c -> !c.getLanguage().toString().toLowerCase().equals(languageSelected));
            }
            Log.d("Filter","CIs removed. Size: "+filteredCIList.size());
            viewCIList();
        }
    }

    // Called by sort UI element
    protected void sortCIList() {
        // TODO lesen der sortierung aus UI und anwenden
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
            intent.putExtra("selectedCiId", ci.getId());
            startActivity(intent);
        });

        findViewById(R.id.btSort).setOnClickListener( v -> {
            buttonOnClickSort();
        });

        findViewById(R.id.btFilter).setOnClickListener( v -> {
            buttonOnClickFilter();
        });
    }

    private void buttonOnClickSort(){
        sortCIList();
    }

    private void buttonOnClickFilter(){
        filterCIList();
    }
}