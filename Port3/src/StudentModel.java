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

    public String PstmtAVGGradeFromCourse(String Course) throws SQLException {
        String sql="SELECT AVG(Grade) as Grade FROM courseRegistration as cR JOIN Course as C on cR.courseID=C.courseID WHERE courseName=?;";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, Course);
        //pstmt.setInt(2, CourseID);
        rs=pstmt.executeQuery();
        String AVGGrade = rs.getString("Grade");
        System.out.println(AVGGrade);
        return AVGGrade;
    }

    public String PstmtAVGGradeFromStudent(String Students) throws SQLException {
        String sql="SELECT AVG(Grade) as Grade FROM courseRegistration as cR JOIN Students as S on cR.studentID=S.studentID WHERE firstName = ?;";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, Students);
        rs=pstmt.executeQuery();
        String AVGGrade = rs.getString("Grade");
        System.out.println(AVGGrade);
        return AVGGrade;
    }

    public ArrayList<StudentInfo> PstmCourseAndStudentGrades(String StudentRegistration) throws SQLException {
        ArrayList<StudentInfo> StudentInfos = new ArrayList<>();
        String sql = "SELECT S.StudentID as StudentID,S.firstName as Name,\n" +
                "       cR.courseID as CourseID,C.courseName as CourseName,cR.Grade as Grade\n" +
                "FROM Students as S\n" +
                "         JOIN courseRegistration as cR ON S.StudentID=cR.StudentID\n" +
                "         JOIN Course C on C.CourseID = cR.CourseID WHERE C.courseID = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,StudentRegistration);
        rs=pstmt.executeQuery();
        while (rs!=null&&rs.next()){
            Integer StudentID = rs.getInt("studentID");
            String FirstName = rs.getString("firstName");
            //String LastName = rs.getString("lastName");
            Integer CourseID = rs.getInt("courseID");
            String CourseName = rs.getString("courseName");
            String Grade = rs.getString("Grade");
            System.out.println("Student with ID:" + StudentID + "FirstName" + FirstName + /*"LastName" + LastName +*/ "Are registered to course"+
                    CourseName + "with ID " +CourseID + "And was Graded"+Grade);
            StudentInfo I = new StudentInfo(StudentID,FirstName/*,LastName*/,CourseID,CourseName,Grade);
            StudentInfos.add(I);
        }
        return StudentInfos;
    }



    public ArrayList<String> SQLQueryStudents() throws SQLException{
        ArrayList<String> Students=new ArrayList<>();
        String sql = "Select firstName from Students;";
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
            String name = rs.getString(1);
            System.out.println(name);
            Courses.add(name);
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
    String FirstName;
    //String LastName;
    Integer CourseID;
    String CourseName;
    String Grade;
    StudentInfo(Integer StudentID, String FirstName/*,String LastName*/, Integer CourseID, String CourseName, String Grade){
        this.StudentID = StudentID;
        this.FirstName = FirstName;
        //this.LastName = LastName;
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Grade = Grade;
    }


}