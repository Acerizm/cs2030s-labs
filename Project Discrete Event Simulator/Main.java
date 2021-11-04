import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        // reads a series of distinct arrival times (not necessarily in chronological order) for each customer
        // creates events out of them and adds them to a priority queue
        // the priority queue is then polled so that the earlier arrival event is removed from the queue and output.
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();

        while (sc.hasNextDouble()) {
            arrivalTimes.add(sc.nextDouble());
        }
        Simulator s = new Simulator(arrivalTimes);
        s.simulate();

    }

}