package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 * UserService
 */

public interface IUserService {

    /**
     * loads the StubUsers
     */
    void loadStubUser();

    /**
     * saves a new User
     * @param user
     */
    void saveUser(User user);

    /**
     * returns all exisiting users.
     * @return
     */
    List<User> getUsers();

    /**
     * checks if the user uses the correct ids to login
     * @param user
     * @return
     */
    boolean loginUser(User user);

    /**
     * sets the current user for this app
     * @param user
     */
    void setCurrentUser(User user);

    /**
     * returns the current User
     * @return
     */
    User getCurrentUser();
}
