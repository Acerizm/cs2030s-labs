import java.util.Scanner;

class Main {
    private static final int STUDENT_INDEX = 0;
    private static final int MODULE_INDEX = 1;
    private static final int ASSESSMENT_INDEX = 2;
    private static final int GRADE_INDEX = 3; 

    public static void main(String[] args) {
        //Read the text from standard input
        // need to use either reader class or the scanner class
        // I will use the scanner class
        Scanner scanner = new Scanner(System.in);  

        //get the number of records to read
        int numOfRecords = scanner.nextInt();
        scanner.nextLine();

        //what is the roster here?
        // Question did not specify :0
        // Assume roster is "AY2021" to test 
        Roster roster = new Roster("AY2021");

        //Map to store N records
        // Map<Integer,String> mapOfCurrentRecords1 = new HashMap<Integer,String>();
        // Map<Integer,String> mapOfCurrentRecords2 = new HashMap<Integer,String>();

        //then there are subsequent inputs that take in 4 words
        if (numOfRecords > 0) {

            // then get the grade
            // will use try catch for null values
            // need to change to optionals in the future
            // will rewrite all get methods to use Optionals
            for (int i = 0; i < numOfRecords; i++) {
                String currentRecord = scanner.nextLine();
                // need to split the words that are spaced 
                // can filter by Reg-Ex (Regular Expression)
                // knowledge is abstracted from https://stackoverflow.com/questions/13081527/splitting-string-on-multiple-spaces-in-java
                String[] currentRecordArray = currentRecord.split("\\s+");
                String studentId = currentRecordArray[STUDENT_INDEX];
                String moduleId = currentRecordArray[MODULE_INDEX];
                String assessmentId = currentRecordArray[ASSESSMENT_INDEX];
                String grade = currentRecordArray[GRADE_INDEX];
                //String grade = roster.getGrade(studentId,moduleId,assessmentId);
                //something is wrong here keeps overriding the original array
                // roster needs to add not put .
                try {
                    if (roster.get(studentId) == null) {
                        roster.put(new Student(studentId))
                            .get(studentId).put(new Module(moduleId))
                            .get(moduleId).put(new Assessment(assessmentId, grade));
                    } else {
                        // when studentid exist in the roster already.
                        //then check if the module exists alr
                        if (roster.get(studentId).get(moduleId) == null) {
                            roster.get(studentId)
                                .put(new Module(moduleId)).get(moduleId)
                                .put(new Assessment(assessmentId, grade));
                        } else {
                            //when the moduleId exists alr
                            roster.get(studentId)
                                .get(moduleId)
                                .put(new Assessment(assessmentId, grade));
                        }
                    }
                } catch (Exception error) {
                    throw error;
                }
            }
        }
        
        while (scanner.hasNextLine()) {
            String currentRecordToTest = scanner.nextLine();
            String[] currentRecordArray = currentRecordToTest.split("\\s+");
            String studentId = currentRecordArray[STUDENT_INDEX];
            String moduleId = currentRecordArray[MODULE_INDEX];
            String assessmentId = currentRecordArray[ASSESSMENT_INDEX];
            String grade = roster.getGrade(studentId,moduleId,assessmentId);
            System.out.println(grade);
        }

        // after doing the business logic
        // then close the scanner class because Java
        scanner.close();
    }
}
