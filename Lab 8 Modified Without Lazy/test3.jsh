/open Logger.java
Logger<Integer> five = Logger.<Integer>of(5); //pass
five.flatMap(x -> Logger.of(x + 1)); //pass
five.flatMap(x -> Logger.of(x).map(y -> y + 2)).flatMap(y -> Logger.of(y).map(z -> z * 10))
Logger.<Integer>of(5).flatMap(x -> Logger.of(x).map(y -> y + 2).flatMap(y -> Logger.of(y).map(z -> z * 10)))
Function<String, Logger<Integer>> g = x -> Logger.<String>of(x).map(y -> y.length())
Logger<Number> lognum = Logger.<String>of("hello").flatMap(g)
/exit   