/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Admin;
import entity.Lecturer;
import entity.Student;
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
    
    public List<String> getReporting(){
        List<String> list = new ArrayList<>();
        try {
            String SQL1 = "SELECT SUM(type = 'student') AS st_count, SUM(type = 'lecturer') AS lec_count, SUM(type = 'admin') AS ad_count FROM users";
            Statement st = this.db.connect().createStatement();
            ResultSet rs = st.executeQuery(SQL1);
            if(rs.next()){
                list.add(rs.getString("st_count"));
                list.add(rs.getString("lec_count"));
                list.add(rs.getString("ad_count"));
            }
            
            String SQL2 = "SELECT COUNT(*) AS dep_count FROM departments";
            Statement st2 = this.db.connect().createStatement();
            ResultSet rs2 = st2.executeQuery(SQL2);
            if(rs2.next()){
                list.add(rs2.getString("dep_count"));
            }
            
            String SQL3 = "SELECT COUNT(*) AS lesson_count FROM lessons";
            Statement st3 = this.db.connect().createStatement();
            ResultSet rs3 = st3.executeQuery(SQL3);
            if(rs3.next()){
                list.add(rs3.getString("lesson_count"));
            }
            
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
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
    
    public List<Lecturer> getLecturers(){
        List<Lecturer> list = new ArrayList<>();
        String sorgu = "SELECT * FROM users WHERE type ='lecturer'";
        try {
            Statement st = this.db.connect().createStatement();
            ResultSet rs = st.executeQuery(sorgu);
            while(rs.next()){
                Lecturer tmp = new Lecturer();
                tmp.setUserId(rs.getInt("id"));
                tmp.setFull_name(rs.getString("full_name"));
                tmp.setEmail(rs.getString("email"));
                tmp.setRegistration_year(rs.getInt("registration_year"));
                list.add(tmp);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Admin> getAdmins(){
        List<Admin> list = new ArrayList<>();
        String sorgu = "SELECT * FROM users WHERE type ='admin'";
        try {
            Statement st = this.db.connect().createStatement();
            ResultSet rs = st.executeQuery(sorgu);
            while(rs.next()){
                Admin tmp = new Admin();
                tmp.setUserId(rs.getInt("id"));
                tmp.setFull_name(rs.getString("full_name"));
                tmp.setEmail(rs.getString("email"));
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
    
    public boolean saveLecturer(Lecturer lecturer, String password){
        String sorgu = "INSERT INTO users (full_name, email, password, type, registration_year) "
                + "VALUES (?,?,?,'lecturer',?)";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setString(1, lecturer.getFull_name());
            ps.setString(2, lecturer.getEmail());
            ps.setString(3, password);
            ps.setInt(4, lecturer.getRegistration_year());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean saveAdmin(Admin admin, String password){
        String sorgu = "INSERT INTO users (full_name, email, password, type) "
                + "VALUES (?,?,?,'admin')";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setString(1, admin.getFull_name());
            ps.setString(2, admin.getEmail());
            ps.setString(3, password);
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean removeUser(int user_id){
        String sorgu = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
            ps.setInt(1, user_id);
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
