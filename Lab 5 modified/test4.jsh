/open Keyable.java
/open KeyableMap.java
/open Assessment.java
/open Module.java
/open Student.java
new Module("CS2040").put(new Assessment("Lab1", "B")).get("Lab1"); //pass
new Module("CS2040").put(new Assessment("Lab1", "B")).get("Lab1").getGrade(); //pass
new Student("Tony").put(new Module("CS2040").put(new Assessment("Lab1", "B"))); //pass 
new Student("Tony").put(new Module("CS2040").put(new Assessment("Lab1", "B"))).get("CS2040"); //pass
Student natasha = new Student("Natasha"); //pass
natasha.put(new Module("CS2040").put(new Assessment("Lab1", "B"))); //pass
natasha.put(new Module("CS2030").put(new Assessment("PE", "A+")).put(new Assessment("Lab2", "C"))); //pass
Student tony = new Student("Tony"); //pass
tony.put(new Module("CS1231").put(new Assessment("Test", "A-"))); //pass
new Module("CS1231").put(new Assessment("Test", "A-")) instanceof KeyableMap //pass
new Student("Tony").put(new Module("CS1231")) instanceof KeyableMap; //pass
/exit