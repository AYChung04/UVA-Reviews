package edu.virginia.sde.reviews;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class Course {
    private int courseId;
    private String subjectMnemonic;
    private int courseNumber;
    private String courseTitle;

    public Course(int courseId, String subjectMnemonic, int courseNumber, String courseTitle) {
        this.courseId = courseId;
        this.subjectMnemonic = subjectMnemonic;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
    }

    // Getters
    public int getCourseId() {
        return courseId;
    }

    public String getSubjectMnemonic() {
        return subjectMnemonic;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    // Setters
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setSubjectMnemonic(String subjectMnemonic) {
        this.subjectMnemonic = subjectMnemonic;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getAverageRatingAsString() throws SQLException {
        DbDriver driver = new DbDriver();
        driver.connect();
        List<Review> reviews = driver.getReviewsByCourseId(courseId);
        driver.disconnect();

        if (reviews.isEmpty()) {
            return "NA";
        }

        int sum = 0;
        for (Review r : reviews) {
            sum = sum + r.getRating();
        }

        return round((double) sum/reviews.size(), 2).toString();

    }

    public static BigDecimal round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd;
    }

    // To String method for debugging and easy visualization
    @Override
    public String toString() {
        return subjectMnemonic +
                " " + courseNumber +
                " - " + courseTitle;
    }
}
