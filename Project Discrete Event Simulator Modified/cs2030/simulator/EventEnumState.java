package cs2030.simulator;

public enum EventEnumState {
    ArriveEvent,
    WaitEvent,
    LeaveEvent,
    DoneEvent,
    ServeEvent;

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
            case "Serve":
                newState = ServeEvent;
            case "Done":
                newState = DoneEvent;
            default:
                newState = ArriveEvent;
        }
        return newState;
    }  
}
