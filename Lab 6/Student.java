import java.util.Map;
import java.util.HashMap;

class Student extends KeyableMap<Module> {
    // a student can read zero or more modules
    private final String studentName;
    //private final Map<String,Module> map;

    //Constructor
    public Student(String studentName) {
        super(studentName);
        this.studentName = studentName;
        //this.map = new HashMap<String,Module>();
    }

    /*public Student(String studentName,Map<String,Module> map) {
        this.studentName = studentName;
        this.map = map;
    }
    */

    //put method to store the modules given in a map
    /*Student put(Module newModule) {
        Map<String,Module> tempMap = this.map;
        tempMap.put(newModule.getKey(),newModule);
        Student tempStudent = new Student(this.studentName,tempMap);
        return tempStudent;
    }
    */

    //get method to get the module given the moduleName from the module map
    /*Module get(String moduleName) {
        //return this.map.get(moduleName);
        Modulesuper.get(moduleName);
    }
    */

    @Override
    public Student put(Module module) {
        super.put(module);
        return this;
    }

    //toString
    @Override
    public String toString() {
        return String.format("%s: %s", this.studentName, super.toString());
    }
    /*public String toString() {
        String modules = "";
        // use i as a counter for the last random element
        int i = 1; 
        for (Map.Entry<String,Module> x : this.map.entrySet()) {
            if (i == this.map.size()) {
                modules += x.getValue();
            } else {
                modules += x.getValue() + ", ";
                i++;
            }

        }
        return String.format("%s: {%s}",this.studentName,modules);
    }
    */

}
