import java.util.List;

class PriorityQueue {
    // This class will keep a collection of customers
    // Each customer will be given a certain priority

    private final List<Event> queue;

    //Constructor
    public PriorityQueue(List<Event> queue){
        this.queue = queue;
    }

    // Methods

    // add method
    void add(Event event) {
        this.queue.add(event);
    }

    void replace(int index,Event event) {
        this.queue.set(index, event);
    }

    // get method
    List<Event> get() {
        return this.queue;
    }

    // poll method
    // retireve and remove the Customer with the highest priority in the queue
    // returns object of type Customer or null if the queue is empty
    Event poll() {
        // if(this.queue.isEmpty()) {
        //     return null;
        // } else {
        //     //if the queue is not empty
        //     // need to use a comparator class
        //     // lets sort the class here first
        //     ArrivalTimeComparator comparator = new ArrivalTimeComparator();
        //     this.queue.sort(comparator);
        //     // the first element will be the earliest time
        //     // and remove and return the first element
        //     Event currentEvent = this.queue.get(0);
        //     // removing and shifting algo is done here already
        //     this.queue.remove(0);
        //     return currentEvent;
        // }
        //if the queue is not empty
        // need to use a comparator class
        // lets sort the class here first
        ArrivalTimeComparator comparator = new ArrivalTimeComparator();
        this.queue.sort(comparator);
        // the first element will be the earliest time
        // and remove and return the first element
        Event currentEvent = this.queue.get(0);
        // removing and shifting algo is done here already
        this.queue.remove(0);
        return currentEvent;
    }   
}
