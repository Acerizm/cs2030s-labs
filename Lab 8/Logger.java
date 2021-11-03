import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Optional;

class Logger<T> {
    //local variables
    // supplier is just a lamda function
    // where the function will only be executed if 
    // you use supplier.get() to exceute the function
    // aka being lazy and save resources

    //Qn: Do we need to really cache here?
    // Or we can save the previous values into an array or String
    // so as to satisfy the map function method signature below
    private final Supplier<T> supplier;
    private Optional<T> cache;

    //priavate constructor
    private Logger(Supplier<T> supplier) {
        this.supplier = supplier;
        this.cache = Optional.<T>empty();
    }

    private Logger(Supplier<T> supplier, T cache) {
        this.supplier = supplier;
        this.cache = Optional.ofNullable(cache);
    }

    // alternative constructor using generics
    // for the purpose of readability and
    // client wont be able to see our constructor
    static <T> Logger<T> of(Supplier<T> supplier) {
        Logger<T> tempLogger = new Logger<T>(Optional.<Supplier<T>>ofNullable(supplier)
            .orElseThrow(() -> {
                throw new IllegalArgumentException("argument cannot be null");
            })
        );
        return tempLogger;
    }

    //need to store the cache when creating a new logger
    // because we need to print the previous value;
    // and this lab the test case cant use get() method feelsbad
    static <T> Logger<T> of(Supplier<T> supplier, T cache) {
        //need to store the previous value into the cache
        Logger<T> tempLogger = new Logger<T>(Optional.<Supplier<T>>ofNullable(supplier)
            .orElseThrow(() -> {
                throw new IllegalArgumentException("argument cannot be null");
            }),
            cache
        );
        return tempLogger;
    }

    static <T> Logger<T> of(T cache) {
        if (cache instanceof Logger) {
            throw new IllegalArgumentException("already a Logger");
        }
        return new Logger<T>(() -> cache);
    }

    T get() {
        // if the cache is empty (1st execution),
        // trigger the supplier;
        T value = this.cache.orElseGet(this.supplier);
        this.cache = Optional.<T>of(value);
        return value;
    }

    //map method
    // Function takes in two arguements
    // T refers to type of input arguement
    // U refers to the return type of the function
    // .apply will return type of U
    // this.get() is of type T and java will infer it as U
    <U> Logger<U> map(Function<? super T,? extends U> mapper) {
        return Logger.<U>of(
            () -> mapper.apply(this.get())
        );
    }

    // toString method
    @Override   
    public String toString() {
        //include the output of the value changes over all map operations.
        //this.cache.ifPresent(x -> System.out.println(x));
        // String previousValue = "" + this.cache;
        // System.out.println(previousValue);
         String string = "Logger[" + this.supplier.get() + "]";
        System.out.println(this.cache);
        return string;
    }

}