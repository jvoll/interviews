package ca.jvoll.coinexchange;

import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Greg Turnquist
 */
class CoinOrderResource extends ResourceSupport {

    private final CoinOrder coinOrder;

    public CoinOrderResource(CoinOrder coinOrder) {
        String username = coinOrder.getAccount().getUsername();
        this.coinOrder = coinOrder;
//        this.add(new Link(coinOrder.getType().toString(), "order-type"));
        this.add(linkTo(CoinOrderRestController.class, username).withRel("orders"));
        this.add(linkTo(methodOn(CoinOrderRestController.class, username)
                .readOrder(username, coinOrder.getId())).withSelfRel());
    }

    public CoinOrder getCoinOrder() {
        return coinOrder;
    }
}
