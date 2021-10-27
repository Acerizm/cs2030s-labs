class Roster extends KeyableMap<Student>{
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
    public String getGrade() {
        return super.get(this.rosterId);
    }

    //toString
    @Override
    public String toString() {
        return String.format("%s: %s", this.rosterId, super.toString());
    }

}
