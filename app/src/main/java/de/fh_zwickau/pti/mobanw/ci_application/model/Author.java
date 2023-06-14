package de.fh_zwickau.pti.mobanw.ci_application.model;

import android.os.Build;

import java.io.Serializable;
import java.util.ArrayList;

public class Author implements Serializable {
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    private int minAge;
    private int maxAge;
    private Gender gender;
    private ArrayList<Language> languages;

    public Author(int minAge, int maxAge, Gender gender, ArrayList<Language> languages) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.gender = gender;
        this.languages = languages;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Gender getGender() {
        return gender;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        String s = "";
        s += gender.toString() + " ";
        if (minAge != 0 || maxAge != 0) {
            s += "("+minAge+"-"+maxAge+") ";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            s += languages.stream().count() + " languages";
        }
        return s;
    }
}
