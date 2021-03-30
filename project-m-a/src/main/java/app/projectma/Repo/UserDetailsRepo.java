package app.projectma.Repo;

import app.projectma.Models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

}
