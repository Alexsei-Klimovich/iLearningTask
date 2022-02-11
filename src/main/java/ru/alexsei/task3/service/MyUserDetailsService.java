package ru.alexsei.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexsei.task3.model.MyUserDetails;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.repository.UserRepository;

import java.util.List;
import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found: " + username));
        return user.map(MyUserDetails::new).get();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        User user = userRepository.getById(id);
        return user;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void createUser(User user){
        userRepository.save(user);
    }
}
