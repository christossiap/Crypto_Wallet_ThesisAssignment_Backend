package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.specifications;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CryptoCoinSpecification {
    public static Specification<CryptoCoin> hasName(String name){
        return (Root<CryptoCoin> root,
                CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)->
                criteriaBuilder.equal(root.get("name"),name);
    }

    public static Specification<CryptoCoin> hasSymbol(String symbol){
        return (Root<CryptoCoin> root,
                CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)->
                criteriaBuilder.equal(root.get("symbol"),symbol);
    }

    public static Specification<CryptoCoin> hasPriceGreaterThan(Double price){
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder )->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<CryptoCoin> hasPriceLessThan(Double price){
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder )->
                criteriaBuilder.lessThan(root.get("price"), price);
    }

    public static Specification<CryptoCoin> hasPercentageChange24hGreaterThen(Double percentageChange24h){
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)->
                criteriaBuilder.greaterThan(root.get("percentageChange24h"),percentageChange24h);
    }

    public static Specification<CryptoCoin> hasPercentageChange24hLessThen(Double percentageChange24h){
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)->
                criteriaBuilder.lessThan(root.get("percentageChange24h"),percentageChange24h);
    }

    public static Specification<CryptoCoin> updatedAfter(LocalDateTime dateAfter) {
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdated"), dateAfter);
    }

    public static Specification<CryptoCoin> updatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.between(root.get("lastUpdated"), startDate, endDate);
    }


}
