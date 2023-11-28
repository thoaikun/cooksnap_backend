package com.cooksnap.backend.repositories;

import java.util.List;
import java.util.Optional;

import com.cooksnap.backend.domains.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = "select t from Token t inner join User u on t.user.userId = u.userId where u.userId = :id and (t.expired = false or t.revoked = false)")
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);
}
