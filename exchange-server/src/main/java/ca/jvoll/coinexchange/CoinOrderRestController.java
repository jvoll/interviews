package ca.jvoll.coinexchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/{userId}/orders")
class CoinOrderRestController {

    private final CoinOrderRepository coinOrderRepository;

    private final AccountRepository accountRepository;

    @Autowired
    CoinOrderRestController(CoinOrderRepository coinOrderRepository,
                            AccountRepository accountRepository) {
        this.coinOrderRepository = coinOrderRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<CoinOrder> readOrders(@PathVariable String userId) {
        this.validateUser(userId);
        return this.coinOrderRepository.findByAccountUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody CoinOrder input) {
        this.validateUser(userId);

        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    CoinOrder result = coinOrderRepository.save(new CoinOrder(account,
                            input.type, input.quantity, input.priceInCents));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    CoinOrder readOrder(@PathVariable String userId, @PathVariable Long orderId) {
        this.validateUser(userId);
        return this.coinOrderRepository.findOne(orderId);
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
