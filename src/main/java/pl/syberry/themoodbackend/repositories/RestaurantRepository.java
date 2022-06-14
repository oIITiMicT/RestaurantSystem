package pl.syberry.themoodbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.syberry.themoodbackend.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{
    Restaurant findRestaurantByName(String name);

    Restaurant getRestaurantByName(String name);
}
