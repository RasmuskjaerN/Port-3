import java.sql.SQLException;

public class Main{


    public static void main(String[] args) {
	// write your code her
        String url="jdbc:sqlite:D:\\Rasmus\\5semester\\Project\\Port3\\Port3DB";
        StudentModel Reg = new StudentModel(url);
        try{
            Reg.connectToDatabase();
            Reg.createStatement();
            Reg.SQLQueryStudents();
            Reg.SQLQueryCourses();
            Reg.PstmtAVGGradeFromCourse("SD","Autumn 2021");

            //Reg.avgGradeFromStudent();



        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        finally {
            try {
                Reg.closeConnectionToDatabase();
            }catch (SQLException e2){
                System.out.println(e2.getMessage());
            }
        }

    }
}
