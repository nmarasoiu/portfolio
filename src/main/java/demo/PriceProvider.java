package demo;

import java.net.http.HttpResponse;

public interface PriceProvider {
    HttpResponse<String> getPrice(String cryptoName);
}
