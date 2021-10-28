/open Keyable.java
/open KeyableMap.java
/open Assessment.java
/open Module.java
/open Student.java
/open Roster.java
Student natasha = new Student("Natasha");
natasha.put(new Module("CS2040").put(new Assessment("Lab1", "B")));
natasha.put(new Module("CS2030").put(new Assessment("PE", "A+")).put(new Assessment("Lab2", "C")));
Student tony = new Student("Tony");
tony.put(new Module("CS1231").put(new Assessment("Test", "A-")));
tony.put(new Module("CS2100").put(new Assessment("Test", "B")).put(new Assessment("Lab1", "F")));
Roster roster = new Roster("AY1920").put(natasha).put(tony);
roster;
roster.get("Tony").flatMap(x -> x.get("CS1231")).flatMap(x -> x.get("Test")).map(Assessment::getGrade);
/exit