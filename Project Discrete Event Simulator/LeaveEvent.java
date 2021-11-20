class LeaveEvent extends Event {
    
    public LeaveEvent(double leaveTime,Customer customer) {
        super(leaveTime,customer);
    }
    // toString
    @Override
    public String toString() {
        return String.format("%.3f %d leaves",super.getTime(),super.getCustomer().getCustomerId());
    }
}
