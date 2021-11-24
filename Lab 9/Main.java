import java.time.Instant;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;

/** hi.
 * 
 * @author: Ashiqur Rahman
 * @version: CS2030S AY21/22 Semester 1, Lab 9
 */

public class Main {
    /**
     * The program read a sequence of (id, search string) from standard input.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Instant start = Instant.now();
        Scanner sc = new Scanner(System.in);
        ArrayList<CompletableFuture<String>> routes = new ArrayList<>();

        while (sc.hasNext()) {
            BusStop srcId = new BusStop(sc.next());
            String searchString = sc.next();
            routes.add(BusSg.findBusServicesBetween(srcId, searchString)
                .thenCompose(x -> x.description()));
        }
        sc.close();

        CompletableFuture.allOf(routes.toArray(new CompletableFuture<?>[0]));
        routes.stream().map(x -> x.join()).forEach(System.out::println);
        Instant stop = Instant.now();
        System.out.printf("Took %,dms\n", Duration.between(start, stop).toMillis());
    }
}
