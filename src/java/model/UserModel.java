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
    private DBConnection db = new DBConnection();

    public DBConnection getDb() {
        return db;
    }

    public void setDb(DBConnection db) {
        this.db = db;
    }
    public void insert( User u){
        try{
           Statement st = this.getDb().connect().createStatement(); 
           st.executeUpdate("url");
        }catch(Exception e){
            System.out.println(e.getMessage());
        } 
    }
    public User login(String email, String password){
        String sorgu = "SELECT * FROM users WHERE email=? AND password=?";
        
        try{
            PreparedStatement ps = this.getDb().connect().prepareStatement(sorgu);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String type = rs.getString("type");
                User user;
                
                if(type.equals("student")) {
                    user = new Student(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getInt("semester"), rs.getInt("registration_year"));
                    user.setStatus(true);
                    return user;
                }
                else if(type.equals("lecturer")) {
                    user = new Lecturer(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getInt("registration_year"));
                    user.setStatus(true);
                    return user;
                }
                else return new User();
            }
            else return new User();
        }
        catch(SQLException e){
            System.out.println("DB ERROR: " + e.getMessage());
            return new User();
        }
    }
    public List<User> getList(){
        List<User> uList = new ArrayList<>();
        try{
           Statement st = this.getDb().connect().createStatement(); 
           ResultSet rs = st.executeQuery("SELECT * FROM users");
           while(rs.next()){
               User u = new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getString("type"));
               uList.add(u);
           }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return uList;
    }
}
