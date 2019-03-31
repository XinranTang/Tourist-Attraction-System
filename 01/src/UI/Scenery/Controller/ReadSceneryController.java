package UI.Scenery.Controller;

import Structure.AdjacencyList;
import Structure.Procedure;
import UI.Main.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ReadSceneryController {
    @FXML
    private TextArea attractionText;
    @FXML
    private Label warning;
    @FXML
    private Canvas canvas;

    /**
     * @param actionEvent 打开景点文件并输出景点邻接链表
     */
    public void openSceneryFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        // 使用文件选择器
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            AdjacencyList adjacencyList = Procedure.createGraph(file);
            StageManager.adjacencyList=adjacencyList;
            attractionText.setText("*景区景点邻接链表：\n\n" + adjacencyList.toString());
            StageManager.outputScene(adjacencyList,canvas);
        }

    }

    /**
     * @param actionEvent 打开景点信息文件并输出景点信息
     */
    public void openSceneryInf(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String s = Procedure.readInformation(file);
            attractionText.setText("*景区景点信息：\n\n" + s);
        }

    }

    /**
     * @param actionEvent 创建并输出景点邻接矩阵
     */
    public void createGraph(ActionEvent actionEvent) {
        String graph = Procedure.outputGraph();
        if (graph.length() == 0) {
            warning.setText("请先选择景点！");
        } else {
            attractionText.setText("*景区景点邻接矩阵：\n\n" + graph);
        }
    }

    /**
     * 查找景区景点页面
     */
    public void search(ActionEvent actionEvent) throws IOException {
        if(StageManager.adjacencyList!=null){
            if (!StageManager.STAGE.containsKey("search")) {
                StageManager.CONTROLLER.put("readSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/searchScenery.fxml"));
                stage.setTitle("查找景区景点");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("search", stage);
                StageManager.STAGE.get("readScenery").close();
            } else {
                StageManager.CONTROLLER.put("readSceneryControl", this);
                StageManager.STAGE.get("readScenery").close();
                StageManager.STAGE.get("search").show();
            }
        }
    }

    /**
     * 景区景点排序页面
     */
    public void sort(ActionEvent actionEvent) throws IOException {
        if(StageManager.adjacencyList!=null){
            if (!StageManager.STAGE.containsKey("sort")) {
                StageManager.CONTROLLER.put("readSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/sortScenery.fxml"));
                stage.setTitle("景区景点排序");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("sort", stage);
                StageManager.STAGE.get("readScenery").close();
            } else {
                StageManager.CONTROLLER.put("readSceneryControl", this);
                StageManager.STAGE.get("readScenery").close();
                StageManager.STAGE.get("sort").show();
            }
        }
    }

    /**
     * 景点间的最短路径和最短距离页面
     */
    public void shortestPath(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("shortest")) {
            StageManager.CONTROLLER.put("readSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/shortest.fxml"));
            stage.setTitle("景点间的最短路径和最短距离");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("shortest", stage);
            StageManager.STAGE.get("readScenery").close();
        } else {
            StageManager.CONTROLLER.put("readSceneryControl", this);
            StageManager.STAGE.get("readScenery").close();
            StageManager.STAGE.get("shortest").show();
        }
    }

    /**
     * 导游路线图页面
     */
    public void tourGuide(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("Hamilton")) {
            StageManager.CONTROLLER.put("readSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/Hamilton.fxml"));
            stage.setTitle("导游路线图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("Hamilton", stage);
            StageManager.STAGE.get("readScenery").close();
        } else {
            StageManager.CONTROLLER.put("readSceneryControl", this);
            StageManager.STAGE.get("readScenery").close();
            StageManager.STAGE.get("Hamilton").show();
        }
    }


    /**
     * 修改景区景点页面
     */
    public void change(ActionEvent actionEvent) throws IOException {
       if(StageManager.admin){
           if(StageManager.adjacencyList!=null){
               if (!StageManager.STAGE.containsKey("change")) {
                   StageManager.CONTROLLER.put("readSceneryControl", this);
                   Stage stage = new Stage();
                   Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/changeScenery.fxml"));
                   stage.setTitle("修改景区景点");
                   stage.setScene(new Scene(root));
                   stage.setResizable(false);
                   stage.show();
                   StageManager.STAGE.put("change", stage);
                   StageManager.STAGE.get("readScenery").close();
               } else {
                   StageManager.CONTROLLER.put("readSceneryControl", this);
                   StageManager.STAGE.get("readScenery").close();
                   StageManager.STAGE.get("change").show();
               }
           }
       }
    }

    /**
     * 景区信息管理系统页面
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("readSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("readScenery").close();
            } else {
                StageManager.CONTROLLER.put("readSceneryControl", this);
                StageManager.STAGE.get("readScenery").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
