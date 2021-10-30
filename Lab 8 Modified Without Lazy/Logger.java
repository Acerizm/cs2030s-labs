import java.util.Optional;

class Logger<T> {
    private final T value;

    //constructor
    private Logger(T value) {
        this.value = value;
    }

    //alternative constructor
    static <T> Logger<T> of(T value) {
        if (value instanceof Logger) {
            throw new IllegalArgumentException("already a Logger");
        }
        Optional<T> optional = Optional.ofNullable(value);
        optional.ifPresentOrElse(
            x -> new Logger<T>(x),
            () -> {
                throw new IllegalArgumentException("argument cannot be null");
            }
        );
        return new Logger<T>(value);
    }

    //toString method
    @Override
    public String toString() {
        return "Logger[" + this.value + "]";
    }
}