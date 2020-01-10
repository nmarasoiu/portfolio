package demo;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PortfolioTest {
    @Test
    public void equityEmpty() {
        Portfolio portfolio = new Portfolio();
        assertThat(portfolio.equity(Stream.of()), equalTo(new BigDecimal(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void equityIncorrectLineFormat() {
        Portfolio portfolio = new Portfolio();
        assertThat(portfolio.equity(Stream.of("a")), equalTo(new BigDecimal(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void equityIncorrectLineFormat2() {
        Portfolio portfolio = new Portfolio();
        assertThat(portfolio.equity(Stream.of("a=")), equalTo(new BigDecimal(0)));
    }

    @Test(expected = NumberFormatException.class)
    public void equityNumberFormatIssue() {
        Portfolio portfolio = new Portfolio();
        assertThat(portfolio.equity(Stream.of("a=a")), equalTo(new BigDecimal(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void equityNonExistentCrypto() {
        Portfolio portfolio = new Portfolio();
        assertThat(portfolio.equity(Stream.of("a=2")), equalTo(new BigDecimal(0)));
    }
}