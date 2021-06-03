/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public String login(User u){
        String sorgu = "SELECT * FROM users WHERE email=? AND password=?";
        
        try{
            PreparedStatement ps = this.getDb().connect().prepareStatement(sorgu);
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString("type");
            }
            else return "false";
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return "false";
        }
    }
    public List<User> getList(){
        List<User> uList = new ArrayList<>();
        try{
           Statement st = this.getDb().connect().createStatement(); 
           ResultSet rs = st.executeQuery("SELECT * FROM users");
           while(rs.next()){
               User u = new User(rs.getString("email"), rs.getString("password"), rs.getInt("id"));
               uList.add(u);
           }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return uList;
    }
}
