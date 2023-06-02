package de.fh_zwickau.pti.mobanw.ci_application.model;

import java.util.Date;

// Anforderung:
// Jedes CI muss mindestens einen Titel, eine Geschichte, eine ID, ein Erfassungsdatum,
// eine Sprache, den Ort des Geschehens, und Angaben zum Autor enthalten.
public class CI {
    private int id;
    private String title;
    private String story;
    private Date recordedDate;
    private Language language;
    private String place;
    private String authorName;

    public CI(int id, String title, String story, Date recordedDate, Language language, String place, String authorName) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.recordedDate = recordedDate;
        this.language = language;
        this.place = place;
        this.authorName = authorName;
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

    public String getAuthorName() {
        return authorName;
    }
}
