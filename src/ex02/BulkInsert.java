package ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BulkInsert {
  

  public static void main(String[] args) {

    String fliePath = "/Users/KWON/Downloads/error.csv"; // 파일 경로
    long startTime = System.currentTimeMillis(); // Start time
    
    DataBase db = new DataBase();
    Connection con = db.connect("jdbc:mariadb://localhost:3306/mydb","root","1111");
    db.importData(con, fliePath);
  
    long endTime = System.currentTimeMillis(); // End time
    System.out.println("\n시스템 수행시간은  " + (endTime - startTime) + "(ms) 입니다.");
  }

}

class DataBase {

  public Connection connect(String db_connect_str, String db_userid, String db_password) {
    
    Connection con; // DB커넥션
    
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance(); // 드라이버 instance 생성 

      con = DriverManager.getConnection(db_connect_str, db_userid, db_password); // 디비접속 
    }
    catch(Exception e) {
      e.printStackTrace();
      con = null;
    }

    return con;    
  }

  public void importData(Connection con, String filename) {
    Statement stmt;
    String query;

    try {
      query = "LOAD DATA INFILE '"+ filename + "' INTO TABLE blucean_test_1 FIELDS TERMINATED BY ','  LINES TERMINATED BY '\n' IGNORE 1 LINES (@userNoValue, type, message, @dateValue, trace) set userNo = if(@userNoValue='', null, @userNoValue), date = if(@dateValue='', null, @dateValue)";
      stmt = con.prepareStatement(query);

      stmt.executeUpdate(query);
    }
    catch(Exception e) {
      e.printStackTrace();
      stmt = null;
      
    }
  }
}


