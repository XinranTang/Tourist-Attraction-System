package UI.Main.Controller;

import UI.Main.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class SceneryController {
    /**
     * 打开景区景点分布图界面
     */
    public void readScenery(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("readScenery")) {
            StageManager.CONTROLLER.put("sceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("scenery").close();
        } else {
            StageManager.CONTROLLER.put("sceneryControl", this);
            StageManager.STAGE.get("readScenery").show();
            StageManager.STAGE.get("scenery").close();
        }
    }
    /**
     * 回到主界面
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("sceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("scenery").close();
            } else {
                StageManager.CONTROLLER.put("sceneryControl", this);
                StageManager.STAGE.get("scenery").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
