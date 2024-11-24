package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
}
