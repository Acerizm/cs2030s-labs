import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

class BusRoutes {
    private final BusStop stop;
    private final String name;
    private final Map<BusService,CompletableFuture<Set<BusStop>>> services;

    BusRoutes(BusStop stop, String name, Map<BusService,CompletableFuture<Set<BusStop>>> services) {
        this.stop = stop;
        this.name = name;
        this.services = services;
    }

    public CompletableFuture<String> description() {
        String result = "Search for: " + this.stop + " <-> " + name + ":\n" + 
            "From " +  this.stop + "\n";

        CompletableFuture<String> resultF = CompletableFuture.supplyAsync(() -> result);


        for (BusService service: services.keySet()) {
            CompletableFuture<Set<BusStop>> stops = services.get(service);
            resultF = resultF.thenCombine(describeService(service, stops), (x,y) -> x + y);
        }
        return resultF;
    }

    public CompletableFuture<String> describeService(BusService service, 
        CompletableFuture<Set<BusStop>> stops) {
        CompletableFuture<String> r = stops
            .thenApply(x -> {
                if (x.isEmpty()) {
                    return "";
                } else {
                    return x.stream().filter(stop -> stop != this.stop)
                        .reduce("- Can take " + service + " to:\n",
                            (start,stop) -> start += "  - " + stop + "\n",
                            (str1,str2) -> str1 + str2);
                }
            });
        return r;
    }
}
