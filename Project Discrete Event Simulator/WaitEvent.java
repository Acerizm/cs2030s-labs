class WaitEvent extends Event {
    
    public WaitEvent(double waitTime,Customer customer,Server server) {
        super(waitTime,customer,server);
    }

    // toString
    @Override
    public String toString() {
        return String.format("%.3f %d waits at server %d",super.getTime(),
            super.getCustomer().getCustomerId(),super.getServer().get().getId());
    }
}
