package edu.virginia.sde.reviews;

public class CourseSession {
    private static CourseSession instance;

    private int courseId;
    private String subjectMnemonic;
    private int courseNumber;
    private String courseTitle;

    private CourseSession(int courseId, String subjectMnemonic, int courseNumber, String courseTitle) {
        this.courseId = courseId;
        this.subjectMnemonic = subjectMnemonic;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
    }

    public static CourseSession getInstance(int courseId, String subjectMnemonic, int courseNumber, String courseTitle) {
        if (instance == null) {
            instance = new CourseSession(courseId, subjectMnemonic, courseNumber, courseTitle);
        } else {
            instance.courseId = courseId;
            instance.subjectMnemonic = subjectMnemonic;
            instance.courseNumber = courseNumber;
            instance.courseTitle = courseTitle;
        }
        return instance;
    }

    public static CourseSession getInstance() {
        return instance;
    }

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

    public static void clearSession() {
        instance = null;
    }
}
