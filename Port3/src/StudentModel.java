import java.sql.*;
import java.util.ArrayList;

public class StudentModel {
    Connection conn = null;
    String url = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    StudentModel(String url) {
        this.url = url;
    }


    public void connectToDatabase() throws SQLException {
        conn = DriverManager.getConnection(url);
    }

    public void closeConnectionToDatabase() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    public ArrayList<String> SQLQueryStudents() throws SQLException{
        ArrayList<String> Students=new ArrayList<>();
        String sql = "Select * from Students;";
        rs=stmt.executeQuery(sql);
        while(rs!=null && rs.next()){
            String name=rs.getString(1);
            System.out.println(name);
            Students.add(name);
        }
        return Students;
    }
}

class StudentInfo{
    Integer StudentID;
    String Name;
    Integer CourseID;
    String CourseName;
    String Grade;
    StudentInfo(Integer StudentID, String Name, Integer CourseID, String CourseName, String Grade){
        this.StudentID = StudentID;
        this.Name = Name;
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Grade = Grade;
    }
}