package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicreation);

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener( event -> {
            try {
                CI ci = createCI(); // TODO: sollte so fehlschlagen, dass der nutzer das mitgeteilt bekommt (toast?)
                if (ci != null) {
                    saveCIsPersistent();

                    // clearing data
                    ((EditText)findViewById(R.id.editTextTitle)).setText("");
                    ((EditText)findViewById(R.id.dateErfassungsdatum)).setText("");
                    ((TextInputEditText)findViewById(R.id.textInputStory)).setText("");
                    ((Spinner)findViewById(R.id.spSprache)).setSelection(0);
                    ((EditText)findViewById(R.id.tbOrtDesGeschehens)).setText("");

                    // switch to created ci
                    Intent intent = new Intent(CICreationActivity.this, CIDetailActivity.class);
                    intent.putExtra("selectedCiId", ci.getId());
                    startActivity(intent);
                }
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
            recordedDate = null;
        }
        if (recordedDate == null) {
            // DATUM FALSCH EINGEGEBEN
            Toast.makeText(this, "Datum bitte eingeben als dd.mm.yyyy", Toast.LENGTH_LONG).show();
            return null;
        }
        String story = ((TextInputEditText)findViewById(R.id.textInputStory)).getText().toString();
        Language language = Language.Unknown;
        try {
            language = Language.valueOf((String)((Spinner)findViewById(R.id.spSprache)).getSelectedItem());
        } catch (Exception e) {
            Log.e("CI Creation", e.getMessage());
        }
        String place = ((EditText)findViewById(R.id.tbOrtDesGeschehens)).getText().toString();
        Author author = UserCIStorage.getUserAuthor();

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
                    break;
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