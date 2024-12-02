package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.associations;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinPortfolio;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Port;
import java.util.List;

@Repository
public interface CryptoCoinPortfolioRepository extends JpaRepository<CryptoCoinPortfolio,Integer> {

    @Query("select CryptoCoinPortfolio.coinAmount" +
            "   from Portfolio p " +
            "join CryptoCoinPortfolio cp on p.id = cp.portfolio.id" +
            "   join CryptoCoin c on c.id = cp.cryptoCoin.id" +
            "   where c.id=:coin_id" +
            "   and p.id=:p_id")
    Double findCoinAmountByCoinIdAndPortfolioId(@Param("coin_id")Integer coin_id, @Param("p_id") Integer p_id);

    CryptoCoinPortfolio findByPortfolioAndCryptoCoin(Portfolio portfolio, CryptoCoin coin);

    List<CryptoCoinPortfolio> findByPortfolio(Portfolio portfolio);
}
