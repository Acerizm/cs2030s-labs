import java.util.function.Supplier;
import java.util.Optional;

class Logger<T> {
    //local variables
    // supplier is just a lamda function
    // where the function will only be executed if 
    // you use supplier.get() to exceute the function
    // aka being lazy and save resources
    private final Supplier<T> supplier;
    private Optional<T> cache;

    //priavate constructor
    private Logger(Supplier<T> supplier) {
        this.supplier = supplier;
        this.cache = Optional.<T>empty();
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

    //get method?

    // toString method
    @Override
    public String toString() {
        return "Logger[" + this.get() + "]";
    }

}