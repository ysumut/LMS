package model;

import entity.Lecturer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LecturerModel {
    private DBConnection db = new DBConnection();
    
    public Lecturer getLecturerByID(int id){
        String sorgu = "SELECT u.*, GROUP_CONCAT(d.name SEPARATOR ',') AS departs "
                + "FROM users u "
                + "LEFT JOIN user_departments u_d ON u.id = u_d.user_id "
                + "LEFT JOIN departments d ON u_d.department_id = d.id "
                + "WHERE u.id = ? "
                + "GROUP BY u.id";
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           
           if(rs.next()){
               List<String> departs = Arrays.asList(rs.getString("departs").split(","));
               
               Lecturer lecturer = new Lecturer(rs.getInt("id"),rs.getString("full_name"),rs.getString("email"),rs.getInt("registration_year"));
               lecturer.setDepartments(departs);
               return lecturer;
           }
           else return new Lecturer();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return new Lecturer();
        }
    }
    
    public List<List<String>> getStudents(int id){
        String sorgu = "SELECT u.id, u.full_name, u.email, COUNT(l.id) AS lesson_count, GROUP_CONCAT(l.id SEPARATOR ',') AS lesson_ids, GROUP_CONCAT(l.name SEPARATOR ',') AS lesson_names "
                + "FROM users u "
                + "LEFT JOIN user_lessons u_l ON u.id = u_l.user_id "
                + "LEFT JOIN lessons l ON u_l.lessons_id = l.id "
                + "WHERE l.id IN (SELECT u_l.lessons_id FROM users u INNER JOIN user_lessons u_l ON u.id = u_l.user_id WHERE u.id = ?) AND u.id != ? "
                + "GROUP BY u.id";
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, id);
           ps.setInt(2, id);
           ResultSet rs = ps.executeQuery();
           
           List<List<String>> students = new ArrayList<>();
           while(rs.next()){
               List<String> l = new ArrayList<>();
               l.add(rs.getString("id"));
               l.add(rs.getString("full_name"));
               l.add(rs.getString("email"));
               l.add(rs.getString("lesson_count"));
               l.add(rs.getString("lesson_ids"));
               l.add(rs.getString("lesson_names"));
               students.add(l);
           }
           
           return students;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean saveStudentNote(int user_id, int lesson_id, int midterm_note, int final_note) {
        String sorgu = "UPDATE user_lessons SET midterm_note = ?, final_note = ? "
                + "WHERE user_id = ? AND lessons_id = ?";
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, midterm_note);
           ps.setInt(2, final_note);
           ps.setInt(3, user_id);
           ps.setInt(4, lesson_id);
           
           return ps.executeUpdate() > 0;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public List<Integer> getLessonNotes(int user_id, int lesson_id){
        String sorgu = "SELECT * "
                + "FROM user_lessons u_l "
                + "WHERE u_l.user_id = ? AND u_l.lessons_id = ?";
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, user_id);
           ps.setInt(2, lesson_id);
           ResultSet rs = ps.executeQuery();
           
           List<Integer> lesson = new ArrayList<>();
           if(rs.next()){
               lesson.add(rs.getInt("midterm_note"));
               lesson.add(rs.getInt("final_note"));
           }
           
           return lesson;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<List<String>> getLessons(int id){
        String sorgu = "SELECT l.* "
                + "FROM users u "
                + "LEFT JOIN user_lessons u_l ON u.id = u_l.user_id "
                + "LEFT JOIN lessons l ON u_l.lessons_id = l.id "
                + "WHERE u.id = ?";
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();
           
           List<List<String>> lessons = new ArrayList<>();
           while(rs.next()){
               List<String> l = new ArrayList<>();
               l.add(rs.getString("name"));
               l.add(rs.getString("is_common"));
               l.add(rs.getString("credit"));
               l.add(rs.getString("akts"));
               l.add(rs.getString("language"));
               lessons.add(l);
           }
           
           return lessons;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
