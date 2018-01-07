package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public interface IUserService {

    void init();
    void loadStubUser();
    void saveUser(User user);
    List<User> getUsers();
    boolean loginUser(User user);
    void setCurrentUser(User user);
    User getCurrentUser();
}
