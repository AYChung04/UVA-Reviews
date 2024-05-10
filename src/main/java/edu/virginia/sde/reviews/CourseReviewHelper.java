package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class CourseReviewHelper {

    private final DbDriver driver = new DbDriver();

    public ObservableList<Review> getReviewsFromCourseID(int courseID) throws SQLException {
        driver.connect();
        List<Review> reviews = driver.getReviewsByCourseId(courseID);
        driver.disconnect();
        return FXCollections.observableList(reviews);
    }

    //checks to see if the user already reviewed the course
    public boolean checkAlreadyReviewed(int userID, int courseID) throws SQLException {
        driver.connect();
        boolean hasReviewed = driver.hasReviewed(userID, courseID);
        driver.disconnect();
        return hasReviewed;
    }
}
