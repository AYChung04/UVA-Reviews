package edu.virginia.sde.reviews;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Review {
    private final SimpleIntegerProperty rating;
    private final SimpleStringProperty timestamp;
    private final SimpleStringProperty comment;
    private final Course course;

    public Review(int rating, String timestamp, String comment) {
        this.rating = new SimpleIntegerProperty(rating);
        this.timestamp = new SimpleStringProperty(timestamp);
        this.comment = new SimpleStringProperty(comment);
        this.course = null;
    }

    public Review(int rating, String timestamp, String comment, Course course) {
        this.rating = new SimpleIntegerProperty(rating);
        this.timestamp = new SimpleStringProperty(timestamp);
        this.comment = new SimpleStringProperty(comment);
        this.course = course;
    }

    public int getRating() {
        return rating.get();
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public String getComment() {
        return comment.get();
    }

    public Course getCourse() {
        return course;
    }

    public SimpleIntegerProperty ratingProperty() {
        return rating;
    }

    public SimpleStringProperty timestampProperty() {
        return timestamp;
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating.get() +
                ", timestamp='" + timestamp.get() + '\'' +
                ", comment='" + comment.get() + '\'' +
                ", courseInfo='" + course;
    }
}




