import java.util.List;

public class Simulator {
    // PriorityQueue is to store the events?
    private final PriorityQueue priorityQueue;
    private final List<Server> serverList;

    //Constructor
    public Simulator(List<Event> list, List<Server> serverList) {
        this.priorityQueue = new PriorityQueue(list);
        this.serverList = serverList;
    }

    // simulate method for level 1
    void simulate() {
        while(!this.priorityQueue.get().isEmpty()) {

            // 1st Stage: Arrival Stage
            Event currentEvent = this.priorityQueue.poll();
            System.out.println(currentEvent);
            if(currentEvent instanceof ArriveEvent) {
                // check if there is any available servers to serve the customer
                ArriveEvent tempEvent = (ArriveEvent)currentEvent;
                for(int i = 0; i < serverList.size(); i++) {
                    Server currentServer = serverList.get(i);
                    //currentServer.getCustomer().or
                    currentServer.getCustomer().ifPresentOrElse(
                        (currentServingCustomer) -> {
                            // then check if there is a waiting customer
                            currentServer.getWaitingCustomer().ifPresentOrElse(
                                (currentWaitingCustomer) -> {
                                    // there is a current waiting customer
                                    // Change the Event from Arrive -> Leave
                                    // Leave time is the same as the arrival time
                                    Customer currentCustomer = tempEvent.getCustomer();
                                    Event newLeaveEvent = new LeaveEvent(currentEvent.getTime(), currentCustomer);
                                    // then add the new event to the priority queue
                                    this.priorityQueue.add(newLeaveEvent);
                                }, 
                                // this happens when servingCustomer exists but waitingCustomer is empty for the server
                                () -> {
                                    Customer currentCustomer = tempEvent.getCustomer();
                                    Server tempServer = new Server(currentServer.getId(),currentServer.getCustomer().get(),currentCustomer,currentServer.getNextAvailableTime());
                                    serverList.set(currentServer.getId() - 1,tempServer);
                                    // change the Event from Arrive -> Wait
                                    updateAllServers(tempServer.getId(),tempServer);
                                    Event newWaitEvent = new WaitEvent(currentEvent.getTime(), currentCustomer,tempServer);
                                    this.priorityQueue.add(newWaitEvent);
                                }
                            );
                        },
                        () -> {
                            // no customer being served by the current Server
                            Customer currentCustomer = tempEvent.getCustomer();
                            Server tempServer = new Server(currentServer.getId(),currentCustomer,currentCustomer.getTime());
                            // replace the server from the original list
                            serverList.set(currentServer.getId() - 1,tempServer);
                            updateAllServers(tempServer.getId(),tempServer);
                            // change the Event from Arrive -> Serve 
                            Event newServeEvent = new ServeEvent(currentEvent.getTime(), currentCustomer,tempServer);
                            this.priorityQueue.add(newServeEvent);
                        }
                    );
                }
                // end of for-loop
            } else if(currentEvent instanceof ServeEvent) {
                ServeEvent tempEvent = (ServeEvent)currentEvent;
                // Before changing the state, need to update the server
                Server currentServer = tempEvent.getServer().get();
                // BUG here
                // Since i cannot use builder pattern, I am forced to do this
                currentServer.getCustomer().ifPresent(
                    servingCustomer -> {
                        currentServer.getWaitingCustomer().ifPresentOrElse(
                            waitingCustomer -> {
                            // if there is a waiting customer call the approapiate constructor
                            Server updatedServer = new Server(currentServer.getId(), servingCustomer, waitingCustomer, 
                                currentServer.getNextAvailableTime() + currentServer.getServiceTime());
                            serverList.set(updatedServer.getId() - 1, updatedServer);
                            updateAllServers(updatedServer.getId(), updatedServer);
                            Event newDoneEvent = new DoneEvent(updatedServer.getNextAvailableTime(),updatedServer.getCustomer().get(),updatedServer);
                            this.priorityQueue.add(newDoneEvent);
                            }, 
                            // if there is no waiting customer
                            () -> {
                                Server updatedServer = new Server(currentServer.getId(), servingCustomer,10);
                                serverList.set(updatedServer.getId() - 1, updatedServer);
                                updateAllServers(updatedServer.getId(), updatedServer);
                                Event newDoneEvent = new DoneEvent(updatedServer.getNextAvailableTime(),updatedServer.getCustomer().get(),updatedServer);
                                this.priorityQueue.add(newDoneEvent);
                            }
                        );
                    }
                );
            } else if(currentEvent instanceof WaitEvent) {
                WaitEvent tempEvent = (WaitEvent)currentEvent;
                Customer currentCustomer = tempEvent.getCustomer();
                // Wait state is only able to go to the Serve State if
                // 1) there is no customer being served by the servers
                for (int i = 0; i < serverList.size(); i++) {
                    Server currentServer = serverList.get(i);
                    currentServer.getCustomer().ifPresentOrElse(
                        // need to add time here for the wait state
                        (servingCustomer) -> {
                            //need to check if the servingCustomer is the same customer in the Wait state
                            if(servingCustomer.getCustomerId() == currentCustomer.getCustomerId()) {
                                Event newServeEvent = new ServeEvent(tempEvent.getTime() + currentServer.getServiceTime(), currentCustomer, currentServer);
                                this.priorityQueue.add(newServeEvent);
                            } else {
                                Event newWaitEvent = new WaitEvent(tempEvent.getTime() + currentServer.getServiceTime(), currentCustomer, currentServer);
                                this.priorityQueue.add(newWaitEvent);
                            }
                        }, 
                        // add the customer to the currentCustomer in the server
                        () -> {
                            Server updatedServer = new Server(currentServer.getId(), currentCustomer,currentServer.getNextAvailableTime());
                            serverList.set(currentServer.getId() - 1,updatedServer);
                            updateAllServers(updatedServer.getId(),updatedServer);
                            //change the state from Wait -> Serve
                            Event newServeEvent = new ServeEvent(currentServer.getServiceTime(), currentCustomer, updatedServer);
                            this.priorityQueue.add(newServeEvent);
                        }
                    );
                }
                // The customer will continue waiting until a server's currentServingCustomer is empty
            } else if(currentEvent instanceof DoneEvent) {
                //this whole code should be at the done state??
                // check if the server has any waiting customer
                DoneEvent tempEvent = (DoneEvent)currentEvent;
                // Before changing the state, need to update the server
                Server currentServer = tempEvent.getServer().get();
                currentServer.getWaitingCustomer().ifPresentOrElse(
                    waitingCustomer -> {
                        Server updatedServer = new Server(currentServer.getId(), waitingCustomer,currentServer.getNextAvailableTime());
                        this.serverList.set(currentServer.getId() - 1, updatedServer);
                        updateAllServers(updatedServer.getId(),updatedServer);                       
                    }, 
                    () -> {
                    }
                );
            }
        
        }
    }

