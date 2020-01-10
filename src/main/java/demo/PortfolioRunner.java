package demo;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static demo.Config.URL_FMT;

public class PortfolioRunner {

    private PortfolioRunner() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Argument needed with the file path");
        }
        BigDecimal equity = new Portfolio(new HttpGateway(URL_FMT)).equity(Files.lines(Paths.get(args[0])));
        System.out.println("Total equity (EUR): " + equity);
    }

}
