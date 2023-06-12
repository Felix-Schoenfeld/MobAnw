package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import de.fh_zwickau.pti.mobanw.ci_application.model.Author;
import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;
import de.fh_zwickau.pti.mobanw.ci_application.model.Gender;
import de.fh_zwickau.pti.mobanw.ci_application.model.Language;
import de.fh_zwickau.pti.mobanw.ci_application.util.UserCIStorage;

public class CICreationActivity extends AppCompatActivity {

    // TODO: sollte in eigenem Menü definiert werden
    Author userAuthor = new Author(18,81, Gender.Unknown, new ArrayList<>(Arrays.asList(Language.German, Language.English)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicreation);

        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Erstellen)", Toast.LENGTH_SHORT).show();

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener( event -> {
            try {
                CI ci = createCI();
                saveCIsPersistent();
                // TODO: clear data here
                // switch to created ci
                Intent intent = new Intent(CICreationActivity.this, CIDetailActivity.class);
                intent.putExtra("selectedCiId", ci.getId());
                startActivity(intent);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected CI createCI() throws ParseException {

        int id = generateUniqueId();
        String title = ((EditText)findViewById(R.id.editTextTitle)).getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
        Date recordedDate;
        try {
            recordedDate = formatter.parse(((EditText) findViewById(R.id.dateErfassungsdatum)).getText().toString());
        } catch (Exception e) {
            recordedDate = new Date();
        }
        String story = ((TextInputEditText)findViewById(R.id.textInputStory)).getText().toString();
        Language language = Language.Unknown;
        language = Language.valueOf((String)((Spinner)findViewById(R.id.spSprache)).getSelectedItem());
        String place = ((EditText)findViewById(R.id.tbOrtDesGeschehens)).getText().toString();
        Author author = userAuthor;

        CI ci = new CI(id, title, story, recordedDate, language, place, author);

        CIRepository.addUserCI(ci);

        return ci;
    }

    private int generateUniqueId() {
        Random rand = new Random();
        int id = rand.nextInt(1000);

        while (true) {
            boolean exists = false;

            for (CI ci : CIRepository.getGlobalCIList()) {
                if (ci.getId() == id) {
                    exists = true;
                }
            }

            if (exists) {
                id = rand.nextInt(1000);
            } else {
                break;
            }
        }
        return id;
    }

    protected void saveCIsPersistent() {
        UserCIStorage.saveCIRepositoryUserCIListToJsonFile(getApplicationContext());
    }

    protected void deleteCI() {

        int id = 0; // TODO: ci löschung implementierent (nur wenn noch zeit ist)

        CIRepository.removeUserCI(id);
    }

}