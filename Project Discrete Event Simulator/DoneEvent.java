class DoneEvent extends Event {
    
    public DoneEvent(double doneTime,Customer customer,Server server) {
        super(doneTime,customer,server);
    }

    //toString
    @Override
    public String toString() {  
        return String.format("%.3f %d done serving by server %d",super.getTime(),
            super.getCustomer().getCustomerId(),super.getServer().get().getId());
    }
}
