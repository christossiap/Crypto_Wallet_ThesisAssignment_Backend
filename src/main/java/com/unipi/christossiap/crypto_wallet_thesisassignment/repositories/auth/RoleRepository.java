package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleByName(String name);
}
