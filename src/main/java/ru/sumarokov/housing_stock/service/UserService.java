package ru.sumarokov.housing_stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.exception.EntityNotFoundException;
import ru.sumarokov.housing_stock.repository.UserRepository;

import java.io.IOException;
import java.rmi.ServerException;
import java.rmi.server.ServerCloneException;
import java.security.Principal;
import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, userId));
    }

    public User createdUser(User user)  {
        try {
            User userClone = (User) user.clone();
            userClone.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(userClone);
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public User updateUser(User user, Principal principal)  {
        checkWhetherUserCanBeChanged(user.getId(), principal);
        User userUpdate = userRepository.findById(user.getId()).
             orElseThrow(() -> new EntityNotFoundException(User.class, user.getId()));
        try {
            User userClone = (User) user.clone();
            userClone.setPassword(userUpdate.getPassword());
            return userRepository.save(userClone);
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
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
