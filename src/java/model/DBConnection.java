/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author yahya
 */
public class DBConnection {
    private Connection connection;
    public Connection connect(){
        try{
            this.connection = DriverManager.getConnection("url","username","password");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return this.connection;
    }
}
