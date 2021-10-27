class Assessment implements Keyable {
    private final String assessmentType;
    private final String grade;

    //Constructor
    public Assessment(String assessmentType,String grade) {
        this.assessmentType = assessmentType;
        this.grade = grade;
    }

    //Interface methods
    public String getKey() {
        return this.assessmentType;
    }

    public String getGrade() {
        return this.grade;
    }

    //toString
    @Override
    public String toString() {
        return String.format("{%s: %s}",this.assessmentType,this.grade);
    }

}
