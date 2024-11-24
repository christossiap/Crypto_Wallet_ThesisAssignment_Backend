package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth;

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
    @Query("SELECT role.name as role " +
            "FROM role " +
            "    JOIN user_role ON role.id=user_role.role_id " +
            "    JOIN user ON user.id = user_role.user_id " +
            "WHERE user.id=:userId")
    List<Role> findUserRoles(int userId);

    @Modifying
    @Query("INSERT INTO user_role (user_id, role_id) VALUES (:userId, :roleId) ")
    void addRoleToUser(int userId, int roleId);

    @Modifying
    default void saveWithRole(User user, Role role) {
        save(user);
        addRoleToUser(user.getId(), role.getId());
    }
}

