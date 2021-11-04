import java.util.Optional;
import java.util.function.Function;

class Logger<T> {
    private final T value;
    private String outputOfValues;

    //constructor
    //private means that I STILL CAN USE IT within this class
    private Logger(T value) {
        this.value = value;
        outputOfValues = "";
    }

    private Logger(T value, String values) {
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
            x -> { }, // do nothing      
            () -> {
                throw new IllegalArgumentException("argument cannot be null");
            }
        );
        return new Logger<T>(value);
    }

    public T getValue() {
        return this.value;
    }

    public String getOutputOfValues() {
        return this.outputOfValues;
    }

    //Level 2
    <U> Logger<U> map(Function<? super T, ? extends U> mapper) {
        try {
            //fix this algo here
            // I printed to a string because the string "can store any type".
            // You can also choose to store the outputs into a list but wgt
            T oldValue = this.value;
            U newValue = mapper.apply(this.value);
            String newOutputValues = this.outputOfValues + "\n" + oldValue + " -> " + newValue;
            //I am using the private constructor NOT the static factory method to 
            //construct the Logger :D
            return new Logger<U>(newValue,newOutputValues);
        } catch (Exception ex) {
            throw ex;
        }
        
    }

    //Level 3
    <U> Logger<U> flatMap(Function<? super T,? extends Logger<? extends U>> mapper) { 
        String oldValues = this.outputOfValues;
        Logger<? extends U> newValue = mapper.apply(this.value);
        newValue.outputOfValues = oldValues + newValue.outputOfValues;
        return new Logger<U>(newValue.value,newValue.outputOfValues);          
    }

    //toString method
    @Override
    public String toString() {
        if (this.outputOfValues == "") {
            // Codecrunch not happy if there is a newLine when there 
            // is no output of values xd
            return "Logger[" + this.value + "]";
        } else {
            return "Logger[" + this.value + "]" + this.outputOfValues;
        }
    }
}