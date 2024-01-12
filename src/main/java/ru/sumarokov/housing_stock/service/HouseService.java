package ru.sumarokov.housing_stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.sumarokov.housing_stock.entity.House;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.exception.EntityNotFoundException;
import ru.sumarokov.housing_stock.repository.HouseRepository;
import ru.sumarokov.housing_stock.repository.UserRepository;

@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository, UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    public House getHouse(Long houseId) {
        return houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException(House.class, houseId));
    }

    public House createHouse(House house, Long userId) {
        if (house.getId() != null) {
            throw new IllegalArgumentException("The new house id must be null");
        } else if (!house.getOwnerId().equals(userId)) {
            throw new AccessDeniedException("You can't create a home for another person");
        }
        return houseRepository.save(house);
    }

    public House updateHouse(House house, Long userId) {
        if (house.getId() == null) {
            throw new IllegalArgumentException("An existing house's id must not be null");
        }
        checkWhetherHouseCanBeChanged(house.getId(), userId);
        return houseRepository.save(house);
    }

    public void deleteHouse(Long houseId, Long userId) {
        checkWhetherHouseCanBeChanged(houseId, userId);
        houseRepository.deleteById(houseId);
    }

    public void addTenantToHouse(Long houseId, Long tenantId, Long userId) {
        checkWhetherHouseCanBeChanged(houseId, userId);
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, tenantId));
        tenant.setHouseId(houseId);
        userRepository.save(tenant);
    }

    private void checkWhetherHouseCanBeChanged(Long houseId, Long userId) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new EntityNotFoundException(House.class, houseId));
        if (!house.getOwnerId().equals(userId)) {
            throw new AccessDeniedException("You cannot edit/delete other people's house");
        }
    }
}
