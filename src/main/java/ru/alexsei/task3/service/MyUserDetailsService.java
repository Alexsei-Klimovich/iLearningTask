package ru.alexsei.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexsei.task3.model.MyUserDetails;
import ru.alexsei.task3.model.User;
import ru.alexsei.task3.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {
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
    public  boolean isEmailExists(String email){
        if (!userRepository.findByEmail(email).isPresent()){
            return false;
        }
        return true;
    }
    public boolean isUserNameExists(String email) {
        if (!userRepository.findByUserName(email).isPresent()){
            return false;
        }
        return true;
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    //updating last activity
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String userName = ((UserDetails) event.getAuthentication().
                getPrincipal()).getUsername();
        User user = userRepository.findByUserName(userName).get();
        updateLastActivity(user);
    }

    public void updateLastActivity(User user){
        user.setLastActivity(new Date());
        userRepository.deleteById(user.getId());
        userRepository.save(user);
    }

    public void lockUserById(Long id){
        User newUser = userRepository.getById(id);
        userRepository.deleteById(id);
        newUser.setEnabled(false);
        userRepository.save(newUser);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (newUser.getUserName().equals(auth.getName())) {
            auth.setAuthenticated(false);
        }
    }

    public void deleteUserById(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = getUserById(id);
        deleteUser(id);
        if (user.getUserName().equals(auth.getName())) {
            auth.setAuthenticated(false);
        }
    }

    public void unlockUserById(Long id){
        User newUser = userRepository.getById(id);
        userRepository.deleteById(id);
        newUser.setEnabled(true);
        userRepository.save(newUser);
    }
}
