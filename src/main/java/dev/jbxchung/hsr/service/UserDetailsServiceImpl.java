package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with account name " + accountName));

        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserByEmailAddress(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email address " + emailAddress));

        return UserDetailsImpl.build(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User user) {
        // todo - handle error cases (e.g. user already exists)
        return userRepository.save(user);
    }

    public User deleteUser(String username) {
        // todo - handle error cases
        User user = userRepository.findByAccountName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with account name " + username));

        // todo - make sure any lazy fields are instantiated before deletion so this can be returned
        userRepository.delete(user);

        return user;
    }
}
