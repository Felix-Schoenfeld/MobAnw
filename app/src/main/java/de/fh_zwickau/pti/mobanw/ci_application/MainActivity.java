package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Button;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

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

        // Setup - Favoriten
        for (Integer id : CIFavStorage.getFavIdsFromJsonFile(this.getBaseContext())) {
            CIRepository.addCIToFavs(id);
        }

        // Setup - Nutzer CIs
        // TODO: ungetestet
        if (CIRepository.getUserCIList().size() < 1) {
            for (CI ci : UserCIStorage.loadUserCIListFromJsonFile(this.getBaseContext())) {
                CIRepository.addUserCI(ci);
            }
            SetupUtil.ciListDebugPrint(CIRepository.getUserCIList());
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

        // FIXME: TEST
        Button buttonUserCITest = (Button) findViewById(R.id.buttonUserCITest);
        buttonUserCITest.setOnClickListener( v -> {
            Date date = new Date();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    date = formatter.parse("1999-1-1");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            int randomNum = ThreadLocalRandom.current().nextInt(5555, 9999 + 1);
            CI userCI = new CI(randomNum, "USER CI TEST", "story", date, Language.German, "Hier", new Author(1,99, Gender.Unknown,new ArrayList<Language>()));
            CIRepository.addUserCI(userCI);
            UserCIStorage.saveCIRepositoryUserCIListToJsonFile(getApplicationContext());
        });
    }
}