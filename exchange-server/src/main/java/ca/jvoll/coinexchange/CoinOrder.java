package ca.jvoll.coinexchange;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CoinOrder {

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Id
    @GeneratedValue
    private Long id;

    public enum Type {
        BUY,
        SELL
    }

    public Type type;
    public int quantity;
    public int priceInCents;

    CoinOrder() { // jpa only
    }

    public CoinOrder(Account account, Type type, int quantity, int priceInCents) {
        this.account = account;
        this.type = type;
        this.quantity = quantity;
        this.priceInCents = priceInCents;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPriceInCents() {
        return priceInCents;
    }
}