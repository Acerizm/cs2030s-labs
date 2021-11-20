import java.util.Random;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
class Rand<T> {
    //Store the random object i guess
    private final int seed;
    private final Function<Integer,T> f;

    //private constructor
    private Rand(int seed, Function<Integer,T> f) {
        this.seed = seed;
        this.f = f;
    }

    //takes in a seed and creates a random object
    // no need to generate a random variable here
    // this is a static factory method
    static Rand<Integer> of(int seed) {
        return new Rand<Integer>(seed, x -> x);
    }


    //get method
    // this method will get the random number from
    // the random object
    T get() {
        return f.apply(this.seed);
    }

    Rand<T> next() {
        //use the new seed value as the seed to generate the next value
        int currentNumber = new Random(this.seed).nextInt(Integer.MAX_VALUE);
        return new Rand<>(currentNumber,f);
    }

    // Stream method to generate a stream of random numbers
    Stream<T> stream() {
        // // Qn: How to generate a stream
        // //Random tempRandom = new Random(this.seed).ints()
        // Stream<Integer> tempStream = Stream.iterate(
        //     this.seed, 
        //     //currentSeed -> new Random(currentSeed).nextInt(Integer.MAX_VALUE)
        //     x -> Rand.of(x).next().seed).map(x -> f.apply(x);
        return Stream.iterate(seed, x-> Rand.of(x).next().seed).map(x ->f.apply(x));
    }

    //rand Range method
    static <T> Stream<T> randRange(int seed, Function<Integer,T> f) {
        return Rand.of(seed).stream().map(f);
    }

    // Generics is used here
    // Lazy evaluation is used here
    // Supplier no need
    <R> Rand<R> map(Function<T,R> mapper) {
        return new Rand<>(seed, mapper.compose(f));
    }

    // Level 4
    // Must lazily evaluate flatMap
    <R> Rand<R> flatMap(Function<T, Rand<R>> mapper) {
        Function<Rand<R>,R> unwrapper = x -> x.get();
        return map(unwrapper.compose(mapper)); 
        // return map(mapper.andThen(x -> x..get()));
        // mapper: T -> Rand<R>
        // unwrapper: Rand<R> -> R
        // unwrapper.compose(mapper): T -> Rand,R> -> R
    }

    //toString method
    @Override
    public String toString() {
        return "Rand";
        //return ""+this;
    }
}
