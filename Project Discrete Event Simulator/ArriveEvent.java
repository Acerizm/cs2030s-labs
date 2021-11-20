class ArriveEvent extends Event {
    // Event needs to know how many customers and servers are there
    //private final Customer customer;
    //private final Server server;

    //Constructor
    public ArriveEvent(double arrivalTime,Customer customer) {
        super(arrivalTime,customer);
    }

    // get method
    // Customer getCustomer() {
    //     return this.super.getCustomer();
    // }

    // toString method
    @Override
    public String toString() {
        return String.format("%.3f %d arrives",super.getTime(),super.getCustomer().getCustomerId());
    }
}
