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

    public String PstmtAVGGradeFromCourse(String Course,String Semester) throws SQLException {
        String sql="SELECT AVG(D1.Grade) as Grade FROM courseRegistration as D1 JOIN Course as D2 on D1.CourseID=D2.CourseID WHERE courseName = ? group by Semester = ?;";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, Course);
        pstmt.setString(2,Semester);
        rs=pstmt.executeQuery();
        String AVGGrade = rs.getString("Grade");
        System.out.println(AVGGrade);
        return AVGGrade;
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

    public ArrayList<String> SQLQueryCourses() throws SQLException{
        ArrayList<String> Courses = new ArrayList<>();
        String sql = "Select courseName from Course;";
        rs=stmt.executeQuery(sql);
        while (rs!=null && rs.next()){
            String cName = rs.getString(1);
            System.out.println(cName);
            Courses.add(cName);
        }
        return Courses;
    }

    public void SetGrade(String Student, String Course, String Grade) throws SQLException{
        conn.setAutoCommit(false);
        String SQL1 = "SELECT studentID as StudentID, firstName as Firstname, lastName as LastName FROM Students Where firstName=?;";
        pstmt=conn.prepareStatement(SQL1);
        pstmt.setString(1,Student);
        rs=pstmt.executeQuery();
        String sID = null;
        while (rs!=null && rs.next()){
            sID = rs.getString(1);
            System.out.println(sID);
        }

        String SQL2 = "SELECT courseID as CourseID, courseName as CourseName FROM Course WHERE courseName=?;";
        pstmt=conn.prepareStatement(SQL2);
        pstmt.setString(1,Course);
        rs=pstmt.executeQuery();
        String cID = null;
        while (rs!=null && rs.next()){
            cID = rs.getString(1);
            System.out.println(cID);
        }
        String SQL3 = "SELECT Grade as Grade from courseRegistration where courseID=? and studentID=?;";
        pstmt=conn.prepareStatement(SQL3);
        pstmt.setString(1,cID);
        pstmt.setString(2,sID);
        rs=pstmt.executeQuery();
        String gradeNull = null;
        while (rs!=null && rs.next()){
            gradeNull = rs.getString(1);
            System.out.println(gradeNull);
        }

        if (gradeNull==null){
            String SQL4 = "UPDATE courseRegistration SET Grade =? WHERE studentID =? and courseID =?;";
            pstmt=conn.prepareStatement(SQL4);
            pstmt.setString(1,Grade);
            pstmt.setString(2,sID);
            pstmt.setString(3,cID);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println(Grade);
        }else{
            System.out.println("Student has a Grade");
        }
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