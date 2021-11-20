/open Lazy.java
Lazy.ofNullable(4);
Lazy.ofNullable(4).get();
Lazy.ofNullable(4).map(x -> x + 4);
Lazy.ofNullable(4).filter(x -> x > 2);
Lazy.ofNullable(4).map(x -> 1).get();
Lazy.ofNullable(4).filter(x -> true).get();
Lazy.ofNullable(4).filter(x -> false).get();
Lazy.ofNullable(4).map(x -> 1).filter(x -> false).get() //wrong
/exit
