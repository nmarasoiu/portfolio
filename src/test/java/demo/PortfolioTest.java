package demo;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PortfolioTest {

    private static final BigDecimal zero = new BigDecimal(0);
    private static final Portfolio portfolio = new Portfolio();

    @Test
    public void equityEmpty() {
        Portfolio portfolio = PortfolioTest.portfolio;
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

    @Test(expected = IllegalArgumentException.class)
    public void equityNonExistentCrypto() {
        assertThat(portfolio.equity(Stream.of("a=2")), equalTo(zero));
    }
    @Test
    public void equityOkReqOneCrypto() {
        assertPositive(portfolio.equity(Stream.of("BTC=123")));
    }

    @Test
    public void equityOkReqTowCryptoCoins() {
        assertPositive(portfolio.equity(Stream.of("BTC=123", "XRP=127834")));
    }

    private void assertPositive(BigDecimal equity) {
        assertThat(equity.compareTo(zero), equalTo(1));
    }

}