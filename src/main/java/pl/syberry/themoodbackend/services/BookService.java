package pl.syberry.themoodbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.syberry.themoodbackend.model.Book;
import pl.syberry.themoodbackend.repositories.BookRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooksByStudentId(int id) {
        return bookRepository.findAllByStudentIdIs(id);
    }

    public List<Book> getBooksByRestaurantId(int id) {
        return bookRepository.findAllByRestaurantId(id);
    }
}
