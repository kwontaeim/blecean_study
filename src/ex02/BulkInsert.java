package ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BulkInsert {
  

  public static void main(String[] args) {
 // Start time
    String fliePath = "/Users/KWON/Downloads/error.csv"; // 파일 경로 스태틱 변수에 담기
    long startTime = System.currentTimeMillis();
    
    DBase db = new DBase();
    Connection conn = db.connect("jdbc:mariadb://localhost:3306/mydb","root","1111");
    db.importData(conn, fliePath);
  
    // End time
    long endTime = System.currentTimeMillis();
    System.out.println("\n시스템 수행시간은  " + (endTime - startTime) + "(ms) 입니다.");
  }

}

class DBase {

  public Connection connect(String db_connect_str, String db_userid, String db_password) {
    
    Connection conn;
    
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();

      conn = DriverManager.getConnection(db_connect_str, db_userid, db_password);
    }
    catch(Exception e) {
      e.printStackTrace();
      conn = null;
    }

    return conn;    
  }

  public void importData(Connection conn, String filename) {
    Statement stmt;
    String query;

    try {
      query = "LOAD DATA INFILE '"+ filename + "' INTO TABLE blucean_test_1 FIELDS TERMINATED BY ','  LINES TERMINATED BY '\n' IGNORE 1 LINES (@userNoValue, type, message, @dateValue, trace) set userNo = if(@userNoValue='', null, @userNoValue), date = if(@dateValue='', null, @dateValue)";
      stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

      stmt.executeUpdate(query);
    }
    catch(Exception e) {
      e.printStackTrace();
      stmt = null;
    }
  }
}


