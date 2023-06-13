package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;

public class FavListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        viewCIList();
    }

    private void viewCIList() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ciListLinearLayout);
        linearLayout.removeAllViews();
        for (CI ci : CIRepository.getFavCIList()) {
            addCIToUI(ci,linearLayout);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        viewCIList();
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
            Intent intent = new Intent(FavListActivity.this, CIDetailActivity.class);
            intent.putExtra("selectedCiId", ci.getId());
            startActivity(intent);
        });

    }
}