package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Integer> {
    WatchList getWatchListByUserId(Integer user_id);
    @Modifying
    @Transactional
    @Query("DELETE FROM WatchList w WHERE w.user.id = :userId")
    void clearWatchListByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT \n" +
            "    u.username AS username,\n" +
            "    c.name AS coin_name\n" +
            "FROM \n" +
            "    watch_list w\n" +
            "JOIN \n" +
            "    user u ON w.user_id = u.id\n" +
            "JOIN \n" +
            "    crypto_coin_watch_list cw ON w.user_id = cw.watchlist_id\n" +
            "JOIN \n" +
            "    crypto_coin c ON c.id = cw.cryptocoin_id\n" +
            "WHERE \n" +
            "    u.id = :user_id;",
            nativeQuery = true)
    List<Object[]> getWatchListInfo(@Param("user_id") Integer user_id, Pageable pageable);

    @Query("select c.name" +
            "   from WatchList w " +
            "join CryptoCoinWatchList cw on w.id = cw.watchList.id" +
            " join CryptoCoin c on c.id = cw.cryptoCoin.id" +
            "   where w.user.id = :user_id")
    List<String> getWatchListCryptoCoinsByUserId(@Param("user_id") Integer user_id);

}
