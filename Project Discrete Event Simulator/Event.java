import java.util.Optional;

class Event {
    //private final double arrivalTime;
    //private final int identifier;
    private final double time;
    private final Customer customer;
    // Some events have no server like ArriveEvent and LeaveEvent
    private final Optional<Server> server;

    //Constructor
    // public Event(double arrivalTime, int identifier) {
    //     this.arrivalTime = arrivalTime;
    //     this.identifier = identifier;
    // }
    public Event(double time,Customer customer) {
        this.time = time;
        this.customer = customer;
        this.server = Optional.empty();
    }

    public Event(double time,Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = Optional.ofNullable(server);
    }

    // Methods
    double getTime() {
        //return this.arrivalTime;
        return this.time;
    }

    Customer getCustomer() {
        return this.customer;
    }

    //Some events have no server
    //fix this later
    Optional<Server> getServer() {
        return this.server;
    }

    // //toString method
    // @Override
    // public String toString() {
    //     return String.format("%.3f %d arrives",this.arrivalTime,this.identifier);
    // }
}
