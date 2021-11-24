import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

//Commented out elements are due to set grade

public class UIJavaFX {
    GridPane Startview;
    Button Exit;
    Button GetStudentGrade;
    Button GetCourseGrade;
    Button GetStudentInfo;
    //Button SetGrade;
    ComboBox StudentComb;
    ComboBox CourseComb;
    ComboBox GradeSetComb;
    Label StudentName;
    Label CourseName;
    //Label SetGradeOption;
    //ComboBox CourseIDcomb;

    ObservableList<String> Students;
    ObservableList<String> Courses;
    ObservableList<String> Grades;
    TextArea InfoScreen;

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
        GetStudentGrade = new Button("Get Student Grades");
        GetCourseGrade = new Button("Get Course Grades");
        GetStudentInfo = new Button("Get Student Info");//
        //SetGrade = new Button("Set Grade");

        StudentComb= new ComboBox();
        CourseComb = new ComboBox();
        //CourseIDcomb = new ComboBox();
        GradeSetComb = new ComboBox();
        InfoScreen = new TextArea();

        StudentName= new Label("Select Student Name:");
        CourseName= new Label("Select Course:");
        //SetGradeOption = new Label("Pick Grade to assign:");

        Startview.add(StudentName,40,1);
        Startview.add(CourseName,40,2);
        //Startview.add(SetGradeOption,40,3);



        Startview.add(Exit,1,10);
        Startview.add(StudentComb,41,1);
        Startview.add(CourseComb,41,2);
        //Startview.add(GradeSetComb, 41,3);
        Startview.add(GetStudentGrade,41,4);
        Startview.add(GetCourseGrade,41,5);
        Startview.add(GetStudentInfo,41,6);
        //Startview.add(SetGrade,41,7);
        Startview.add(InfoScreen,1,1,9,9);


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
