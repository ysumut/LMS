package model;

import entity.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
}
