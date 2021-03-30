package app.projectma.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.projectma.Models.User;
import app.projectma.Repo.UserDetailsRepo;

@Service
public class UserService {
    @Autowired
    UserDetailsRepo userDetailsRepo;

    public String addUser(User user) {
        Optional<User> newUser = userDetailsRepo.findByUserName(user.getUserName());
        if (newUser.isPresent()) {
            return "the username '" + user.getUserName() + "' exists,try another one";
        }
        userDetailsRepo.save(user);
        return "";
    }

    public Long Userexists(String userName) {
        Optional<User> User = userDetailsRepo.findByUserName(userName);
        if (User.isPresent()) {
            return User.get().getId();
        }
        return 0L;
    }

    public User getUserByUsername(String userName) {
        Optional<User> User = userDetailsRepo.findByUserName(userName);
        if (User.isPresent()) {
            return User.get();
        }
        return null;
    }

    public List<String> getCollaboratorsById(List<Long> ids) {
        if (!ids.isEmpty()) {
            List<String> res = new ArrayList<String>();
            List<User> users = userDetailsRepo.findAllById(ids);
            for (User user : users) {
                res.add(user.getUserName());
            }
            return res;
        }
        return null;
    }
}
