package demo;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static demo.Config.URL_FMT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PortfolioIntegrationTest {

    private static final BigDecimal zero = new BigDecimal(0);
    private static final Portfolio portfolio = new Portfolio(new HttpGateway(URL_FMT));

    @Test
    public void equityNonExistentCrypto() {
        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class,
                () -> new Portfolio(new HttpGateway(URL_FMT)).equity(Stream.of("a=2")));
        assertThat(exception.getMessage().matches("\\{.+\\}"), equalTo(true));
    }

    @Test
    public void equityPositive() {
        assertThat(portfolio.equity(Stream.of("BTC=2")).compareTo(zero), equalTo(1));
        assertThat(portfolio.equity(Stream.of("BTC=1", "XRP=127834")).compareTo(zero), equalTo(1));
    }

}