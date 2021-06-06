package model;

import entity.Message;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageModel {
    private final DBConnection db = new DBConnection();
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
    
    public List<List<String>> getStudentMessages(int user_id){
        String sorgu = "SELECT m.title, m.content, m.created_at, u.full_name AS lecturer, d.name AS department "
                + "FROM messages m "
                + "INNER JOIN users u ON m.from_id = u.id "
                + "INNER JOIN departments d ON m.department_id = d.id "
                + "WHERE m.department_id IN (SELECT u_d.department_id FROM users u LEFT JOIN user_departments u_d ON u.id = u_d.user_id WHERE u.id = ?) "
                + "ORDER BY m.created_at DESC";
        
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, user_id);
           ResultSet rs = ps.executeQuery();
           
           List<List<String>> messages = new ArrayList<>();
           while(rs.next()){
               List<String> l = new ArrayList<>();
               l.add(rs.getString("title"));
               l.add(rs.getString("content"));
               l.add(rs.getString("lecturer"));
               l.add(rs.getString("department"));
               l.add(simpleDate.format(rs.getTimestamp("created_at")));
               messages.add(l);
           }
           
           return messages;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<List<String>> getLecturerMessages(int user_id){
        String sorgu = "SELECT m.title, m.content, m.created_at, d.name AS department "
                + "FROM messages m "
                + "INNER JOIN departments d ON m.department_id = d.id "
                + "WHERE m.from_id = ? "
                + "ORDER BY m.created_at DESC";
        
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, user_id);
           ResultSet rs = ps.executeQuery();
           
           List<List<String>> messages = new ArrayList<>();
           while(rs.next()){
               List<String> l = new ArrayList<>();
               l.add(rs.getString("title"));
               l.add(rs.getString("content"));
               l.add(rs.getString("department"));
               l.add(simpleDate.format(rs.getTimestamp("created_at")));
               messages.add(l);
           }
           
           return messages;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Map<String, String> getLecturerDepartments(int user_id){
        String sorgu = "SELECT d.id, d.name "
                + "FROM user_departments u_d "
                + "INNER JOIN departments d ON u_d.department_id = d.id "
                + "WHERE u_d.user_id = ? ";
        
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setInt(1, user_id);
           ResultSet rs = ps.executeQuery();
           
           Map<String, String> departments = new HashMap<>();
           while(rs.next()){
               departments.put(rs.getString("id"), rs.getString("name"));
           }
           return departments;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean saveMessage(Message message) {
        String sorgu = "INSERT INTO messages (title,content,from_id,department_id,created_at) "
                + "VALUES (?,?,?,?,?)";
        try{
           PreparedStatement ps = this.db.connect().prepareStatement(sorgu);
           ps.setString(1, message.getTitle());
           ps.setString(2, message.getContent());
           ps.setInt(3, message.getFrom_id());
           ps.setInt(4, message.getDepartment_id());
           ps.setString(5, message.getCreated_at());
           ps.execute();
           
           return true;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
