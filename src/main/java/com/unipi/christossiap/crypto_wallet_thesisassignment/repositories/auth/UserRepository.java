package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserDetails;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.Role;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
    @Query("SELECT r.name " +
            "FROM Role r " +
            "    JOIN UserRole ur ON r.id= ur.role.id " +
            "    JOIN User u ON u.id = ur.user.id " +
            "WHERE u.id=:userId")
    List<String> findUserRoles(int userId);

    @Query("select new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserDetails(u.username,u.password,u.email,r.name)" +
            "from User u" +
            "   Join UserRole ur on u.id = ur.user.id" +
            "   join Role r on ur.role.id = r.id")
    List<UserDetails> findUserDetails();
}

