package cs2030.simulator;

public enum EventEnumState {
    ArriveEvent,
    WaitEvent,
    LeaveEvent,
    DoneEvent,
    ServeEvent,
    RestingEvent;

    public EventEnumState nextState(String state) {
        EventEnumState newState;
        switch(state) {
            case "Arrive":
                newState = ArriveEvent;
                break;
            case "Wait":
                newState = WaitEvent;
                break;
            case "Leave":
                newState = WaitEvent;
                break;
            case "Serve":
                newState = ServeEvent;
                break;
            case "Done":
                newState = DoneEvent;
                break;
            case "Resting":
                newState = RestingEvent;
                break;
            default:
                newState = ArriveEvent;
                break;
        }
        return newState;
    }  
}
