package pl.syberry.themoodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.syberry.themoodbackend.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    User getUserByNickname(String nickname);
    User getUserByEmail(String email);
}
