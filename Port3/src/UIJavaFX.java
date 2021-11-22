import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;


public class UIJavaFX {
    GridPane Startview;
    Button Exit;
    Button StudentGrades;
    Button CourseGrades;
    Button StudentInfo;
    Button SetGrade;
    ComboBox StudentComb;
    ComboBox CourseComb;
    ComboBox GradeSetComb;

    ObservableList<String> Students;
    ObservableList<String> Courses;
    ObservableList<String> Grades;
    TextArea StudentGrade;

    public UIJavaFX(){
        Startview= new GridPane();
        CreateView();
    }
    public Parent asParent(){return Startview;}

    private void CreateView() {
        Startview.setMinSize(400,400);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(5);
        Exit = new Button("Close");
        StudentGrades = new Button("Get Student Grades");
        CourseGrades = new Button("Get Course Grades");
        //GetStudentInfos = new Button("Get Student Info");
        SetGrade = new Button("Set Grade");
        StudentComb= new ComboBox();
        CourseComb = new ComboBox();
        GradeSetComb = new ComboBox();
        StudentGrade = new TextArea();
        Startview.add(Exit,20,20);
        Startview.add(StudentComb,1,1);
        Startview.add(CourseComb,2,1);
        Startview.add(GradeSetComb, 3,1);
        /*Startview.add(GetGradesCourse,2,2);
        Startview.add(GetGradesStudent,1,2);
        Startview.add(GetStudentsInfo,1,3);*/
        Startview.add(SetGrade,3,2);
        Startview.add(StudentGrade,1,4,3,4);


    }

    public void configure(){
        StudentComb.setItems((Students));
        StudentComb.getSelectionModel().selectFirst();
        CourseComb.setItems((Courses));
        CourseComb.getSelectionModel().selectFirst();
        GradeSetComb.setItems((Grades));
        GradeSetComb.getSelectionModel().selectFirst();
    }

}
