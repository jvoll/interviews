package ca.jvoll.coinexchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<CoinOrderResource> readOrders(@PathVariable String userId) {
        this.validateUser(userId);

        List<CoinOrderResource> bookmarkResourceList = coinOrderRepository
                .findByAccountUsername(userId).stream().map(CoinOrderResource::new)
                .collect(Collectors.toList());

        return new Resources<>(bookmarkResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody CoinOrder input) {
        this.validateUser(userId);

        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    CoinOrder result = coinOrderRepository.save(new CoinOrder(account,
                            input.type, input.quantity, input.priceInCents));

                    Link forOneBookmark = new CoinOrderResource(result).getLink("self");

                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    CoinOrderResource readOrder(@PathVariable String userId, @PathVariable Long orderId) {
        this.validateUser(userId);
        return new CoinOrderResource(this.coinOrderRepository.findOne(orderId));
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
