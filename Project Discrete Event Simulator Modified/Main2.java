import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import cs2030.simulator.Simulator;

public class Main2 {
    public static void main(String[] args) {
        // Each customer has its own service time
        // Each server has a queue of customers to allow multiple customers to queue up
        // A customer that chooses to join a queue joins at the tail.

        // local variables
        int numOfServers = 0;
        int maxQueueLength = 0;
        List<Double> arrivalTimeList = new ArrayList<Double>();
        List<Double> serviceTimeList = new ArrayList<Double>();

        Scanner sc = new Scanner(System.in);
        numOfServers = sc.nextInt();
        maxQueueLength = sc.nextInt();

        //for each customer
        // Qn: Do i still need a customer object for level 2?
        // Ans: Probably need
        while(sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            arrivalTimeList.add(arrivalTime);
            double serviceTime = sc.nextDouble();
            serviceTimeList.add(serviceTime);
        } 
        
        Simulator s = new Simulator();
        s.simulate(numOfServers,maxQueueLength,arrivalTimeList,serviceTimeList);
        sc.close();

        // Since i can't do a builder pattern here
        // im forced to pass in garbage to the Simulator clas
        // or refactor whole code :D
    }
}
