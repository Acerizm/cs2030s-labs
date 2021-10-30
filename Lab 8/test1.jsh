/open Logger.java
Logger.<Integer>of(5);
Logger<String> hello = Logger.<String>of("Hello");
try { Logger.<Object>of(hello); }catch (Exception e) { System.out.println(e); }
/exit