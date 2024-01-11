package ru.sumarokov.housing_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.housing_stock.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Boolean existsByName(String name);
}
