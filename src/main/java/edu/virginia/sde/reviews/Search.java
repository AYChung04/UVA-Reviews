package edu.virginia.sde.reviews;

public class Search {
    private String subject;
    private String number;
    private String title;

    public Search() {
        this.subject = "";
        this.number = "";
        this.title = "";
    }

    public Search(String subject, String number, String title) {
        this.subject = subject;
        this.number = number;
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
