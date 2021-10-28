import java.util.Optional;

class Roster extends KeyableMap<Student> {
    // stores a student in a map via the put method
    // aka need to extend to KeyableMap
    //roster.get("StudentID") will return the Student

    //local variables
    private final String rosterId;

    //constructor
    public Roster(String rosterId) {
        super(rosterId);
        this.rosterId = rosterId;
    }

    //getGrade method
    //getGrade takes in (String studentId, String moduleId, String assessmentId)
    // codecrunch wrong test method for getGrade lol
    public String getGrade(String studentId,String moduleId, String assessmentId) {
        //test what you get here
        // need to check if the stuff is null value
        // will get nullPointer exception
        // 1. Either do the unelegant way (check for null value)
        // 2. Or use Optionals (I will use this for Lab 6)
        // 3. or use Try-Catch 
        // try {
        //     String grade = super.get(studentId).get(moduleId).get(assessmentId).getGrade();
        //     return grade;
        // } catch (Exception ex) {
        //     //catch all the errors
        //     String result = String.format("No such record: %s %s %s",
        //         studentId,moduleId,assessmentId);
        //     return result;
        // }

        // Need to check if this is the correct way to chain methods in Java with optionals 
        //.flatMap returns me the value
        // .map returns me the optional

        // if the inner annonymous func in flatMap returns null, flatMap will return me an Optional
        // else return me the value which I need to chain it with other methods
        String result = String.format("No such record: %s %s %s",
            studentId,moduleId,assessmentId);
        String grade = super.get(studentId)
            .flatMap(x -> x.get(moduleId))
            .flatMap(x -> x.get(assessmentId))
            .map(x -> x.getGrade())
            .orElse(result);
        return grade;
    }

    @Override
    public Roster put(Student student) {
        super.put(student);
        return this;
    }

    //toString
    @Override
    public String toString() {
        return String.format("%s: %s", this.rosterId, super.toString());
    }

}
