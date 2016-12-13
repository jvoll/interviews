package ca.jvoll.coinexchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeApplication.class, args);
	}

	@Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           CoinOrderRepository coinOrderRepository) {
	    return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Arrays.asList(
                        "al,bob,carl".split(","))
                        .forEach(
                                a -> {
                                    Account account = accountRepository.save(new Account(a,
                                            "password"));
                                    coinOrderRepository.save(new CoinOrder(account, CoinOrder.Type.BUY, 10, 200));
                                });

                Arrays.asList(
                        "john,ken,lenny".split(","))
                        .forEach(
                                a -> {
                                    Account account = accountRepository.save(new Account(a,
                                            "password"));
                                    coinOrderRepository.save(new CoinOrder(account, CoinOrder.Type.SELL, 10, 400));
                                });
            }
        };
	}
}
