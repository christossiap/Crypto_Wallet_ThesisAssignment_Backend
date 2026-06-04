package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserByCode(String code);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    @Query(value = """
    SELECT r.name
    FROM role r
    JOIN user_role ur ON r.id = ur.role_id
    WHERE ur.user_id = :userId
    """, nativeQuery = true)
    List<String> findUserRoles(int userId);

}

