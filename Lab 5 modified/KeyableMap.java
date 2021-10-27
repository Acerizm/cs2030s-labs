import java.util.Map;
import java.util.HashMap;

class KeyableMap<T extends Keyable> implements Keyable {
    /*Keyable Map will be immutable for CS2030s
        i am using the same functions over and over again
        but for different types
        so i use generics 
        but why exactly do I need to extend to Keyable for? (Upperbound)

        KeyableMap models an entity that contains a map
        KeyableMap is literally the map
        but KeyableMap will like "implement all the 
        interface methods above the Map Class" (Collection Class)
        so that we can keep returning a new KeyableMap for immutability sake

        but why extend to Keyable?
        because cannot assume V will implement Keyable
        for example: V could be assessment which by right implements keyable
        but since V is a generic type, some random object type could be passed to it
        which does not implement Keyable

        also extends allow the subclasses below it that MUST implement Keyable 
    */

    //same implementation as before
    private final String key;
    private final Map<String,T> map;

    public KeyableMap(String key) {
        this.key = key;
        this.map = new HashMap<String,T>();
    }

    public KeyableMap(String key,Map<String,T> map) {
        this.key = key;
        this.map = map;
    }

    public T get(String key) {
        // this one in the future need use optionals 
        // the value can be null value very hard to test with jshell
        return this.map.get(key);
    }

    public String getKey() {
        return this.key;
    }

    
    //return type is the KeyableMap
    public KeyableMap<? extends Keyable> put(T item) {
        this.map.put(item.getKey(), item);
        return this;
    }

    @Override
    public String toString() {
        String items = "";
        //need a counter 
        int i = 1;
        for (Map.Entry<String,T> x : this.map.entrySet()) {
            if (i == this.map.size()) {
                items += x.getValue();
            } else {
                items += x.getValue() + ", ";
                i++;
            }
        }

        return String.format("{%s}",items);
    }


}
