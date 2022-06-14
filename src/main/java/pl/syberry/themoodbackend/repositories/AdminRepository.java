package pl.syberry.themoodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.syberry.themoodbackend.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
    Admin getAdminByNickname(String nickname);
}
