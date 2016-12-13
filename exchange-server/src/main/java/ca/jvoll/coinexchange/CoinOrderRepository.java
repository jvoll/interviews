package ca.jvoll.coinexchange;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CoinOrderRepository extends JpaRepository<CoinOrder, Long> {
    Collection<CoinOrder> findByAccountUsername(String username);
}