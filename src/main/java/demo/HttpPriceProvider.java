package demo;

import java.net.http.HttpResponse;

public interface HttpPriceProvider {//potentially an Http-agnostic price provider may help in reality, but we keep it more simple
    HttpResponse<String> getPrice(String cryptoName);
}
