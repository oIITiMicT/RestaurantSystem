package pl.syberry.themoodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.syberry.themoodbackend.model.Book;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByStudentIdIs(int id);

    List<Book> findAllByRestaurantId(int id);
}
