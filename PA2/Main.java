import java.util.stream.Stream;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Stream.iterate(10, x -> x * 10)
            .limit(6)
            .map(x -> Main.simulate(2030,x))
            .forEach(x -> System.out.println(x));
    }

    //Generate points in a square region spanning (-1,-1) tp (1,1)
    // Count how many points fall within a unit circle centered around the circle

    static double simulate(int seed,int n) {
        Circle circle = new Circle(new Point(0.0,0.0), 1.0);

        long numPointsInCircle = Stream.iterate(Rand.of(seed), x-> x.next().next())
            .limit(n)
            //.map(x -> x.flatMap(y -> Rand.of(y).map(z -> List.of(getNormalizedValue(y),z))).next()));
            //.map(x -> x.flatMap(y-> Rand.of(y).map(z -> List.of(getNormalizedValue(y),getNormalizedValue(z))).next()).get())
            .map(x -> x.flatMap(y -> Rand.of(y).map(z -> List.of(getNormalizedValue(y),getNormalizedValue(z))).next()).get())
            .filter(x -> circle.contains(new Point(x.get(0), x.get(1))))
            .count();

        return 4.0 * numPointsInCircle / n;
    }

    private static double getNormalizedValue(int value) {
        double lo = -1.0;
        double hi = 1.0;
        return (hi -lo) * value / (Integer.MAX_VALUE - 1) + lo;
    }
}
