package de.fh_zwickau.pti.mobanw.ci_application.model;

import java.io.Serializable;
import java.util.Date;

// Anforderung:
// Jedes CI muss mindestens einen Titel, eine Geschichte, eine ID, ein Erfassungsdatum,
// eine Sprache, den Ort des Geschehens, und Angaben zum Autor enthalten.
public class CI implements Serializable {
    private final int id;
    private final String title;
    private final String story;
    private final Date recordedDate;
    private final Language language;
    private final String place;
    private final Author author;

    public CI(int id, String title, String story, Date recordedDate, Language language, String place, Author author) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.recordedDate = recordedDate;
        this.language = language;
        this.place = place;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {
        return story;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public Language getLanguage() {
        return language;
    }

    public String getPlace() {
        return place;
    }

    public Author getAuthor() {
        return author;
    }
}
