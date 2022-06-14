package pl.syberry.themoodbackend.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@ToString
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private List<Book> bookList;

    public void addBook(Book book) {
        if (bookList == null) {
            bookList = new ArrayList<>();
        }

        bookList.add(book);
    }

    public Book[] getBooks() {
        return bookList.toArray(new Book[0]);
    }

    public Restaurant() {

    }

    public Restaurant(String name) {
        this.name = name;
    }
}
