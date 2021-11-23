import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class StudentController {
    UIJavaFX view;
    StudentModel model;

    public StudentController (StudentModel m, UIJavaFX v) throws SQLException {
        this.view = v;
        this.model = m;

        this.view.Exit.setOnAction(e -> {
            Platform.exit();
            try {
                this.model.closeConnectionToDatabase();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        });
        this.model.connectToDatabase();
        this.model.createStatement();
        this.view.Courses = GetCourses();
        this.view.Students = GetStudents();
        this.view.Grades = GetGrades();
        view.GetStudentInfo.setOnAction(e->{
            try {
                HandleGetStudentsInfo(String.valueOf(view.StudentComb.getValue()), view.StudentGrade);
            } catch (SQLException e2) {
                e2.printStackTrace();
                System.out.println(e2.getMessage());

            }
        });

        view.SetGrade.setOnAction(e->{
            try {
                model.SetGrade(String.valueOf(view.StudentComb.getValue()), String.valueOf(view.CourseComb.getValue()), String.valueOf(view.GradeSetComb.getValue()));
            }catch (SQLException e2) {
                e2.printStackTrace();
                System.out.println(e2.getMessage());
                if (model.conn != null) {
                    try {
                        System.err.print("Grade is being rolled back");
                        model.conn.rollback();
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                        System.out.println(excep.getMessage());
                    }
                }
            }
        });



        view.GetStudentGrade.setOnAction(e->{
            try {
                HandleGetStudentAVGGrade(String.valueOf(view.StudentComb.getValue()), view.StudentGrade);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        view.GetCourseGrade.setOnAction(e->{
            try {
                HandleGetCourseAVGGrade(String.valueOf(view.CourseComb.getValue()), view.StudentGrade);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        this.view.configure();
    }
    public ObservableList<String> GetStudents() throws SQLException{
        ArrayList<String> Students = model.SQLQueryStudents();
        ObservableList<String> StudentsNames = FXCollections.observableArrayList(Students);
        return StudentsNames;
    }

    public ObservableList<String> GetGrades() {
        ArrayList<String> Grades = new ArrayList<>();
        Collections.addAll(Grades, "-3", "0", "02", "4", "7", "10", "12");
        ObservableList<String> GradesNR = FXCollections.observableArrayList(Grades);
        return GradesNR;
    }

    public ObservableList<String> GetCourses() throws SQLException{
        ArrayList<String> Course = model.SQLQueryCourses();
        ObservableList<String> CoursesNames = FXCollections.observableArrayList(Course);
        return CoursesNames;
    }




    public void HandleGetStudentsInfo(String Student, TextArea studentGrade) throws SQLException{
        studentGrade.clear();
        studentGrade.appendText("Student Info: ");
        ArrayList<StudentInfo> Info= model.PstmCourseAndStudentGrades(Student);
        for (int i = 0; i < Info.size(); i++) {
            String Course = String.format(Info.get(i).CourseName);
            String Grade = Info.get(i).Grade;
            studentGrade.appendText(Student + " on course " + Course + " has gotten " + Grade + " as their grade\n");
        }
    }

    public void HandleGetStudentAVGGrade(String Student, TextArea studentGrade) throws SQLException{
        studentGrade.clear();
        studentGrade.appendText(Student + " has an AVG grade of ");
        String Info = model.PstmtAVGGradeFromStudent(Student);
        studentGrade.appendText(Info + "\n");
    }

    public void HandleGetCourseAVGGrade(String Course, TextArea CourseGrade) throws SQLException{
        CourseGrade.clear();
        CourseGrade.appendText(Course + " has an AVG grade of ");
        String Info = model.PstmtAVGGradeFromCourse(Course);
        CourseGrade.appendText(Info + "\n");
    }
}
