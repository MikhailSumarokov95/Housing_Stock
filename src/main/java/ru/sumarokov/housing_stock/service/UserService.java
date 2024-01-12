package ru.sumarokov.housing_stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    @Autowired
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

    public User updateUser(User user, Principal principal) {
        checkWhetherUserCanBeChanged(user.getId(), principal);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId, Principal principal) {
        checkWhetherUserCanBeChanged(userId, principal);
        userRepository.deleteById(userId);
    }

    private void checkWhetherUserCanBeChanged(Long changeUserId, Principal principal) {
        User authUser = userRepository.findByName(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(User.class, principal.getName()));
        if (!changeUserId.equals(authUser.getId())) {
            throw new AccessDeniedException("You cannot edit/delete someone else's account");
        }
    }
}
