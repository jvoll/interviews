package ca.jvoll.coinexchange;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * @author Greg Turnquist
 */
class CoinOrderResource extends ResourceSupport {

    private final CoinOrder coinOrder;

    public CoinOrderResource(CoinOrder coinOrder) {
        String username = coinOrder.getAccount().getUsername();
        this.coinOrder = coinOrder;
//        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
//        this.add(linkTo(BookmarkRestController.class, username).withRel("bookmarks"));
//        this.add(linkTo(methodOn(BookmarkRestController.class, username)
//                .readBookmark(username, bookmark.getId())).withSelfRel());
    }

    public CoinOrder getCoinOrder() {
        return coinOrder;
    }
}
