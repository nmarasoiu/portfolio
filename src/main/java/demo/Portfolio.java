package demo;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Portfolio {
    private static final String URL_FMT = "https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=EUR";//properties?
    private static final BigDecimal zero = new BigDecimal(0);
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    BigDecimal equity(Stream<String> lines) {
        return lines.reduce(zero, this::reducer, BigDecimal::add);
    }

    private BigDecimal reducer(BigDecimal acc, String line) {
        String[] keyValue = line.split("=");
        if (keyValue.length != 2) {
            throw new IllegalArgumentException("The line format is similar to BTC=12 meaning having 12 BTC in portfolio");
        }
        String name = keyValue[0].trim();
        long count = Long.parseLong(keyValue[1].trim());
        BigDecimal price = price(name);//not so ok in map (effect)
        BigDecimal totalPosition = new BigDecimal(count).multiply(price);
        System.out.println(name + ": " + count + " * " + price + " = " + totalPosition);//not so ok in map (effect)
        return acc.add(totalPosition);
    }

    private BigDecimal price(String cryptoName) {
        HttpResponse<String> response = sendGetRequest(cryptoName);
        String json = response.body();
        int status = response.statusCode();
        if (status >= 400 && status < 500) {
            throw new IllegalArgumentException(json);//non existent crypto name or other client side request issue
        }
        if (status >= 200 && status < 300) {
            return new BigDecimal(json.substring(7, json.length() - 1));
        }
        throw new IllegalStateException("The were issues, please retry. Status code: " + status +
                "Error message on the http response is: " + json);
    }

    private HttpResponse<String> sendGetRequest(String cryptoName) {
        URI uri = URI.create(String.format(URL_FMT, cryptoName));
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
