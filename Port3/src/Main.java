import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    public void start(Stage primaryStage){
        UIJavaFX view = new UIJavaFX();
        StudentModel model = new StudentModel("jdbc:sqlite:D:\\Rasmus\\5semester\\Project\\Port3\\Port3DB");
        StudentController controller = null;
        try{
            controller = new StudentController(model,view);


            /*Reg.connectToDatabase();
            Reg.createStatement();
            Reg.SQLQueryStudents();
            Reg.SQLQueryCourses();
            Reg.PstmtAVGGradeFromCourse("SD","Autumn 2021");
            Reg.PstmtAVGGradeFromStudent("1");
            Reg.PstmCourseAndStudentGrades("1");*/



        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        primaryStage.setTitle("Student Registration");
        primaryStage.setScene(new Scene(view.asParent(),800,550));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e ->{
            Platform.exit();
            try {
                model.closeConnectionToDatabase();
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        });

    }



    public static void main(String[] args) {
	// write your code her
        launch(args);

    }
}
