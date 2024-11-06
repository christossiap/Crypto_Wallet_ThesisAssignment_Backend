package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.specifications;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CryptoCoinSpecification {
    public static Specification<CryptoCoin> hasName(String name){
        return (Root<CryptoCoin> root,
                CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)->{
                return criteriaBuilder.equal(root.get("name"),name);
        };
    }

    public static Specification<CryptoCoin> hasSymbol(String symbol){
        return (Root<CryptoCoin> root,
                CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)->{
            return criteriaBuilder.equal(root.get("symbol"),symbol);
        };
    }

    public static Specification<CryptoCoin> hasPriceGreaterThan(Double price){
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder )->{
            return criteriaBuilder.greaterThan(root.get("price"), price);
        };
    }

    public static Specification<CryptoCoin> hasPriceLessThan(Double price){
        return (Root<CryptoCoin> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder )->{
            return criteriaBuilder.lessThan(root.get("price"), price);
        };
    }

}
