package pl.syberry.themoodbackend.controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.syberry.themoodbackend.model.*;
import pl.syberry.themoodbackend.services.BookService;
import pl.syberry.themoodbackend.services.RestaurantService;
import pl.syberry.themoodbackend.services.UserService;
import pl.syberry.themoodbackend.services.AdminService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Autowired
    EmailSender emailSender;

    @Autowired
    RestaurantService restaurantService;
    @GetMapping("/users/book")
    @ResponseBody
    public ResponseEntity<?> getBooksByDate(@RequestParam String search_username, @RequestParam String username) {
        if (!Objects.equals(username, search_username) && adminService.getAdminByNickName(username) == null) {
            return new ResponseEntity<>("You don't have access!", HttpStatus.METHOD_NOT_ALLOWED);
        }
        List<Book> userBooks = bookService.getBooksByStudentId(userService.getUserByNickName(search_username).getId());
        return new ResponseEntity<>(userBooks, HttpStatus.OK);
    }

    @PostMapping("/users/booking")
    @ResponseBody
    public ResponseEntity<?> createBook(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start_date,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end_date,
                                        @RequestParam String username, @RequestParam String restaurant_name) {
        try {

            if (start_date.compareTo(end_date) > 0) {
                return new ResponseEntity<>("the start date of the reservation must be less than the end date", HttpStatus.BAD_REQUEST);
            }
            if (userService.getUserByNickName(username) == null) {
                return new ResponseEntity<>("This user does not exist in the system", HttpStatus.NOT_FOUND);
            }
            if (restaurantService.getRestaurantByName(restaurant_name) == null) {
                return new ResponseEntity<>("This restaurant does not exist in the system", HttpStatus.NOT_FOUND);
            }
            User user = userService.getUserByNickName(username);
            Restaurant restaurant = restaurantService.getRestaurantByName(restaurant_name);
            for (Book book : restaurant.getBooks()) {
                if (book.getStartDate().compareTo(start_date) >= 0 && book.getStartDate().compareTo(end_date) <= 0) {
                    return new ResponseEntity<>("this time in the restaurant is already taken", HttpStatus.CONFLICT);
                }
                if (book.getEndDate().compareTo(start_date) >= 0 && book.getEndDate().compareTo(end_date) <= 0) {
                    return new ResponseEntity<>("this time in the restaurant is already taken", HttpStatus.CONFLICT);
                }
            }
            for (Book book : user.getBooks()) {
                if (book.getStartDate().compareTo(start_date) >= 0 && book.getStartDate().compareTo(end_date) <= 0) {
                    return new ResponseEntity<>("you already have a reservation for this time", HttpStatus.CONFLICT);
                }
                if (book.getEndDate().compareTo(start_date) >= 0 && book.getEndDate().compareTo(end_date) <= 0) {
                    return new ResponseEntity<>("you already have a reservation for this time", HttpStatus.CONFLICT);
                }
            }
            Book book = new Book(start_date, end_date, user.getId(), restaurant.getId());
            String text = "Hello " + user.getFirstName() + "!\n" +
                    "You have a reservation in restaurant " + restaurant.getName() +
                    " at " + book.getStartDate().getDayOfMonth() + " " + book.getStartDate().getMonth() +
                    " from " + book.getStartDate().getHour() + ":" +
                    book.getStartDate().getMinute() + " to " + book.getEndDate().getHour() + ":" +
                    book.getEndDate().getMinute() + "\n\nWe are waiting for you";
            emailSender.sendEmail(user.getEmail(), "Your reservation", text);
            Functions functions = new Functions();
            SessionFactory factory = functions.createSession();
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            book.setStartDate(book.getStartDate().plusHours(2));
            book.setEndDate(book.getEndDate().plusHours(2));
            session.save(book);
            session.getTransaction().commit();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/list")
    @ResponseBody
    public ResponseEntity<List<User>> getListOfUsers(@RequestParam String admin_name) {
        try {
            if (adminService.getAdminByNickName(admin_name) == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            List<User> users = userService.listAllUser();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            if (userService.getUserByNickName(username) == null && userService.getUserByEmail(username) == null) {
                return new ResponseEntity<>("User with this name/email does not exist in the system",HttpStatus.NOT_FOUND);
            }
            User user = new User();
            if (userService.getUserByNickName(username) != null) {
                user = userService.getUserByNickName(username);
            } else {
                user = userService.getUserByEmail(username);
            }
            if (!Objects.equals(user.getPassword(), password)) {
                return new ResponseEntity<>("Incorrect password",HttpStatus.METHOD_NOT_ALLOWED);
            }
            return new ResponseEntity<>("Welcome", HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users/register")
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String surname,
                                             @RequestParam String username, @RequestParam String password,
                                             @RequestParam String email, @RequestParam String phone) {
        try {
            if (password.length() < 6) {
                return new ResponseEntity<>("Password must have at least 6 characters", HttpStatus.CONFLICT);
            }
            if (phone.length() != 9) {
                return new ResponseEntity<>("Phone must have 9 numbers", HttpStatus.CONFLICT);
            }
            if (userService.getUserByNickName(username) != null) {
                return new ResponseEntity<>("User with this username already exist", HttpStatus.CONFLICT);
            }
            if (userService.getUserByEmail(email) != null) {
                return new ResponseEntity<>("User with this email already exist", HttpStatus.CONFLICT);
            }
            User user = new User(username, name, surname, email, phone, password);
            Functions functions = new Functions();
            SessionFactory factory = functions.createSession();
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}