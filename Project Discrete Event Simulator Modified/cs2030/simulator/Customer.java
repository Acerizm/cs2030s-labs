package cs2030.simulator;
public class Customer {
    private final int id;
    private final double arrivalTime;
    // level 2 onwards
    private final double serviceTime;

    public Customer(int id, double arrivalTime,double serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    } 

    int getId() {
        return this.id;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getServiceTime() {
        return this.serviceTime;
    }


}