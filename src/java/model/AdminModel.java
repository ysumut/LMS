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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yahya
 */
public class AdminModel {
    private DBConnection db = new DBConnection();
    
    public void insert( Admin u){
        try{
           Statement st = this.db.connect().createStatement(); 
           st.executeUpdate("url");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } 
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
