package demo;

import org.junit.Test;

import javax.net.ssl.SSLSession;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PortfolioUnitTest {

    private static final BigDecimal zero = new BigDecimal(0);
    private static final Portfolio portfolio = new Portfolio(cryptoName -> mockedResponse());
    private static final BigDecimal price = new BigDecimal("2.2");

    @Test
    public void equityEmpty() {
        Portfolio portfolio = PortfolioUnitTest.portfolio;
        assertThat(portfolio.equity(Stream.of()), equalTo(zero));
    }

    @Test(expected = IllegalArgumentException.class)
    public void equityIncorrectLineFormat() {
        assertThat(portfolio.equity(Stream.of("a")), equalTo(zero));
    }

    @Test(expected = IllegalArgumentException.class)
    public void equityIncorrectLineFormat2() {
        assertThat(portfolio.equity(Stream.of("a=")), equalTo(zero));
    }

    @Test(expected = NumberFormatException.class)
    public void equityNumberFormatIssue() {
        assertThat(portfolio.equity(Stream.of("a=a")), equalTo(zero));
    }

    @Test
    public void equityOkReqOneCrypto() {
        assertThat(portfolio.equity(Stream.of("BTC=123")), equalTo(new BigDecimal(123).multiply(price)));
    }
    @Test
    public void equityOkReqTowCryptoCoins() {
        assertThat(portfolio.equity(Stream.of("BTC=123", "XRP=127834")), equalTo(new BigDecimal(123+127834).multiply(price)));
    }

    private static HttpResponse<String> mockedResponse() {
        return new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 200;
            }

            @Override
            public String body() {
                return "{'EUR':2.2}";
            }
            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }


            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}