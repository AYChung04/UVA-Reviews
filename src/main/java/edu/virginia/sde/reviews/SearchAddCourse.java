package edu.virginia.sde.reviews;

public class SearchAddCourse {

    private String subjectInput;

    private int numberInput;

    private String titleInput;

    public SearchAddCourse(String subjectInput, int numberInput, String titleInput){
        this.subjectInput = subjectInput;
        this.numberInput = numberInput;
        this.titleInput = titleInput;
    }


    public String getSubjectInput() {
        return subjectInput;
    }

    public void setSubjectInput(String subjectInput) {
        this.subjectInput = subjectInput;
    }

    public int getNumberInput() {
        return numberInput;
    }

    public void setNumberInput(int numberInput) {
        this.numberInput = numberInput;
    }

    public String getTitleInput() {
        return titleInput;
    }

    public void setTitleInput(String titleInput) {
        this.titleInput = titleInput;
    }
}
