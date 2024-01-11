package ru.sumarokov.housing_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sumarokov.housing_stock.entity.House;

public interface HouseRepository extends JpaRepository<House, Long> {
}
