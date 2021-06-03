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
    public boolean login(User u){
        String sorgu = "SELECT * FROM user WHERE tc=? AND sifre=?"; // dğişecek
        try{
            PreparedStatement ps = this.getDb().connect().prepareStatement(sorgu);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public List<User> getList(){
        List<User> uList = new ArrayList<>();
        try{
           Statement st = this.getDb().connect().createStatement(); 
           ResultSet rs = st.executeQuery("sorgu");
           while(rs.next()){
               User u = new User("username","password",1);// değişecek bölge rs.getString(usernamegibi gibi
               uList.add(u);
           }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return uList;
    }
}
