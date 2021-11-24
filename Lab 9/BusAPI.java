import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletableFuture;
import java.util.Optional;

class BusAPI {
    private static final int HTTP_RESPONSE_STATUS_OK = 200;
    private static final String BUS_SERVICE_API = 
        "https://cs2030-bus-api.herokuapp.com/bus_services/";
    private static final String BUS_STOP_API = 
        "https://cs2030-bus-api.herokuapp.com/bus_stops/";

    private static CompletableFuture<String> httpGet(String url) {
        HttpClient client = HttpClient.newBuilder()
            .build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        CompletableFuture<String> responseFinal = client.sendAsync(request, BodyHandlers
            .ofString()).handle((result,e) -> {
                if (Optional.ofNullable(result).isEmpty()) {
                    throw new CompletionException(e);
                } else {
                    return result;
                }
            })
            .thenApply(response -> {
                if (response.statusCode() != HTTP_RESPONSE_STATUS_OK) {
                    System.out.println(response + " " + response.statusCode());
                    return "";
                }
                return response.body();
            });

        return responseFinal;
    }

    public static CompletableFuture<String> getBusStopsServedBy(String serviceId) {
        return httpGet(BUS_SERVICE_API + serviceId);
    }

    public static CompletableFuture<String> getBusServicesAt(String stopId) {
        return httpGet(BUS_STOP_API + stopId);
    }
}
