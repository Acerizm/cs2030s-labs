import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import cs2030.simulator.Simulator;
//import cs2030.simulator.Event;

class Main1 {  
    public static void main(String[] args) {
        // reads a series of distinct arrival times (not necessarily in chronological order) for each customer
        // creates events out of them and adds them to a priority queue
        // the priority queue is then polled so that the earlier arrival event is removed from the queue and output.
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimeList = new ArrayList<Double>();

        //get the number of servers available
        int numOfServers = sc.nextInt();
        while(sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            arrivalTimeList.add(arrivalTime);
        }
        sc.close();
        Simulator s = new Simulator();
        s.simulate(arrivalTimeList, numOfServers);
    }
}
