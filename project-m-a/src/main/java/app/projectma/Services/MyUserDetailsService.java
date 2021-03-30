package app.projectma.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.projectma.Models.MyUserDetails;
import app.projectma.Models.User;
import app.projectma.Repo.UserDetailsRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userDetailsRepo.findByUserName(name);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + name));

        return user.map(MyUserDetails::new).get();
    }

}
