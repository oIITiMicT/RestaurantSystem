package pl.syberry.themoodbackend.controllers;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.syberry.themoodbackend.model.*;
import pl.syberry.themoodbackend.services.AdminService;
import pl.syberry.themoodbackend.services.BookService;
import pl.syberry.themoodbackend.services.RestaurantService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @GetMapping("restaurant/list")
    @ResponseBody
    public ResponseEntity<List<Restaurant>> getListOfRestaurants(@RequestParam String admin_name) {
        try {
            if (adminService.getAdminByNickName(admin_name) == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            List<Restaurant> restaurants = restaurantService.listAllRestaurants();
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        @PostMapping("restaurant/register")
    @ResponseBody
    public ResponseEntity<String> registerRestaurant(@RequestParam String admin_name, @RequestParam String restaurant_name) {
        try {
            if (adminService.getAdminByNickName(admin_name) == null) {
                return new ResponseEntity<>("you don't have permissions", HttpStatus.METHOD_NOT_ALLOWED);
            }

            if (restaurantService.getRestaurantByName(restaurant_name) != null) {
                return new ResponseEntity<>("a restaurant with the same name already exists", HttpStatus.CONFLICT);
            }
            Restaurant restaurant = new Restaurant(restaurant_name);
            Functions functions = new Functions();
            SessionFactory factory = functions.createSession();
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(restaurant);
            session.getTransaction().commit();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/restaurant/book")
    @ResponseBody
    public ResponseEntity<?> getListOfBooks(@RequestParam String restaurant_name, @RequestParam String admin_username) {
        try {
            if (adminService.getAdminByNickName(admin_username) == null) {
                return new ResponseEntity<>("You don't have access!", HttpStatus.METHOD_NOT_ALLOWED);
            }
            if (restaurantService.getRestaurantByName(restaurant_name) == null) {
                return new ResponseEntity<>("a restaurant with the this name not exist in system", HttpStatus.NOT_FOUND);
            }
            List<Book> books = bookService.getBooksByRestaurantId(restaurantService.getRestaurantByName(restaurant_name).getId());
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
