package demo;

import java.time.Duration;

//todo from properties?
class Config {
    static final int MIN_JSON_LEN_FOR_ERR = 21;
    static final Duration HTTP_REQ_TIMEOUT = Duration.ofSeconds(30);

    static final String URL_FMT = "https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=EUR";//properties?
}
