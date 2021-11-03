/open Logger.java
Logger<Integer> five = Logger.<Integer>of(5);
five.get();
five.map(x -> x + 1);
five.map(x -> x + 1).map(x -> x - 1)
/exit