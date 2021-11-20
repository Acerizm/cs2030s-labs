import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;
class Lazy<T> {
    private final T value;
    private final Supplier<T> supplier;

    private Lazy(T v){
        this.value = v;
        this.supplier = null;
    }

    private Lazy(Supplier<T> supplier) {
        this.value = null;
        this.supplier = supplier;
    }

    static <T> Lazy<T> ofNullable(T v) {
        return new Lazy<T>(v);
    }

    static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<T>(supplier);
    }

    //get method
    //get method is wrong here
    Optional<T> get() {
        if (this.value != null) {
            System.out.println("test");
            Optional<T> tempOptional = Optional.of(value);
            return tempOptional;
        }
        else {
            // trigger the supplier.get() when necessary
            // Qn: When is necessary??
            if(this.supplier == null) {
                return Optional.empty();
            } else {
                Optional<T> tempOptional = Optional.of(this.supplier.get());
                return tempOptional;
            }
        }
    }

    //mapper function
    <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
        //this one also wrong
        return Lazy.<R>of(() -> mapper.apply(get().get()));
    }

    //filter function
    //https://www.geeksforgeeks.org/java-8-predicate-with-examples/
    //https://mkyong.com/java8/java-8-predicate-examples/
    // Predicates will either return true or false depending on the lamda given
    // Need to apply the filter function lazily!
    Lazy<T> filter(Predicate<? super T> predicate) {
        if (predicate.test(this.value)) {
            // System.out.println("case1");
            return Lazy.<T>of(() -> this.value);
        } else {
            //Wrong logic here
            if (this.value == null) {
                 System.out.println("case2");
                return this;
            } else {
                 System.out.println("case3");
                return Lazy.<T>ofNullable(null);
            }
        }
    }

    //toString method
    @Override
    public String toString() {
        if (this.value == null) {
            return "Lazy[?]";
        } else {
            String tempString = "Lazy[" + this.value + "]";
            return tempString;
        }
    }

}
