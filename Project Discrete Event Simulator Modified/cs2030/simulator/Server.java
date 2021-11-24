package cs2030.simulator;
import java.util.ArrayList;
import java.util.List;

class Server {
    //Qn: Does server need to know which customer it is serving?
    private final int id;
    private final int servingCustomerId;
    //private final int waitingCustomerId;
    //level 2 onwards can have a list of waiting customers
    // but i still refuse to use Customer class because dont see a need to :D
    // this list is unbounded, need to fix this with another method
    private final List<Integer> waitingCustomerList;
    private final double nextAvailableTime;

    public Server(int id) {
        this.id = id;
        this.servingCustomerId = 0;
        //this.waitingCustomerId = 0;
        this.waitingCustomerList = new ArrayList<Integer>();
        this.nextAvailableTime = 0;
    }


    public Server(int id, int servingCustomerId, double nextAvailableTime) {
        this.id = id;
        this.servingCustomerId = servingCustomerId;
        //this.waitingCustomerId = 0;
        this.waitingCustomerList = new ArrayList<Integer>();
        this.nextAvailableTime = nextAvailableTime;
    }

    public Server(int id, int servingCustomerId, List<Integer> waitingCustomerList, double nextAvailableTime) {
        this.id = id;
        this.servingCustomerId = servingCustomerId;
        //this.waitingCustomerId = waitingCustomerId;
        this.waitingCustomerList = waitingCustomerList;
        this.nextAvailableTime = nextAvailableTime;
    }

    int getId() {
        return this.id;
    }

    int getServingCustomerId() {
        return this.servingCustomerId;
    }

    // int getWaitingCustomerId() {
    //     return this.waitingCustomerId;
    // }

    List<Integer> getWaitingCustomerList() {
        return this.waitingCustomerList;
    }

    boolean isServingAnotherCustomer() {
        return servingCustomerId > 0;
    }

    //Bug here
    boolean hasWaitingCustomer() {
        //return waitingCustomerId > 0;
        return !this.waitingCustomerList.isEmpty();
    }


    // Only for level 1 
    // Huge potential bug here
    boolean haveTwoCustomers() {
        return isServingAnotherCustomer() && hasWaitingCustomer();
    }

    // Need to be careful before adding another waiting customer
    // as there is a limit to the queue
    Server addMoreWaitingCustomer(int newWaitingCustomerId) {
        List<Integer> newWaitingCustomerList = this.getWaitingCustomerList();
        newWaitingCustomerList.add(newWaitingCustomerId);
        return new Server(this.getId(), this.servingCustomerId, newWaitingCustomerList, this.nextAvailableTime);
    }

    // this code will run when the server is ready to serve the earlist waiting customer
    // State Wait -> Serve
    Server serveEarliestWaitingCustomer(int waitingCustomerId) {
        List<Integer> newWaitingCustomerList = this.getWaitingCustomerList();
        for(int i = 0; i < waitingCustomerList.size(); i++) {
            if(waitingCustomerList.get(i) == waitingCustomerId) {
                waitingCustomerList.remove(i);
                break;
            } else {
                continue;
            }
        }
        return new Server(this.getId(),waitingCustomerId,newWaitingCustomerList,this.getNextAvailableTime());
    }

    // // State Wait -> Wait
    Server updateTimeOnly(double newTime) {
        return new Server(this.getId(),this.getServingCustomerId(), this.getWaitingCustomerList(), newTime);
    }

    boolean isWaitingQueueFull(int maxQueueLength) {
        // return this.waitingCustomerList.size() == maxQueueLength;
        return this.waitingCustomerList.size() >= maxQueueLength;
    }

    double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    // pop the earliest waiting customer and push the list up
    
}
