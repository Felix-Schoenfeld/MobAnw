package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;

public class CIListActivity extends AppCompatActivity {

    ArrayList<CI> globalCIList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cilist);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            globalCIList = (ArrayList<CI>) getIntent().getSerializableExtra("globalCIList");
        }
        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Listen/Suchen)", Toast.LENGTH_SHORT).show();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ciListLinearLayout);
        linearLayout.removeAllViews();
        for (CI ci : globalCIList) {
            addCIToUI(ci,linearLayout);
        }
    }

    private void addCIToUI(CI ci, LinearLayout linearLayout) {
        TextView textView = new TextView(this);
        int backgroundColor = linearLayout.getBaseline();
        textView.setTextColor(backgroundColor);
        // Create a SpannableString for the title
        SpannableString titleSpannable = new SpannableString(ci.getTitle());
        titleSpannable.setSpan(new RelativeSizeSpan(1.2f), 0, ci.getTitle().length(), 0); // Set large font size

        // Create a SpannableString for the author
        SpannableString authorSpannable = new SpannableString(ci.getTitle().toString());
        authorSpannable.setSpan(new RelativeSizeSpan(0.8f), 0, ci.getTitle().toString().length(), 0); // Set small font size

        // Create a CharSequence to hold the combined text
        CharSequence combinedText = TextUtils.concat(titleSpannable, "\n", authorSpannable);

        textView.setText(combinedText);
        linearLayout.addView(textView);
    }
}