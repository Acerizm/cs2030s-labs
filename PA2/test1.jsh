/open Rand.java
Rand.of(2030) instanceof Rand;
Rand.of(2030).get();
Rand.of(2030).next() instanceof Rand;
Rand.of(2030).next().get();
Rand.of(2030).next().next().get();
Rand r = Rand.of(2030);
r.get();
//for some stupid reason my code is mutable in next()?
r.next().get();
r.get();
r.next().next().get();
r.get();
/exit

