package cs2030.simulator;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulator {
     
    public Simulator() { }

    // refactored code for common usage ///////////////////////////////////
    List<Server> createServerList(int numOfServers) {
        List<Server> tempServerList = new ArrayList<Server>();
        //get the number of servers available
        for(int n = 0; n < numOfServers; n++) {
            Server tempServer = new Server(n+1);
            tempServerList.add(tempServer);
        }
        return tempServerList;
    }

    PriorityQueue createPriorityQueue(List<Double> arrivalTimeList) {
        List<Event> tempEventList = new ArrayList<Event>();
        int m = 0;
        while (m < arrivalTimeList.size()) {
            // Create a new customer
            // double arrivalTime = sc.nextDouble();
            //Customer currentCustomer = new Customer(i,arrivalTime);
            //Event tempEvent = new ArriveEvent(i,arrivalTime);
            Event tempEvent = new Event(m + 1, arrivalTimeList.get(m), EventEnumState.ArriveEvent);          
            tempEventList.add(tempEvent);
            m++;
        }

        // Assign a new priority queue
        PriorityQueue queue = new PriorityQueue(tempEventList);
        return queue;
    }

    // Create a hash of the customer service time
    HashMap<Integer,Double> createCustomerHashMap(List<Double> serviceTimeList) {
        HashMap<Integer,Double> tempHashMap = new HashMap<Integer,Double>();
        int i = 0;
        while (i < serviceTimeList.size()) {
            tempHashMap.put(i+1, serviceTimeList.get(i));
            i++;
        }
        return tempHashMap;  
    }

    void removeServer(List<Server> list, int serverId) {
        for(int i = 0; i < list.size(); i++) {
            Server currentServer = list.get(i);
            if(currentServer.getId() == serverId) {
                list.remove(i);
                break;
            }
            else {
                continue;
            }
        }
    }

    boolean IsServerResting(List<Server> restingList, int serverId) {
        if (restingList.isEmpty()) {
            return false;
        } else {
            for(int i = 0; i < restingList.size(); i++) {
                Server currentServer = restingList.get(i);
                if(currentServer.getId() == serverId) {
                    return true;
                } else {
                    continue;
                }
            }
            return false;
        }
    }


    ///////////////////////////////////////////////////////////////////////

    // Level 1
    public void simulate(List<Double> arrivalTimeList, int numOfServers) {
        // local variables to keep track of 
        // 1) Average waiting time
        // 2) No. of customers served ( cannot be those who left without any server )
        //      when a customer leaves without server, we minus 1 :D
        // 3) number of customers left without being served
        PriorityQueue queue = this.createPriorityQueue(arrivalTimeList);
        List<Server> serverList = this.createServerList(numOfServers);
        double totalWaitingTime = 0;
        int numOfCustomers = queue.get().size();
        int numOfCustomersLeft = 0;

        while(!queue.get().isEmpty()) {
            Event tempEvent = queue.poll();
            System.out.println(tempEvent);

            // Get the current customer Id
            int currentCustomerId = tempEvent.getCustomerId();
            double currentEventTime = tempEvent.getTime();

            if(tempEvent.getState() ==  EventEnumState.ArriveEvent) {
                //ArriveEvent currentEvent = (ArriveEvent)tempEvent;
                Event currentEvent = tempEvent;
                boolean customerHasServer = false;
                // check if there are available servers to serve the current customer
                //need to modify code here
                // allow customer to get another avaialable server if the other server has no customer
                for(int j = 0; j < serverList.size(); j++) {
                    Server currentServer = serverList.get(j);
                    if(!currentServer.isServingAnotherCustomer()) {
                        customerHasServer = true;
                        Server updatedServer = new Server(j+1,currentCustomerId,currentEventTime);
                        serverList.set(j, updatedServer);
                        // Event from Arrive -> Serve
                        //Event newEvent = new ServeEvent(currentCustomerId, updatedServer.getNextAvailableTime(), j+1);
                        Event newEvent = new Event(currentCustomerId, updatedServer.getNextAvailableTime(), j+1,EventEnumState.ServeEvent);
                        queue.add(newEvent);
                        break;
                    } else {
                        continue;
                    }
                } 
                // What to do once the customer has found a server
                // Example: Customer found Server 3 able to serve him/her?
                if(customerHasServer == false) {
                    for(int i = 0; i < serverList.size(); i++) {
                        Server currentServer = serverList.get(i);
                        if(currentServer.isServingAnotherCustomer()){
                            if(currentServer.hasWaitingCustomer()) {
                                // when the server is extremly busy
                                // is currently serving a customer and has a waiting customer
                                continue;
                            } else {
                                // server is currently serving another customer
                                // but has no customer waiting in queue
                                customerHasServer = true;
                                //Server updatedServer = new Server(i+1,currentServer.getServingCustomerId(),currentCustomerId,currentServer.getNextAvailableTime());
                                Server updatedServer = currentServer.addMoreWaitingCustomer(currentCustomerId);
                                serverList.set(i, updatedServer);
                                // Event from Arrive -> Wait
                                //double newEventTime = currentEvent.getTime() + currentEvent.getServingTime();
                                // Bug
                                // What is the time for the new Event?
                                //Event newEvent = new WaitEvent(currentCustomerId, currentEvent.getTime(), i+1);
                                Event newEvent = new Event(currentCustomerId, currentEvent.getTime(), i+1,EventEnumState.WaitEvent);
                                queue.add(newEvent);
                                break;
                            }
    
                        } else if(!currentServer.haveTwoCustomers()) {
                            // when the server is free!
                            // meaning no waiting customer and not serving any customer
                            customerHasServer = true;
                            Server updatedServer = new Server(i+1,currentCustomerId,currentEventTime);
                            serverList.set(i, updatedServer);
                            // Event from Arrive -> Serve
                            //Event newEvent = new ServeEvent(currentCustomerId, updatedServer.getNextAvailableTime(), i+1);
                            Event newEvent = new Event(currentCustomerId, updatedServer.getNextAvailableTime(), i+1,EventEnumState.ServeEvent);
                            queue.add(newEvent);
                            break;
                        }
                    } // end of for loop
    
                    //Check if the customer does not have a server
                    // Event from Arrive -> Leave
                    if(customerHasServer == false) {
                        // Event from Arrive -> Leave
                        // Pass in 0 because there is no server
                        //Event newEvent = new LeaveEvent(currentCustomerId, currentEventTime, 0);
                        Event newEvent = new Event(currentCustomerId, currentEventTime, 0,EventEnumState.LeaveEvent);
                        queue.add(newEvent);
                        // add to the stats
                        numOfCustomersLeft = numOfCustomersLeft + 1;
                        numOfCustomers = numOfCustomers - 1;
                    }
                }          
            } else if(tempEvent.getState() == EventEnumState.ServeEvent) {
                //ServeEvent currentEvent = (ServeEvent)tempEvent;
                Event currentEvent = tempEvent;
                // Serve the customer immediately
                // Change State from Serve -> Done
                // Qn: Remove the currentCustomer from the server?
                // Ans: no
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                double afterServeTime = currentEventTime + currentEvent.getServingTime();
                //Server updatedServer = new Server(currentEvent.getServerId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerId(), afterServeTime);
                Server updatedServer = new Server(currentEvent.getServerId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerList(), afterServeTime);
                serverList.set(currentEvent.getServerId() - 1, updatedServer);
                //Event newEvent = new DoneEvent(currentCustomerId, afterServeTime, currentEvent.getServerId());
                Event newEvent = new Event(currentCustomerId, afterServeTime, currentEvent.getServerId(),EventEnumState.DoneEvent);
                queue.add(newEvent);
            } else if(tempEvent.getState() == EventEnumState.DoneEvent) {
                //DoneEvent currentEvent = (DoneEvent)tempEvent;
                Event currentEvent = tempEvent;
                // Remove the customer from the server
                // Qn: Push the waiting customer to the currentServingCustomer?
                // Ans: Maybe yes
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                double doneTime = currentEventTime;
                // int waitingCustomerId = currentServer.getWaitingCustomerId();
                // Server updatedServer = new Server(currentServer.getId(), waitingCustomerId, 0, doneTime);
                // just for level 1
                int waitingCustomerId;
                if(currentServer.getWaitingCustomerList().isEmpty()) {
                    waitingCustomerId = 0;
                } else {
                    waitingCustomerId = currentServer.getWaitingCustomerList().get(0);
                }
                //int waitingCustomerId = currentServer.getWaitingCustomerList().get(0);
                Server updatedServer = new Server(currentServer.getId(),waitingCustomerId,doneTime);
                serverList.set(currentServer.getId() - 1, updatedServer);
                // No need to do anything else since the event has already popped out from the queue;
                // Qn: Are you sure?
                // need to change the waiting state for the customer
                // if there is a waiting customer
                for(int i = 0; i < queue.get().size(); i++) {
                    Event findWaitEvent = queue.get().get(i);
                    if (findWaitEvent.getState() ==  EventEnumState.WaitEvent && findWaitEvent.getCustomerId() == waitingCustomerId) {
                        //Event newEvent = new ServeEvent(waitingCustomerId, findWaitEvent.getTime(), findWaitEvent.getServerId());
                        Event newEvent = new Event(waitingCustomerId, findWaitEvent.getTime(), findWaitEvent.getServerId(),EventEnumState.ServeEvent);
                        queue.get().set(i,newEvent);
                    } else {
                        continue;
                    }
                }
            } else if(tempEvent.getState() == EventEnumState.WaitEvent) {
                //harderst part of this project
                //WaitEvent currentEvent = (WaitEvent)tempEvent;
                Event currentEvent = tempEvent;
                // Check if the server can serve the customer now
                // if the currentServingid has been changed from the DoneState
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                if(currentServer.getServingCustomerId() == currentCustomerId) {
                    // Wait -> Serve
                    //Server updatedServer = new Server(currentServer.getId(), currentCustomerId, currentServer.getServingCustomerId(), currentServer.getNextAvailableTime());
                    Server updatedServer = currentServer.serveEarliestWaitingCustomer(currentCustomerId);
                    serverList.set(currentServer.getId() - 1, updatedServer);
                    double servingTime = updatedServer.getNextAvailableTime();
                    //Event newEvent = new ServeEvent(currentCustomerId, servingTime,currentServer.getId());
                    Event newEvent = new Event(currentCustomerId, servingTime,currentServer.getId(),EventEnumState.ServeEvent);
                    queue.add(newEvent);
                    // add to the stats
                    totalWaitingTime = totalWaitingTime + servingTime - currentEvent.getTime();
                } else {
                    // need to update the time
                    //Server updatedServer = new Server(currentServer.getId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerId(), currentServer.getNextAvailableTime());
                    Server updatedServer = currentServer.updateTimeOnly(currentServer.getNextAvailableTime());
                    serverList.set(currentServer.getId() - 1, updatedServer);
                    double waitingTime = updatedServer.getNextAvailableTime();
                    //Event newEvent = new WaitEvent(currentCustomerId, waitingTime,currentServer.getId());
                    Event newEvent = new Event(currentCustomerId, waitingTime,currentServer.getId(),EventEnumState.WaitEvent);
                    queue.add(newEvent);
                    // add to the stats
                    totalWaitingTime = totalWaitingTime + waitingTime - currentEvent.getTime();
                }
            }
        }
        // print out the stats here
        double averageWaitingTime = totalWaitingTime / numOfCustomers;
        String stats = String.format("[%.3f %d %d]",averageWaitingTime,numOfCustomers,numOfCustomersLeft);
        System.out.println(stats);
    }

    // Level 2
    public void simulate(int numOfServers, int maxQueueLength, List<Double> arrivalTimeList, List<Double> serviceTimeList){
        //local variables
        PriorityQueue queue = this.createPriorityQueue(arrivalTimeList);
        List<Server> serverList = this.createServerList(numOfServers);
        HashMap<Integer,Double> customerHashMap = this.createCustomerHashMap(serviceTimeList);
        // for stats
        double totalWaitingTime = 0;
        int numOfCustomers = queue.get().size();
        int numOfCustomersLeft = 0;

        // need to track which Events are waiting
        // so that I wont print those that are waiting again
        //List<Event> tempWaitingList = new ArrayList<Event>();
        HashMap<Integer,Event> waitingHashMap = new HashMap<Integer,Event>();

        // Simulation starts here
        while(!queue.get().isEmpty()) {
            Event currentEvent = queue.poll();
            //System.out.println(currentEvent);

            // Get the current customer Id
            int currentCustomerId = currentEvent.getCustomerId();
            double currentEventTime = currentEvent.getTime();

            if(currentEvent.getState() == EventEnumState.ArriveEvent) {
                boolean customerHasServer = false;
                //check if there are available servers to serve the current customer
                for (int i = 0; i < serverList.size(); i++) {
                    Server currentServer = serverList.get(i);
                    if(!currentServer.isServingAnotherCustomer()) {
                        customerHasServer = true;
                        Server updatedServer = new Server(i+1, currentCustomerId, currentEventTime);
                        serverList.set(i, updatedServer);
                        // Event from Arrive -> Serve
                        //Event newEvent = new ServeEvent(currentCustomerId, updatedServer.getNextAvailableTime(), j+1);
                        Event newEvent = new Event(currentCustomerId, updatedServer.getNextAvailableTime(), i+1,EventEnumState.ServeEvent);
                        queue.add(newEvent);
                        break;
                    } else {
                        continue;
                    }
                } //end of for-loop
                // This happens when the all the servers are serving at least 1 customer
                // then now we check if there is a server wih an empty waiting queue
                if(customerHasServer == false) {
                    for(int i = 0; i < serverList.size(); i++) {
                        Server currentServer = serverList.get(i);
                        if(currentServer.isServingAnotherCustomer()){
                            if(currentServer.isWaitingQueueFull(maxQueueLength)) {
                                // when the server is extremly busy
                                // is currently serving a customer and the waiting queue is full
                                continue;
                            } else {
                                // server is currently serving another customer
                                // but can add customer to the waiting queue
                                customerHasServer = true;
                                //Server updatedServer = new Server(i+1,currentServer.getServingCustomerId(),currentCustomerId,currentServer.getNextAvailableTime());
                                Server updatedServer = currentServer.addMoreWaitingCustomer(currentCustomerId);
                                serverList.set(i, updatedServer);
                                // Event from Arrive -> Wait
                                //double newEventTime = currentEvent.getTime() + currentEvent.getServingTime();
                                // Bug
                                // What is the time for the new Event?
                                //Event newEvent = new WaitEvent(currentCustomerId, currentEvent.getTime(), i+1);
                                Event newEvent = new Event(currentCustomerId, currentEvent.getTime(), i+1,EventEnumState.WaitEvent);
                                queue.add(newEvent);
                                break;
                            }
                        } 
                    } //end of for loop
                    
                    //Check if the customer still does not have a server
                    // Event from Arrive -> Leave
                    if(customerHasServer == false) {
                        // Event from Arrive -> Leave
                        // Pass in 0 because there is no server
                        //Event newEvent = new LeaveEvent(currentCustomerId, currentEventTime, 0);
                        Event newEvent = new Event(currentCustomerId, currentEventTime, 0,EventEnumState.LeaveEvent);
                        queue.add(newEvent);
                        // add to the stats
                        numOfCustomersLeft = numOfCustomersLeft + 1;
                        numOfCustomers = numOfCustomers - 1;
                    }
                }
                System.out.println(currentEvent);
            } else if(currentEvent.getState() == EventEnumState.ServeEvent) {
                // Serve the customer immediately
                // Change State from Serve -> Done
                // Qn: Remove the currentCustomer from the server?
                // Ans: no
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                // Level 2 chamges here
                //double afterServeTime = currentEventTime + currentEvent.getServingTime();
                double afterServeTime = currentEventTime + customerHashMap.get(currentCustomerId);
                Server updatedServer = new Server(currentEvent.getServerId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerList(), afterServeTime);
                serverList.set(currentEvent.getServerId() - 1, updatedServer);
                Event newEvent = new Event(currentCustomerId, afterServeTime, currentEvent.getServerId(),EventEnumState.DoneEvent);
                queue.add(newEvent);
                System.out.println(currentEvent);
            } else if(currentEvent.getState() == EventEnumState.DoneEvent) {
                // Remove the customer from the server
                // Qn: Push the waiting customer to the currentServingCustomer?
                // Ans: Maybe yes
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                //double doneTime = currentEventTime;
                // int waitingCustomerId = currentServer.getWaitingCustomerId();
                // Server updatedServer = new Server(currentServer.getId(), waitingCustomerId, 0, doneTime);
                // just for level 1
                int waitingCustomerId;
                if(currentServer.getWaitingCustomerList().isEmpty()) {
                    waitingCustomerId = 0;
                } else {
                    waitingCustomerId = currentServer.getWaitingCustomerList().get(0);
                }
                //int waitingCustomerId = currentServer.getWaitingCustomerList().get(0);
                // BUG HERE
                // never push the other waiting customers up the list
                //Server updatedServer = new Server(currentServer.getId(),waitingCustomerId,doneTime);
                Server updatedServer = currentServer.serveEarliestWaitingCustomer(waitingCustomerId);
                serverList.set(currentServer.getId() - 1, updatedServer);
                // No need to do anything else since the event has already popped out from the queue;
                // Qn: Are you sure?
                // need to change the waiting state for the customer
                // if there is a waiting customer
                for(int i = 0; i < queue.get().size(); i++) {
                    Event findWaitEvent = queue.get().get(i);
                    if (findWaitEvent.getState() ==  EventEnumState.WaitEvent && findWaitEvent.getCustomerId() == waitingCustomerId) {
                        //Event newEvent = new ServeEvent(waitingCustomerId, findWaitEvent.getTime(), findWaitEvent.getServerId());
                        Event newEvent = new Event(waitingCustomerId, findWaitEvent.getTime(), findWaitEvent.getServerId(),EventEnumState.ServeEvent);
                        queue.get().set(i,newEvent);
                        break;
                    } else {
                        continue;
                    }
                }
                System.out.println(currentEvent);
            } else if(currentEvent.getState() == EventEnumState.WaitEvent) {
                //harderst part of this project
                // Check if the server can serve the customer now
                // if the currentServingid has been changed from the DoneState
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                if(currentServer.getServingCustomerId() == currentCustomerId) {
                    // Wait -> Serve
                    //Server updatedServer = new Server(currentServer.getId(), currentCustomerId, currentServer.getServingCustomerId(), currentServer.getNextAvailableTime());
                    Server updatedServer = currentServer.serveEarliestWaitingCustomer(currentCustomerId);
                    serverList.set(currentServer.getId() - 1, updatedServer);
                    double servingTime = updatedServer.getNextAvailableTime();
                    //Event newEvent = new ServeEvent(currentCustomerId, servingTime,currentServer.getId());
                    Event newEvent = new Event(currentCustomerId, servingTime,currentServer.getId(),EventEnumState.ServeEvent);
                    queue.add(newEvent);
                    // add to the stats
                    totalWaitingTime = totalWaitingTime + servingTime - currentEvent.getTime();
                    System.out.println(currentEvent);
                    // remove the waiting customer from the waiting list
                    waitingHashMap.remove(currentCustomerId);
                } else {
                    // This happens when I still need to wait
                    // need to update the time
                    //Server updatedServer = new Server(currentServer.getId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerId(), currentServer.getNextAvailableTime());
                    Server updatedServer = currentServer.updateTimeOnly(currentServer.getNextAvailableTime());
                    serverList.set(currentServer.getId() - 1, updatedServer);
                    double waitingTime = updatedServer.getNextAvailableTime();
                    //Event newEvent = new WaitEvent(currentCustomerId, waitingTime,currentServer.getId());
                    Event newEvent = new Event(currentCustomerId, waitingTime,currentServer.getId(),EventEnumState.WaitEvent);
                    queue.add(newEvent);
                    // add to the stats
                    totalWaitingTime = totalWaitingTime + waitingTime - currentEvent.getTime();
                    // add to the waitingList
                    // test if the waitingList contains the event
                    Optional<Event> test = Optional.ofNullable(waitingHashMap.get(currentCustomerId));
                    test.ifPresentOrElse(
                        event -> {
                            //System.out.println(currentEvent);
                            waitingHashMap.put(currentCustomerId,newEvent);
                        }, 
                        () -> {
                            System.out.println(currentEvent);
                            waitingHashMap.put(currentCustomerId,newEvent);
                        }
                    );
                }
            }
        }

        // print out the stats here
        double averageWaitingTime = totalWaitingTime / numOfCustomers;
        String stats = String.format("[%.3f %d %d]",averageWaitingTime,numOfCustomers,numOfCustomersLeft);
        System.out.println(stats);
    }

    //level 3
    public void simulate(int numOfCustomers,int numOfServers, int maxQueueLength, List<Double> arrivalTimeList, List<Double> serviceTimeList, List<Double> restingTimeList) {
        //local variables
        PriorityQueue queue = this.createPriorityQueue(arrivalTimeList);
        List<Server> serverList = this.createServerList(numOfServers);

        //need to track which server is resting now
        List<Server> restingServersList = new ArrayList<Server>();

        HashMap<Integer,Double> customerHashMap = this.createCustomerHashMap(serviceTimeList);
        // for stats
        double totalWaitingTime = 0;
        //int numOfCustomers = queue.get().size();
        int numOfCustomersLeft = 0;

        // need to track which Events are waiting
        // so that I wont print those that are waiting again
        //List<Event> tempWaitingList = new ArrayList<Event>();
        HashMap<Integer,Event> waitingHashMap = new HashMap<Integer,Event>();

        // Simulation starts here
        while(!queue.get().isEmpty()) {
            Event currentEvent = queue.poll();

            // Get the current customer Id
            int currentCustomerId = currentEvent.getCustomerId();
            double currentEventTime = currentEvent.getTime();

            if(currentEvent.getState() == EventEnumState.ArriveEvent) {
                boolean customerHasServer = false;
                //check if there are available servers to serve the current customer
                for (int i = 0; i < serverList.size(); i++) {
                    Server currentServer = serverList.get(i);
                    // boolean test = IsServerResting(restingServersList, currentServer.getId());
                    if(!currentServer.isServingAnotherCustomer() && !IsServerResting(restingServersList, currentServer.getId())) {
                        customerHasServer = true;
                        // Server updatedServer = new Server(i+1, currentCustomerId, currentEventTime);
                        Server updatedServer = new Server(currentServer.getId(), currentCustomerId, currentEventTime);
                        serverList.set(i, updatedServer);
                        // Event from Arrive -> Serve
                        //Event newEvent = new ServeEvent(currentCustomerId, updatedServer.getNextAvailableTime(), j+1);
                        // Event newEvent = new Event(currentCustomerId, updatedServer.getNextAvailableTime(), i+1,EventEnumState.ServeEvent);
                        Event newEvent = new Event(currentCustomerId, updatedServer.getNextAvailableTime(), updatedServer.getId(),EventEnumState.ServeEvent);
                        queue.add(newEvent);
                        break;
                    } else {
                        continue;
                    }
                } //end of for-loop
                // This happens when the all the servers are serving at least 1 customer
                // then now we check if there is a server with an empty waiting queue
                if(customerHasServer == false) {
                    for(int i = 0; i < serverList.size(); i++) {
                        Server currentServer = serverList.get(i);
                        if(currentServer.isServingAnotherCustomer() && !IsServerResting(restingServersList, currentServer.getId())){
                            if(currentServer.isWaitingQueueFull(maxQueueLength)) {
                                // when the server is extremly busy
                                // is currently serving a customer and the waiting queue is full
                                continue;
                            } else {
                                if(currentCustomerId == 14) {
                                    System.out.println(currentServer.getWaitingCustomerList());
                                }
                                // server is currently serving another customer
                                // but can add customer to the waiting queue
                                customerHasServer = true;
                                //Server updatedServer = new Server(i+1,currentServer.getServingCustomerId(),currentCustomerId,currentServer.getNextAvailableTime());
                                Server updatedServer = currentServer.addMoreWaitingCustomer(currentCustomerId);
                                serverList.set(i, updatedServer);
                                // Event from Arrive -> Wait
                                //double newEventTime = currentEvent.getTime() + currentEvent.getServingTime();
                                // Bug
                                // What is the time for the new Event?
                                //Event newEvent = new WaitEvent(currentCustomerId, currentEvent.getTime(), i+1);
                                // Event newEvent = new Event(currentCustomerId, currentEvent.getTime(), i+1,EventEnumState.WaitEvent);
                                Event newEvent = new Event(currentCustomerId, currentEvent.getTime(), updatedServer.getId(),EventEnumState.WaitEvent);
                                queue.add(newEvent);
                                break;
                            }
                        } else {
                            continue;
                        }
                    } //end of for loop
                    
                    //Check if the customer still does not have a server
                    // Event from Arrive -> Leave
                    if(customerHasServer == false) {
                        // Event from Arrive -> Leave
                        // Pass in 0 because there is no server
                        //Event newEvent = new LeaveEvent(currentCustomerId, currentEventTime, 0);
                        Event newEvent = new Event(currentCustomerId, currentEventTime, 0,EventEnumState.LeaveEvent);
                        queue.add(newEvent);
                        // add to the stats
                        numOfCustomersLeft = numOfCustomersLeft + 1;
                        numOfCustomers = numOfCustomers - 1;
                    }
                }
                System.out.println(currentEvent);
            } else if(currentEvent.getState() == EventEnumState.ServeEvent) {
                // Serve the customer immediately
                // Change State from Serve -> Done
                // Qn: Remove the currentCustomer from the server?
                // Ans: no
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                // Level 2 chamges here
                //double afterServeTime = currentEventTime + currentEvent.getServingTime();
                double afterServeTime = currentEventTime + customerHashMap.get(currentCustomerId);
                Server updatedServer = new Server(currentEvent.getServerId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerList(), afterServeTime);
                serverList.set(currentEvent.getServerId() - 1, updatedServer);
                Event newEvent = new Event(currentCustomerId, afterServeTime, currentEvent.getServerId(),EventEnumState.DoneEvent);
                queue.add(newEvent);
                System.out.println(currentEvent);

            } else if(currentEvent.getState() == EventEnumState.DoneEvent) {
                // level 3 requirement
                // need to make the server rest after the server served the customer
                // take out the server from the server list?

                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                int waitingCustomerId;
                if(currentServer.getWaitingCustomerList().isEmpty()) {
                    waitingCustomerId = 0;
                } else {
                    waitingCustomerId = currentServer.getWaitingCustomerList().get(0);
                }

                // Huge bug here
                // Server updatedServer = currentServer.serveEarliestWaitingCustomer(waitingCustomerId);
                // serverList.set(currentServer.getId() - 1, updatedServer);

                for(int i = 0; i < queue.get().size(); i++) {
                    Event findWaitEvent = queue.get().get(i);
                    if (findWaitEvent.getState() ==  EventEnumState.WaitEvent && findWaitEvent.getCustomerId() == waitingCustomerId) {
                        double nextServeTime = findWaitEvent.getTime() + restingTimeList.get(0);
                        totalWaitingTime+=restingTimeList.get(0);
                        //restingTimeList.remove(0);
                        //Event newEvent = new Event(waitingCustomerId, findWaitEvent.getTime(), findWaitEvent.getServerId(),EventEnumState.ServeEvent);
                        Event newEvent = new Event(waitingCustomerId, nextServeTime, findWaitEvent.getServerId(),EventEnumState.ServeEvent);
                        queue.get().set(i,newEvent);

                        // Level 3 requirement
                        // add another resting event
                        Event newRestingEvent = new Event(waitingCustomerId, findWaitEvent.getTime(), findWaitEvent.getServerId(),EventEnumState.RestingEvent);
                        queue.get().add(i,newRestingEvent);

                        // need to also update the server again
                        // cause the nextAvailable time is different when the server rests
                        // updatedServer = updatedServer.updateTimeOnly(updatedServer.getNextAvailableTime() + restingTimeList.get(0));
                        // serverList.set(currentServer.getId() - 1, updatedServer);
                        Server updatedServer = currentServer.updateTimeOnly(currentServer.getNextAvailableTime() + restingTimeList.get(0));
                        serverList.set(currentServer.getId() - 1, updatedServer);
                        restingTimeList.remove(0);
                        
                        
                        //then remove the currentServer from the serverList to the restingList
                        //serverList.remove(currentServer.getId() - 1);
                        // removeServer(serverList, updatedServer.getId());
                        restingServersList.add(updatedServer);
                        break;
                    } else {
                        continue;
                    }
                }

                // when the Server is completely free
                if(waitingCustomerId == 0) {
                    // Level 3 requirement
                    // add another resting event
                    double currentTime = currentEvent.getTime() + restingTimeList.get(0);

                    // need to also update the server again
                    // cause the nextAvailable time is different when the server rests
                    // updatedServer = updatedServer.updateTimeOnly(updatedServer.getNextAvailableTime() + restingTimeList.get(0));
                    // serverList.set(currentServer.getId() - 1, updatedServer);
                    Server updatedServer = currentServer.updateTimeOnly(currentServer.getNextAvailableTime() + restingTimeList.get(0));
                    serverList.set(currentServer.getId() - 1, updatedServer);

                    restingTimeList.remove(0);
                    Event newRestingEvent = new Event(waitingCustomerId, currentTime, currentEvent.getServerId(),EventEnumState.RestingEvent);
                    queue.get().add(newRestingEvent);

                    restingServersList.add(updatedServer);
                }
               System.out.println(currentEvent);      
            } else if(currentEvent.getState() == EventEnumState.WaitEvent) {
                //harderst part of this project
                // Check if the server can serve the customer now
                // if the currentServingid has been changed from the DoneState
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                if(currentServer.getServingCustomerId() == currentCustomerId) {
                    // Wait -> Serve
                    //Server updatedServer = new Server(currentServer.getId(), currentCustomerId, currentServer.getServingCustomerId(), currentServer.getNextAvailableTime());
                    Server updatedServer = currentServer.serveEarliestWaitingCustomer(currentCustomerId);
                    serverList.set(currentServer.getId() - 1, updatedServer);
                    double servingTime = updatedServer.getNextAvailableTime();
                    //Event newEvent = new ServeEvent(currentCustomerId, servingTime,currentServer.getId());
                    Event newEvent = new Event(currentCustomerId, servingTime,currentServer.getId(),EventEnumState.ServeEvent);
                    queue.add(newEvent);
                    // add to the stats
                    totalWaitingTime = totalWaitingTime + servingTime - currentEvent.getTime();
                    System.out.println(currentEvent);
                    // remove the waiting customer from the waiting list
                    waitingHashMap.remove(currentCustomerId);
                } else {
                    // This happens when I still need to wait
                    // need to update the time
                    //Server updatedServer = new Server(currentServer.getId(), currentServer.getServingCustomerId(), currentServer.getWaitingCustomerId(), currentServer.getNextAvailableTime());
                    Server updatedServer = currentServer.updateTimeOnly(currentServer.getNextAvailableTime());
                    serverList.set(currentServer.getId() - 1, updatedServer);
                    double waitingTime = updatedServer.getNextAvailableTime();
                    //Event newEvent = new WaitEvent(currentCustomerId, waitingTime,currentServer.getId());
                    Event newEvent = new Event(currentCustomerId, waitingTime,currentServer.getId(),EventEnumState.WaitEvent);
                    queue.add(newEvent);
                    // add to the stats
                    totalWaitingTime = totalWaitingTime + waitingTime - currentEvent.getTime();
                    // add to the waitingList
                    // test if the waitingList contains the event
                    Optional<Event> test = Optional.ofNullable(waitingHashMap.get(currentCustomerId));
                    test.ifPresentOrElse(
                        event -> {
                            //System.out.println(currentEvent);
                            waitingHashMap.put(currentCustomerId,newEvent);
                        }, 
                        () -> {
                            System.out.println(currentEvent);
                            waitingHashMap.put(currentCustomerId,newEvent);
                        }
                    );
                }
            } else if(currentEvent.getState() == EventEnumState.RestingEvent) {
                // add back the missing Server from the resting list to the Server list
                // Bug here and I have no idea how to fix this
                
                removeServer(restingServersList, currentEvent.getServerId());
                Server currentServer = serverList.get(currentEvent.getServerId() - 1);
                int waitingCustomerId;
                if(currentServer.getWaitingCustomerList().isEmpty()) {
                    waitingCustomerId = 0;
                } else {
                    waitingCustomerId = currentServer.getWaitingCustomerList().get(0);
                }
                Server updatedServer = currentServer.serveEarliestWaitingCustomer(waitingCustomerId);
                serverList.set(currentServer.getId() - 1, updatedServer);
            }

        }

        // print out the stats here
        double averageWaitingTime = totalWaitingTime / numOfCustomers;
        String stats = String.format("[%.3f %d %d]",averageWaitingTime,numOfCustomers,numOfCustomersLeft);
        System.out.println(stats);
    }
}
