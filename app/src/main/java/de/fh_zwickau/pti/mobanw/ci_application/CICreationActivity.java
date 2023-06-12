package de.fh_zwickau.pti.mobanw.ci_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import de.fh_zwickau.pti.mobanw.ci_application.model.CI;
import de.fh_zwickau.pti.mobanw.ci_application.model.CIRepository;
import de.fh_zwickau.pti.mobanw.ci_application.util.UserCIStorage;

public class CICreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicreation);
        Bundle extras = getIntent().getExtras();

        Toast.makeText(getApplicationContext(), "Hallo, Welt! (Erstellen)", Toast.LENGTH_SHORT).show();
    }

    // TODO: UI interaktion

    protected void createCI() {

        CI ci = null; // TODO: ci erstellung implementierent

        CIRepository.addUserCI(ci);
    }

    protected void saveCIPersistent() {
        UserCIStorage.saveCIRepositoryUserCIListToJsonFile(getApplicationContext());
    }

    protected void deleteCI() {

        int id = 0; // TODO: ci löschung implementierent (nur wenn noch zeit ist)

        CIRepository.removeUserCI(id);
    }

}