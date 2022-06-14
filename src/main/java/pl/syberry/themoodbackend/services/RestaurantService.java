package pl.syberry.themoodbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.syberry.themoodbackend.model.Restaurant;
import pl.syberry.themoodbackend.model.User;
import pl.syberry.themoodbackend.repositories.RestaurantRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Restaurant> listAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantByName(String name){return restaurantRepository.getRestaurantByName(name);}
}
