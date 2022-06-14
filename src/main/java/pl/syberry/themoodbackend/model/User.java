package pl.syberry.themoodbackend.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
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

    public User() {

    }

    public User(String nickname, String firstName, String lastName, String email, String phone, String password) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
