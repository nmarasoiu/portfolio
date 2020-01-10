package demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpGateway implements PriceProvider {

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final String urlFormat;

    public HttpGateway(String urlFormat) {
        this.urlFormat = urlFormat;
    }

    public HttpResponse<String> getPrice(String cryptoName) {
        URI uri = URI.create(String.format(urlFormat, cryptoName));
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());//sync for this simple case, there aren't many crypto coins traded
        } catch (IOException e) {
            throw new IllegalStateException("Problem with the HTTP round trip to get the price for " + cryptoName, e);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted", e);
        }
    }
}
