package com.SmartHealthRemoteSystem.SHSR.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String updateUser(User user) throws ExecutionException, InterruptedException {
        return userRepository.saveUser(user);
    }

    public Boolean createUser(User user) throws ExecutionException, InterruptedException {
        //to check whether userId is taken or not
        List<User> userList = userRepository.getListUser();
        for(User user1:userList){
            if(user.getUserId().equals(user1.getUserId())){
                return false; //return false if it failed to create user
            }
        }
        userRepository.saveUser(user);
        return true; //return true if it successfully created the user
    }

    public User getUser(String userId){
        return userRepository.getUser(userId);
    }

    public String deleteUser(String userId) throws ExecutionException, InterruptedException {
        return userRepository.deleteUser(userId);
    }
}
