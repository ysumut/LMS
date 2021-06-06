/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Lecturer;
import entity.Student;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yahya
 */
public class UserModel {

    private final DBConnection db = new DBConnection();

    public void insert(User u) {
        try {
            Statement st = this.db.connect().createStatement();
            st.executeUpdate("url");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User login(String email, String password) {
        String sorgu = "SELECT * FROM users WHERE email=? AND password=?";

        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                User user;

                if (type.equals("student")) {
                    user = new Student(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getInt("semester"), rs.getInt("registration_year"));
                    user.setStatus(true);
                    return user;
                } else if (type.equals("lecturer")) {
                    user = new Lecturer(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getInt("registration_year"));
                    user.setStatus(true);
                    return user;
                } else {
                    return new User();
                }
            } else {
                return new User();
            }
        } catch (SQLException e) {
            System.out.println("DB ERROR: " + e.getMessage());
            return new User();
        }
    }

    public List<User> getList() {
        List<User> uList = new ArrayList<>();
        try {
            Statement st = this.db.connect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User u = new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getString("type"));
                uList.add(u);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return uList;
    }

    public boolean updateUser(User user) {
        String sorgu = "UPDATE users SET full_name=?, email=? WHERE id = ?";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setString(1, user.getFull_name());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String updatePassword(User user) {
        String SELECT_PASS_SQL = "SELECT password FROM users WHERE id = ?";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(SELECT_PASS_SQL);
            ps.setInt(1, user.getUserId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString("password").equals(user.getOld_pass())) {
                    String UPDATE_PASS_SQL = "UPDATE users SET password = ? WHERE id = ?";
                    PreparedStatement ps2 = this.db.connect().prepareStatement(UPDATE_PASS_SQL);
                    ps2.setString(1, user.getNew_pass());
                    ps2.setInt(2, user.getUserId());

                    return ps2.executeUpdate() > 0 ? "true" : "false";
                } else {
                    return "Eski şifre doğru değil!";
                }
            } else {
                return "Bir hata ile karşılaşıldı!";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Bir hata ile karşılaşıldı!";
        }
    }
}