    void updateAllServers(int serverId,Server updatedServer) {
        List<Event> tempList = this.priorityQueue.get();
        for(int i = 0; i < tempList.size(); i++) {
            //try to replace each Event with itself but with updated server data
            Event currentEvent = tempList.get(i);
            // Need to check if the event needs to update based on the server serving the customer
            // and also need to check if there is a server assigned to the event
            if(currentEvent.getServer().isPresent() == true) {
                if (currentEvent.getServer().get().getId() == serverId) {
                    if(currentEvent instanceof ServeEvent) {
                        Event updatedEvent = new ServeEvent(currentEvent.getTime(), currentEvent.getCustomer(), updatedServer);
                        this.priorityQueue.replace(i,updatedEvent);
                    } else if(currentEvent instanceof DoneEvent) {
                        Event updatedEvent = new DoneEvent(currentEvent.getTime(), currentEvent.getCustomer(), updatedServer);
                        this.priorityQueue.replace(i,updatedEvent);
                    } else if(currentEvent instanceof WaitEvent) {
                        Event updatedEvent = new WaitEvent(currentEvent.getTime(), currentEvent.getCustomer(), updatedServer);
                        this.priorityQueue.replace(i,updatedEvent);
                    } else {
                        //do nothing
                    }
                } else {
                    //do nothing
                }
            } else {    
                //do nothing
            }
        }
    }
}
