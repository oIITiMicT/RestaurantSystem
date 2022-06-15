package pl.syberry.themoodbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.syberry.themoodbackend.model.User;
import pl.syberry.themoodbackend.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> listAllUser() {
        return userRepository.findAll();
    }

    public void saveStudent(User student) {
        userRepository.save(student);
}

    public Optional<User> getStudentByID(Integer id) {
        return userRepository.findById(id);
    }

    public User getUserByNickName(String nickname) {
        return userRepository.getUserByNickname(nickname);
    }

    public User getUserByEmail(String email){return userRepository.getUserByEmail(email);}

    public User getUserByPhone(String phone) {return userRepository.getUserByPhone(phone);}


    public void deleteStudent(Integer id) {
        userRepository.deleteById(id);
    }
}
