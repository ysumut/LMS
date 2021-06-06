package model;

import entity.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageModel {
    private DBConnection db = new DBConnection();
    
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
               l.add(rs.getString("created_at"));
               messages.add(l);
           }
           
           return messages;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
