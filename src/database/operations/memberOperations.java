package database.operations;

/*
IT Tallaght - 2015, S2
Computing - Year 2, Project
Group 17
*/

import database.ConnectionDB;
import model.Member;

import javax.swing.*;
import java.io.*;
import java.sql.*;

public class MemberOperations {

    Connection conn;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rset;

    public MemberOperations() {
        this.conn = ConnectionDB.getConn();
    }

    // Get all members in the databage
    public ResultSet getAllMembers() {
        String query = "SELECT * FROM member";
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(JOptionPane.showConfirmDialog(null, "MemberOperations - getAllMembers"));
        }
        return rset;
    }

    // Get all members and all their details (except blobs) to display on the main members window
    public ResultSet getAllMembersMinusBlobs() {
        String query = "SELECT memberid, memberfname, memberlname, memberstreet, membercity, membercounty," +
                " memberdobd, memberdobm, memberdoby, memberemail, membernumber, memberpoints FROM member";
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(JOptionPane.showConfirmDialog(null, "MemberOperations - getAllMembersMinusBlobs"));
        }
        return rset;
    }

    // Insert (add) a new member
    public void addMember(String fname, String lname, String street, String city, String county, int bDay, String bMonth, String bYear, String email, int memNum, int memPoints, File memPic) {
        String query = "INSERT INTO member (memberId, memberFName, memberLName, memberStreet, memberCity, memberCounty," +
                "memberDOBd, memberDOBm, memberDOBy, memberEmail, memberNumber, memberPoints, memPic)" +
                "VALUES (memberSeq.nextVal,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, street);
            pstmt.setString(4, city);
            pstmt.setString(5, county);
            pstmt.setInt(6, bDay);
            pstmt.setString(7, bMonth);
            pstmt.setString(8, bYear);
            pstmt.setString(9, email);
            pstmt.setInt(10, memNum);
            pstmt.setInt(11, memPoints);
            pstmt.setBinaryStream(12, new FileInputStream(memPic));
            pstmt.execute();
        } catch (SQLException q) {
            JOptionPane.showMessageDialog(null, "MemberOperations - addMember 1");
        } catch (FileNotFoundException fnf) {
            JOptionPane.showMessageDialog(null, "MemberOperations - addMember 2");
        }
    }

    // return a member object based on id
    public Member getMemberById(int idIn){
        Member m = null;
        String query = "SELECT memberId, memberFName, memberLName, memberStreet, memberCity, memberCounty, memberDOBd, memberDOBm, memberDOBy, memberEmail, memberNumber, memberPoints, memPic FROM member WHERE memberId = '"+idIn+"'";
        try{
            stmt = conn.createStatement();
            rset = stmt.executeQuery(query);
            while (rset.next()) {
                m = new Member(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5),
                        rset.getString(6), rset.getInt(7), rset.getString(8), rset.getString(9), rset.getString(10),
                        rset.getString(11),rset.getInt(12), rset.getBytes(13));
            }
        }catch(SQLException sq){
            JOptionPane.showMessageDialog(null, "MemberOperations - memberById");
        }
        return m;
    }
}