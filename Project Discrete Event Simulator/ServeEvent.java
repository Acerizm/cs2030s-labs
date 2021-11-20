class ServeEvent extends Event {
    public ServeEvent(double serveTime,Customer customer,Server server) {
        super(serveTime,customer,server);
    }

    // toString
    @Override
    public String toString() {
        return String.format("%.3f %d serves by server %d",super.getTime(),
            super.getCustomer().getCustomerId(),super.getServer().get().getId());
    }
}
