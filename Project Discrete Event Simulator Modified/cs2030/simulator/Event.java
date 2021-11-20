package cs2030.simulator;

class Event {
    private final double serviceTime = 1.0;
    private final int customerId;
    private final double time;
    private final int serverId;
    private final EventEnumState state;
    // Level 4 needs to know which kiosk im serving to
    // Ans: no need as the kiosk requirement is this
    // if there are k human servers, then the self-checkout counters are identified from k + 1 onwards.

    public Event(int customerId, double time,EventEnumState state ) {
        this.customerId = customerId;
        this.time = time;
        //default serverId for no servers
        this.serverId = 0;
        this.state = state;
    }

    public Event(int customerId,double time, int serverId,EventEnumState state) {
        this.customerId = customerId;
        this.time = time;
        this.serverId = serverId;
        this.state = state;
    }

    int getCustomerId() {
        return this.customerId;
    }

    double getTime() {
        return this.time;
    }

    int getServerId() {
        return this.serverId;
    }

    double getServingTime() {
        return this.serviceTime;
    }

    EventEnumState getState() {
        return this.state;
    }

    Event nextEvent(String state) {
        Event updatedEvent;
        switch(state) {
            case "Arrive":
            {
                EventEnumState newState = this.state.nextState("Arrive");
                updatedEvent = new Event(this.customerId, this.getTime(), this.serverId, newState);
                break;
            }
            case "Wait":
            {
                EventEnumState newState = this.state.nextState("Wait");
                updatedEvent = new Event(this.customerId, this.getTime(), this.serverId, newState);
                break;
            }
            case "Leave":
            {
                EventEnumState newState = this.state.nextState("Leave");
                updatedEvent = new Event(this.customerId, this.getTime(), this.serverId, newState);
                break;
            }
            case "Serve":
            {
                EventEnumState newState = this.state.nextState("Serve");
                updatedEvent = new Event(this.customerId, this.getTime(), this.serverId, newState);
                break;
            }
            case "Done": 
            {
                EventEnumState newState = this.state.nextState("Done");
                updatedEvent = new Event(this.customerId, this.getTime(), this.serverId, newState);
                break;
            }
            default:
                // just return the same event
                updatedEvent = this;
        }       
        return updatedEvent; 
    }
    
    //toString method
    @Override
    public String toString() {
        switch(this.state) {
            case ArriveEvent:
                return String.format("%.3f %d arrives",this.getTime(),this.getCustomerId());
            case WaitEvent:
                return String.format("%.3f %d waits at server %d",this.getTime(),this.getCustomerId(),this.getServerId());
            case LeaveEvent:
                return String.format("%.3f %d leaves",this.getTime(),this.getCustomerId());
            case ServeEvent:
                return String.format("%.3f %d serves by server %d",this.getTime(),this.getCustomerId(),this.getServerId());
            case DoneEvent:
                return String.format("%.3f %d done serving by server %d",this.getTime(),this.getCustomerId(),this.getServerId());
            default:
                return "";
        }
    }
}
