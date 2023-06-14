package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

import de.fh_zwickau.pti.mobanw.ci_application.model.Author;
import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;
import de.fh_zwickau.pti.mobanw.ci_application.model.Gender;
import de.fh_zwickau.pti.mobanw.ci_application.model.Language;
import de.fh_zwickau.pti.mobanw.ci_application.util.CIFavStorage;
import de.fh_zwickau.pti.mobanw.ci_application.util.SetupUtil;
import de.fh_zwickau.pti.mobanw.ci_application.util.UserCIStorage;

public class MainActivity extends AppCompatActivity {

    /** Liste von allen CIs, wird allen Aktivitäten mitgegeben. */
    private ArrayList<CI> globalCIList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setup - Demo CIs
        if (CIRepository.getGlobalCIList().size() < 1) {
            for (CI ci : SetupUtil.loadCIListFromJson(this.getBaseContext())) {
                CIRepository.addCIGlobally(ci);
            }
            globalCIList = CIRepository.getGlobalCIList();
            SetupUtil.ciListDebugPrint(globalCIList);
        }

        // Setup - Nutzer CIs
        if (CIRepository.getUserCIList().size() < 1) {
            for (CI ci : UserCIStorage.loadUserCIListFromJsonFile(this.getBaseContext())) {
                CIRepository.addUserCI(ci);
            }
            SetupUtil.ciListDebugPrint(CIRepository.getUserCIList());
        }


        // Setup - Favoriten
        for (Integer id : CIFavStorage.getFavIdsFromJsonFile(this.getBaseContext())) {
            // existenz überprüfen, hinzufügen
            if (CIRepository.getCIById(id) != null) CIRepository.addCIToFavs(id);
        }

        if (UserCIStorage.getUserAuthor() == null) {
            // default author falls keiner existiert
            UserCIStorage.setUserAuthor(new Author(18,81, Gender.Unknown, new ArrayList<>(Arrays.asList(Language.German, Language.English))));
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

        // Profil Anpassung
        Button buttonProfile = (Button) findViewById(R.id.bntProfile);
        buttonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AuthorProfileActivity.class);
            startActivity(intent);
        });

    }
}