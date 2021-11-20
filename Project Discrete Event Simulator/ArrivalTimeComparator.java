import java.util.Comparator;

class ArrivalTimeComparator implements Comparator<Event> {
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
            return 0;
        }
    }
}
