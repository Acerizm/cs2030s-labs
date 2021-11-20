import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main1 {  
    public static void main(String[] args) {
        // reads a series of distinct arrival times (not necessarily in chronological order) for each customer
        // creates events out of them and adds them to a priority queue
        // the priority queue is then polled so that the earlier arrival event is removed from the queue and output.
        Scanner sc = new Scanner(System.in);
        // This list will store a list of events
        List<Event> eventList = new ArrayList<Event>();
        //This list will store a list of servers
        List<Server> serverList = new ArrayList<Server>();

        //get the number of servers available
        int numOfServers = sc.nextInt();
        for(int j = 0; j < numOfServers; j++) {
            Server tempServer = new Server(j+1);
            serverList.add(tempServer);
        }

        //get the number of customers
        int i = 1;
        while (sc.hasNextDouble()) {
            // Create a new customer
            double arrivalTime = sc.nextDouble();
            Customer currentCustomer = new Customer(i,arrivalTime);

            // Add the customer to the arrive event
            // should I do it here???
            Event tempEvent = new ArriveEvent(arrivalTime,currentCustomer);
            eventList.add(tempEvent);
            //customerList.add(currentCustomer);
            i++;
        }
        sc.close();
        Simulator s = new Simulator(eventList,serverList);
        s.simulate();
    }
}
