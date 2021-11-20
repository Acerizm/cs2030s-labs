import java.util.Optional;

class Server {
    //server needs to know which customer it is serving
    private final Optional<Customer> optionalServingCustomer;
    private final Optional<Customer> optionalWaitingCustomer;
    private final int id;
    private final double nextAvailableTime;
    private final double serviceTime = 1.0;

    public Server(int id) {
        this.optionalServingCustomer = Optional.empty();
        this.optionalWaitingCustomer = Optional.empty();
        this.nextAvailableTime = 0.0;
        this.id = id;
    }
    public Server(int id, Customer customerServed,double nextAvailableTime) {
        this.optionalServingCustomer = Optional.ofNullable(customerServed);
        this.optionalWaitingCustomer = Optional.empty();
        this.nextAvailableTime = nextAvailableTime;
        this.id = id;
    }

    public Server(int id, Customer customerServed, Customer waitingCustomer, double nextAvailableTime) {
        this.optionalServingCustomer = Optional.ofNullable(customerServed);
        this.optionalWaitingCustomer = Optional.ofNullable(waitingCustomer);
        this.nextAvailableTime = nextAvailableTime;
        this.id = id;
    }

    // methods

    Optional<Customer> getCustomer() {
        return this.optionalServingCustomer;
    }

    Optional<Customer> getWaitingCustomer() {
        return this.optionalWaitingCustomer;
    }

    int getId() {
        return this.id;
    }

    double getServiceTime() {
        return this.serviceTime;
    }

    double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    // Server updateNextAvailableTime(double time) {
    //     if(!this.getWaitingCustomer().isPresent()){
    //         return new Server(this.getId(), this.getCustomer().get(), time);
    //     } else {
    //         return new Server(this.getId(), this.getCustomer().get(), this.getWaitingCustomer().get(), time);
    //     }
    // }
}
