class Customer {
    private final int id;
    private final double arrivalTime;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    } 

    public int getCustomerId() {
        return this.id;
    }

    public double getTime() {
        return this.arrivalTime;
    }

    // @Override 
    // public String toString() {
    //     return String.format("%.3f %d arrives",this.arrivalTime,this.id);
    // }
}
