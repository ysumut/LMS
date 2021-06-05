/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Admin;
import entity.Student;
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
public class AdminModel {
    private DBConnection db = new DBConnection();
    public DBConnection getDb() {
        return db;
    }
    public void setDb(DBConnection db) {
        this.db = db;
    }
    public void insert( Admin u){
        try{
           Statement st = this.getDb().connect().createStatement(); 
           st.executeUpdate("url");
        }catch(Exception e){
            System.out.println(e.getMessage());
        } 
    }
    public boolean login(Admin a){
        String sorgu = "SELECT * FROM admin WHERE username=? AND password=?";
        try{
            PreparedStatement ps = this.getDb().connect().prepareStatement(sorgu);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
            else return false;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public void ogrenciekle(Student stu){
        UserModel u = new UserModel();
        u.insert(stu);
    }
    public List<User> listele(){
        List<User> ogr = new ArrayList<>();
        UserModel u = new UserModel();
        ogr =u.getList();
        return ogr;
    }
}
