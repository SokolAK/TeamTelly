package pl.sokolak.teamtally.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByEmail(String email);

    @Modifying
    @Query(value = "UPDATE Users u SET logged = true WHERE id = :id", nativeQuery = true)
    void updateLogged(@Param("id") Integer id);
}