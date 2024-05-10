package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchResults {

    // takes in the string of a search gets the results that search in alphabetical order, and stores it in the appropriate private field.
    public ObservableList<Course> getResultsOfSearch(Search search) throws SQLException {
        DbDriver driver = new DbDriver();
        driver.connect();
        List<Course> results = driver.getCourseFromSearch(search);
        ObservableList<Course> returnList = FXCollections.observableList(results);
        driver.disconnect();
        return returnList;
    }


}
