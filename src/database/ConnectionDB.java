package database;

/*
IT Tallaght - 2015, S2
Computing - Year 2, Project
Group 17
*/

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionDB {
  private Connection conn = null;
  private ResultSet rset = null;
  private PreparedStatement pstmt = null;

  public ConnectionDB(){
    conn = openDB();
  }

  public Connection openDB(){
    try{
      OracleDataSource ods = new OracleDataSource();

      ods.setURL("jdbc:oracle:thin:hr@localhost:1521/XE");
      ods.setUser("Project");
      ods.setPassword("1234");

      conn = ods.getConnection();


    }catch (Exception e){
      System.out.println(e);
      System.out.println("Cannot establish connection");
    }
    return conn;
  }
}