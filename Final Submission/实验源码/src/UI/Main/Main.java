package UI.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * 加载初始界面
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FxmlFile/MainUI.fxml"));
        primaryStage.setTitle("景区信息管理系统");
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.setResizable(false);
        primaryStage.show();
        StageManager.STAGE.put("main", primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
