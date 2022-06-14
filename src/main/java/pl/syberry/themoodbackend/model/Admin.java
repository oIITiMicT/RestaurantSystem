package pl.syberry.themoodbackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="admin")
@Getter
@Setter
@ToString
public class Admin {
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

    public Admin() {

    }

    public Admin(String nickname, String firstName, String lastName) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
