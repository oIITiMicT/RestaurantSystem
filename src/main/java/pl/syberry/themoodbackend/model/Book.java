package pl.syberry.themoodbackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name= "end_date")
    private LocalDateTime endDate;

    @Column(name = "user_id")
    private int studentId;

    @Column(name = "restaurant_id")
    private int restaurantId;


    public Book() {
    }

    public Book(LocalDateTime startDate, LocalDateTime endDate, int studentId, int restaurantId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.studentId = studentId;
        this.restaurantId = restaurantId;
    }
}