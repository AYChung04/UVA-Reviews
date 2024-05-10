package edu.virginia.sde.reviews;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SearchCourse {

    private String subjectInput;

    private int numberInput;

    private String titleInput;

    private Connection connection;
    private static final String URL = "jdbc:sqlite:reviews.sqlite";
// method still does not add course to the database.
    public void addCourseToDB (SearchAddCourse addCourse) throws SQLException {
        subjectInput = addCourse.getSubjectInput();
        numberInput = addCourse.getNumberInput();
        titleInput = addCourse.getTitleInput();

        String newSubjectInput = subjectInput.toUpperCase();

        DbDriver driver = new DbDriver();
        try{
            driver.connect();
            if (driver.courseExists(newSubjectInput, numberInput, titleInput)) {
                System.out.println("Course already exists in the database.");
            } else {
                driver.addCourseToDatabase(newSubjectInput, numberInput, titleInput);
                System.out.println("Course added successfully.");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                driver.disconnect();
            }catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
