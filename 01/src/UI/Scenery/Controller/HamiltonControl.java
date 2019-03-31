package UI.Scenery.Controller;

import Structure.MyPair;
import Structure.Procedure;
import UI.Main.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HamiltonControl implements Initializable {
    public Canvas canvas;
    public TextArea attractionText;
    public TextField t1;
    public TextField t2;
    public Label warning;
    public ChoiceBox box1;

    /**
     * 查找景区景点页面
     */
    public void search(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("search")) {
                StageManager.CONTROLLER.put("HamiltonControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/searchScenery.fxml"));
                stage.setTitle("查找景区景点");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("search", stage);
                StageManager.STAGE.get("Hamilton").close();
            } else {
                StageManager.CONTROLLER.put("HamiltonControl", this);
                StageManager.STAGE.get("Hamilton").close();
                StageManager.STAGE.get("search").show();
            }
        }
    }

    /**
     * 景区景点排序
     */
    public void sort(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("sort")) {
                StageManager.CONTROLLER.put("HamiltonControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/sortScenery.fxml"));
                stage.setTitle("景区景点排序");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
                StageManager.STAGE.put("sort", stage);
                StageManager.STAGE.get("Hamilton").close();
            } else {
                StageManager.CONTROLLER.put("HamiltonControl", this);
                StageManager.STAGE.get("Hamilton").close();
                StageManager.STAGE.get("sort").show();
            }
        }
    }

    /**
     * 景点间的最短路径和最短距离页面
     */
    public void shortestPath(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("shortest")) {
            StageManager.CONTROLLER.put("HamiltonSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/shortest.fxml"));
            stage.setTitle("景点间的最短路径和最短距离");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("shortest", stage);
            StageManager.STAGE.get("Hamilton").close();
        } else {
            StageManager.CONTROLLER.put("HamiltonSceneryControl", this);
            StageManager.STAGE.get("Hamilton").close();
            StageManager.STAGE.get("shortest").show();
        }
    }

    public void tourGuide(ActionEvent actionEvent) {
    }

    /**
     * 修改景区景点页面
     */
    public void change(ActionEvent actionEvent) throws IOException {
        // 管理员模式
        if(StageManager.admin){
            if (StageManager.adjacencyList != null) {
                if (!StageManager.STAGE.containsKey("change")) {
                    StageManager.CONTROLLER.put("HamiltonControl", this);
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/changeScenery.fxml"));
                    stage.setTitle("修改景区景点");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                    StageManager.STAGE.put("change", stage);
                    StageManager.STAGE.get("Hamilton").close();
                } else {
                    StageManager.CONTROLLER.put("HamiltonControl", this);
                    StageManager.STAGE.get("Hamilton").close();
                    StageManager.STAGE.get("change").show();
                }
            }
        }
    }

    /**
     * 景区景点分布图页面
     */
    public void read(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("readScenery")) {
            StageManager.CONTROLLER.put("HamiltonControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("Hamilton").close();
        } else {
            StageManager.CONTROLLER.put("HamiltonControl", this);
            StageManager.STAGE.get("Hamilton").close();
            StageManager.STAGE.get("readScenery").show();
        }
    }

    /**
     * @param actionEvent 搜索导游路线图
     */
    public void searchTourGuide(ActionEvent actionEvent) throws IOException {
        if (t1.getText() == null || t1.getText().length() == 0 || t2.getText() == null || t2.getText().length() == 0) {
            warning.setText("请输入景点！");
        } else {
            // 清除画布
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, 500, 500);
            StageManager.outputScene(StageManager.adjacencyList, canvas);
            gc.setFill(Color.RED);
            gc.setStroke(Color.WHITE);

            MyPair<ArrayList<String>, Integer> pair = Procedure.createTourSortGraph(t1.getText(), t2.getText());

            ArrayList<String> arrayList = pair.getFirst();
            // 设置TestArea
            int i = 0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("*起点：").append(t1.getText()).append("\n");
            stringBuilder.append("*终点：").append(t2.getText()).append("\n");
            while (i < arrayList.size()) {
                String string = arrayList.get(i);
                i++;
                if (i != 1) {
                    stringBuilder.append("->");
                }
                // 画出路线
                stringBuilder.append(string);
                gc.fillOval(StageManager.adjacencyList.find(string).getPosition()[0], StageManager.adjacencyList.find(string).getPosition()[1], 44, 44);
                gc.setStroke(Color.RED);
                gc.setFill(Color.RED);
                gc.setLineWidth(3);
                if (i != 1) {
                    gc.strokeLine(StageManager.adjacencyList.find(arrayList.get(i - 2)).getPosition()[0] + 8, StageManager.adjacencyList.find(arrayList.get(i - 2)).getPosition()[1] + 26, StageManager.adjacencyList.find(string).getPosition()[0] + 8, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                }
                gc.setFill(Color.RED);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1);
                gc.strokeText(string, StageManager.adjacencyList.find(string).getPosition()[0] + 4, StageManager.adjacencyList.find(string).getPosition()[1] + 26);

            }
            stringBuilder.append("\n").append("长度：").append(pair.getSecond());
            attractionText.setText(stringBuilder.toString());
            // 写入文件
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("TourGuide.txt")), "gbk"));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    /**
     * @param location  地址
     * @param resources 源
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.outputScene(StageManager.adjacencyList, canvas);
    }

    /**
     * @param actionEvent 退出
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("HamiltonControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("Hamilton").close();
            } else {
                StageManager.CONTROLLER.put("HamiltonControl", this);
                StageManager.STAGE.get("Hamilton").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
