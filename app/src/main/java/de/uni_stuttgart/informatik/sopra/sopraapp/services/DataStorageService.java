package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.content.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Defines how the data will be called and
 * saved.
 */

public class DataStorageService {

    public static DataStorageService instance = null;
    private List<User> stubUser = new ArrayList<>();
    private List<Field> allFields = new ArrayList<>();
    private Connection connection;


    private DataStorageService() {
        addStubUsers();
        try {
            DriverManager.getConnection("jdbc:derby:AppDB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataStorageService getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new DataStorageService();

        }
        //AppDatabase appDatabase = Room.databaseBuilder(applicationContext, AppDatabase.class,"app-database").build();
        return instance;
    }

    private void addStubUsers() {
        this.stubUser.add(new User("aa", "a"));

        this.stubUser.add(new User("admin2", "admin"));
        this.stubUser.add(new User("", ""));

        stubUser.get(0).setUserRole(UserRole.LANDWIRT);
        stubUser.get(1).setUserRole(UserRole.GUTACHTER);
        stubUser.get(2).setUserRole(UserRole.LANDWIRT);

    }

    /**
     * Stubusers
     *
     * @return
     */
    public List<User> getStubUsers() {
        return stubUser;
    }

    public List getAllFieldsBy(User user) {
        Statement s = null;
        try {
            s = connection.createStatement();

            String sql = "SELECT * FROM Field ";
            switch (user.getCurrentUserRole()) {

                case LANDWIRT:
                    sql += "WHERE owner ==" + user.getName();
                    break;
                case GUTACHTER:
                    break;
            }
            ResultSet result = s.executeQuery(sql);
            while (result.next()) {

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Field> getAllFieldsFromEveryUser() {

        return null;
    }
}
