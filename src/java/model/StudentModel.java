package model;

import entity.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentModel {
    private DBConnection db = new DBConnection();

    public DBConnection getDb() {
        return db;
    }

    public void setDb(DBConnection db) {
        this.db = db;
    }
    
    public void insert(Student stu){
        try{
           Statement st = this.getDb().connect().createStatement(); 
           st.executeUpdate("url");
        }catch(Exception e){
            System.out.println(e.getMessage());
        } 
    }
    
    public Student getStudentByID(int id){
        String sorgu = "SELECT u.*, GROUP_CONCAT(d.name SEPARATOR ',') AS departs "
                + "FROM users u "
                + "LEFT JOIN user_departments u_d ON u.id = u_d.user_id "
                + "LEFT JOIN departments d ON u_d.department_id = d.id "
                + "WHERE u.id = ? "
                + "GROUP BY u.id";
        try{
           PreparedStatement ps = this.getDb().connect().prepareStatement(sorgu);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           
           if(rs.next()){
               List<String> departs = Arrays.asList(rs.getString("departs").split(","));
               
               Student student = new Student(rs.getInt("id"),rs.getString("email"),rs.getInt("semester"),rs.getInt("registration_year"));
               student.setDepartments(departs);
               return student;
           }
           else return new Student();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new Student();
        }
    }
    
    public List<List<String>> getLessonNotes(int id){
        String sorgu = "SELECT l.name, u_l.midterm_note, u_l.final_note "
                + "FROM users u "
                + "LEFT JOIN user_lessons u_l ON u.id = u_l.user_id "
                + "LEFT JOIN lessons l ON u_l.lessons_id = l.id "
                + "WHERE u.id = ?";
        try{
           PreparedStatement ps = this.getDb().connect().prepareStatement(sorgu);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           
           List<List<String>> lessons = new ArrayList<>();
           while(rs.next()){
               List<String> l = new ArrayList<>();
               l.add(rs.getString("name"));
               l.add(rs.getString("midterm_note"));
               l.add(rs.getString("final_note"));
               lessons.add(l);
           }
           
           return lessons;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
