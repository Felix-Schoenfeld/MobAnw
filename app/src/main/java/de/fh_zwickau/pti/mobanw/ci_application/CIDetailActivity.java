package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;
import de.fh_zwickau.pti.mobanw.ci_application.util.CIFavStorage;
import de.fh_zwickau.pti.mobanw.ci_application.util.UserCIStorage;

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


        checkBoxFav.setChecked(CIRepository.isFavorite(selectedCiId));

        checkBoxFav.setOnCheckedChangeListener((a,b) -> {
            Log.d("CI Fav Button", "Pressed!");
            if (checkBoxFav.isChecked() && !(CIRepository.isFavorite(selectedCiId))) {
                CIRepository.addCIToFavs(selectedCiId);
                Log.d("CI Fav Button","CI added");
            } else if (!(checkBoxFav.isChecked()) && CIRepository.isFavorite(selectedCiId)) {
                CIRepository.removeCIFromFavs(selectedCiId);
                Log.d("CI Fav Button","CI removed");
            }
            CIFavStorage.saveCIRepositoryFavListToJsonFile(getApplicationContext());
        });

        Button btnDeleteCI = (Button)findViewById(R.id.btnDeleteCi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (CIRepository.getUserCIList().stream().filter(c -> c.getId() == ci.getId()).count() > 0) {
                btnDeleteCI.setEnabled(true);
            } else {
                btnDeleteCI.setEnabled(false);
            }
        }
        btnDeleteCI.setOnClickListener(event -> {
            CIRepository.removeCIGlobally(ci.getId());
            UserCIStorage.saveCIRepositoryUserCIListToJsonFile(getApplicationContext());
            finish();
        });


    }
}
