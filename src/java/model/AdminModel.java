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
    private final DBConnection db = new DBConnection();
    
    public List<Student> getStudents(){
        List<Student> list = new ArrayList<>();
        String sorgu = "SELECT * FROM users WHERE type ='student'";
        try {
            Statement st = this.db.connect().createStatement();
            ResultSet rs = st.executeQuery(sorgu);
            while(rs.next()){
                Student tmp = new Student();
                tmp.setUserId(rs.getInt("id"));
                tmp.setFull_name(rs.getString("full_name"));
                tmp.setEmail(rs.getString("email"));
                tmp.setRegistration_year(rs.getInt("registration_year"));
                tmp.setSemester(rs.getInt("semester"));
                list.add(tmp);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean saveStudent(Student student, String password){
        String sorgu = "INSERT INTO users (full_name, email, password, type, registration_year, semester) "
                + "VALUES (?,?,?,'student',?,?)";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setString(1, student.getFull_name());
            ps.setString(2, student.getEmail());
            ps.setString(3, password);
            ps.setInt(4, student.getRegistration_year());
            ps.setInt(5, student.getSemester());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean removeStudent(Student student){
        String sorgu = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setInt(1, student.getUserId());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
