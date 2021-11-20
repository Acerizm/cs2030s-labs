/open Rand.java
Rand.of(2030).next().map(x -> x - 1).get();
Rand.of(2030).map(x -> x - 1).get();
Rand.of(2030).map(x -> x - 1).next().get();
/exit