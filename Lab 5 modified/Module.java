//import java.util.Map;
//import java.util.HashMap;

//Module Class javadoc comment?
// I need to pass assessment to KeyableMap
class Module extends KeyableMap<Assessment> {
    //local methods
    private final String moduleName;
    //private final Map<String,Assessment> map;


    //Constructor
    public Module(String moduleName) {
        super(moduleName);
        this.moduleName = moduleName;
        //this.map = new HashMap<String,Assessment>();
    }

    /*
        public Module(String moduleName, Map<String,Assessment> map) {
            this.moduleName = moduleName;
            this.map = map;
        }
    */

    public String getKey() {
        return this.moduleName;
    }

    //get method to get the sepcific assessment given the key
    /*  
    Assessment get(String key) {
        return this.map.get(key);
    }
    */

    //put method to store the assessment to the map!
    /* 
        Module put(Assessment newAssessment) {
        Map<String,Assessment> tempMap = this.map;
        tempMap.put(newAssessment.getKey(),newAssessment);
        Module tempModule = new Module(this.moduleName,tempMap);
        return tempModule;
    }
    */

    @Override
    public Module put(Assessment result) {
        super.put(result);
        return this;
    }
  
    //toString
    @Override
    public String toString() {
        return String.format("%s: %s", this.moduleName, super.toString());
    }
    /*public String toString() {
        String assessments = "";
        this is a foreach loop in java
            O(1) so there is no first and last values all random
            so how am i supposed to remove the "," comma?
            use a counter/tracker i guess
            C wont agree to this xd
         
        int i = 1;
        for (Map.Entry<String,Assessment> x: this.map.entrySet()) {
            if (i == this.map.size()) {
                assessments += x.getValue();
            } else {
                assessments += x.getValue() + ", ";
                i++;
            }
        }
        return String.format("%s: {%s}",this.moduleName,assessments);
    }
    */
}
