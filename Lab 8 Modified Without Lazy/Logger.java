import java.util.Optional;
import java.util.function.Function;

class Logger<T> {
    private final T value;
    private final String outputOfValues;

    //constructor
    //private means that i STILL CAN USE IT within this class
    private Logger(T value) {
        this.value = value;
        outputOfValues = "" + value;
    }

    private Logger( T value, String values) {
        this.value = value;
        outputOfValues = values;
    }

    //alternative constructor
    static <T> Logger<T> of(T value) {
        if (value instanceof Logger) {
            throw new IllegalArgumentException("already a Logger");
        }
        Optional<T> optional = Optional.ofNullable(value);
        optional.ifPresentOrElse(
            x -> {} // do nothing
            ,
            () -> {
                throw new IllegalArgumentException("argument cannot be null");
            }
        );
        return new Logger<T>(value);
    }

    public String get() {
        return this.outputOfValues;
    }

    //Level 2
    <U> Logger<U> map(Function<? super T, ? extends U> mapper) {
        // if (this.outputOfValues == "") {
        //     System.out.println("hello World");
        //     U newValue = mapper.apply(this.value);
        //     String newOutputValues = this.outputOfValues + newValue;
        //     return new Logger<U>(newValue,newOutputValues);
        // } else {
        //     return new Logger<U>(mapper.apply(this.value));
        // }
        //fix this algo here
        T oldValue = this.value;
        U newValue = mapper.apply((this.value));
        String newOutputValues = this.outputOfValues + "->" + newValue + "\n";
        return new Logger<U>(newValue,newOutputValues);
    }

    //toString method
    @Override
    public String toString() {
        return "Logger[" + this.value + "]\n" + this.outputOfValues;
    }
}