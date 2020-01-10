package demo;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

import static demo.Config.MIN_JSON_LEN_FOR_ERR;

class Portfolio {
    private static final BigDecimal zero = new BigDecimal(0);
    private final HttpPriceProvider priceProvider;

    Portfolio(HttpPriceProvider priceProvider) {
        this.priceProvider = priceProvider;
    }

    BigDecimal equity(Stream<String> lines) {
        return lines.reduce(zero, this::reducer, BigDecimal::add);
    }

    private BigDecimal reducer(BigDecimal acc, String line) {
        String[] keyValue = line.split("=");
        if (keyValue.length != 2) {
            throw new IllegalArgumentException("The line format is similar to BTC=12 meaning having 12 BTC in portfolio: line=" + line);
        }
        String name = keyValue[0].trim();
        long count = Long.parseLong(keyValue[1].trim());
        BigDecimal price = price(name);//not so ok in map (effect)
        BigDecimal totalPosition = new BigDecimal(count).multiply(price);
        System.out.println(name + ": " + count + " * " + price + " = " + totalPosition);//not so ok in map (effect)
        return acc.add(totalPosition);
    }

    private BigDecimal price(String cryptoName) {
        HttpResponse<String> response = priceProvider.getPrice(cryptoName);
        String json = response.body();
        int status = response.statusCode();
        if ((status >= 400 && status < 500) || json.length() > MIN_JSON_LEN_FOR_ERR) {
            throw new IllegalArgumentException(json);//non existent crypto name or other client side request issue
        }
        if (status >= 200 && status < 300) {
            return new BigDecimal(json.substring(7, json.length() - 1));//normally extract thru pattern matching
        }
        throw new IllegalStateException("The were issues, please retry. Status code: " + status +
                "Error message on the http response is: " + json);
    }

}
