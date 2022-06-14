package pl.syberry.themoodbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.syberry.themoodbackend.model.Admin;
import pl.syberry.themoodbackend.repositories.AdminRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    public List<Admin> listAllAdmins() {
        return adminRepository.findAll();
    }

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Optional<Admin> getAdminByID(Integer id) {
        return adminRepository.findById(id);
    }

    public Admin getAdminByNickName(String nickname) {
        return adminRepository.getAdminByNickname(nickname);
    }

    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }
}
