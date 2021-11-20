package cs2030.simulator;
import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    // no need constructor
    // Compare method 
    // need to override
    // and also need to implement since it is an interface
    // Qn: Why use Comparator and not Comparable interface?
    // Ans: I can use Comparator without modifying existing code/classes
    // aka using the benefits of implementing and using interfaces!
    public int compare(Event i, Event j) {
        double firstEventTime = i.getTime();
        double secondEventTime = j.getTime();
        double difference = firstEventTime - secondEventTime;

        //this implmentation is based on compare() method API
        // the earlier time will have higher priority
        if(difference > 0) {
            return 1;
        } else if(difference < 0) {
            return -1;
        } else {
            // this algo needs to prioritize which event has higher priority
            if(i.getState() == EventEnumState.DoneEvent && j.getState() == EventEnumState.ServeEvent) {
                return -1;
            
            } else if(i.getState() == EventEnumState.DoneEvent && j.getState() == EventEnumState.ArriveEvent) {
                return -1;
            } else if(i.getState() == EventEnumState.ServeEvent && j.getState() == EventEnumState.DoneEvent) {
                return 1;
            } else if(i.getState() == EventEnumState.ArriveEvent && j.getState() == EventEnumState.ServeEvent) {
                return 1;
            } else if(i.getState() == EventEnumState.ServeEvent && j.getState() == EventEnumState.ArriveEvent) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}