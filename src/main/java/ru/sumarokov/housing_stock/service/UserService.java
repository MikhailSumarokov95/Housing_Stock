package ru.sumarokov.housing_stock.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.exception.EntityNotFoundException;
import ru.sumarokov.housing_stock.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));
    }

    public User getUser(Principal principal) {
        return userRepository.findByName(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(User.class, principal.getName()));
    }
}
