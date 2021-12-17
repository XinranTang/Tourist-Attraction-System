package UI.Scenery.Controller;

import Structure.AdjacencyList;
import Structure.Procedure;
import UI.Main.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ChangeSceneryController implements Initializable {
    public Label warning1;
    public TextField t1;
    public TextField t11;
    public Label warning2;
    public Label warning3;
    public Label warning4;
    public TextArea attractionText;
    public TextField t2;
    public TextField t3;
    public TextField t4;
    public Canvas canvas;

    private boolean flag = false;

    /**
     * @param location  URL地址
     * @param resources 源
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.outputScene(StageManager.adjacencyList, canvas);
    }

    /**
     * 查找景区景点页面
     */
    public void search(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("search")) {
                StageManager.CONTROLLER.put("changeSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/searchScenery.fxml"));
                stage.setTitle("查找景区景点");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("search", stage);
                StageManager.STAGE.get("change").close();
            } else {
                StageManager.CONTROLLER.put("changeSceneryControl", this);
                StageManager.STAGE.get("change").close();
                StageManager.STAGE.get("search").show();
            }
        }
    }

    /**
     * 景区景点排序页面
     */
    public void sort(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("sort")) {
                StageManager.CONTROLLER.put("changeSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/sortScenery.fxml"));
                stage.setTitle("景区景点排序");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
                StageManager.STAGE.put("sort", stage);
                StageManager.STAGE.get("change").close();
            } else {
                StageManager.CONTROLLER.put("changeSceneryControl", this);
                StageManager.STAGE.get("change").close();
                StageManager.STAGE.get("sort").show();
            }
        }
    }

    /**
     * 景点间的最短路径和最短距离页面
     */
    public void shortestPath(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("shortest")) {
            StageManager.CONTROLLER.put("changeSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/shortest.fxml"));
            stage.setTitle("景点间的最短路径和最短距离");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("shortest", stage);
            StageManager.STAGE.get("change").close();
        } else {
            StageManager.CONTROLLER.put("changeSceneryControl", this);
            StageManager.STAGE.get("change").close();
            StageManager.STAGE.get("shortest").show();
        }
    }

    /**
     * 导游路线图页面
     */
    public void tourGuide(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("Hamilton")) {
            StageManager.CONTROLLER.put("changeSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/Hamilton.fxml"));
            stage.setTitle("导游路线图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("Hamilton", stage);
            StageManager.STAGE.get("change").close();
        } else {
            StageManager.CONTROLLER.put("changeSceneryControl", this);
            StageManager.STAGE.get("change").close();
            StageManager.STAGE.get("Hamilton").show();
        }
    }

    /**
     * 自己界面不需要再打开了
     */
    public void change(ActionEvent actionEvent) {
    }

    /**
     * 景区景点分布图页面
     */
    public void read(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("readScenery")) {
            StageManager.CONTROLLER.put("changeSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("change").close();
        } else {
            StageManager.CONTROLLER.put("changeSceneryControl", this);
            StageManager.STAGE.get("change").close();
            StageManager.STAGE.get("readScenery").show();
        }
    }

    /**
     * @param actionEvent 添加景点
     */
    public void addA(ActionEvent actionEvent) {
        flag = false;
        warning1.setText("");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 500, 500);
        StageManager.outputScene(StageManager.adjacencyList, canvas);
        if (t1.getText() == null || t11.getText() == null || t1.getText().length() == 0 || t11.getText().length() == 0) {
            warning1.setText("请输入信息！");
        } else {
            AdjacencyList adjacencyList = Procedure.insertAttraction(t1.getText(), t11.getText());
            if (adjacencyList == null) {
                warning1.setText("勿添加重复景点！");
            } else {
                flag = true;
                StageManager.adjacencyList = adjacencyList;
                attractionText.setText(adjacencyList.toString());
                gc.setFill(Color.RED);
                gc.fillOval(StageManager.adjacencyList.find(t1.getText()).getPosition()[0], StageManager.adjacencyList.find(t1.getText()).getPosition()[1], 44, 44);
                gc.strokeText(t1.getText(), StageManager.adjacencyList.find(t1.getText()).getPosition()[0] + 4, StageManager.adjacencyList.find(t1.getText()).getPosition()[1] + 26);
            }
        }
    }

    /**
     * @param actionEvent 删除景点
     */
    public void deleteA(ActionEvent actionEvent) {
        flag = false;
        warning2.setText("");
        if (t2.getText() == null || t2.getText().length() == 0) {
            warning2.setText("请输入信息！");
        } else {
            AdjacencyList adjacencyList = Procedure.deleteAttraction(t2.getText());
            if (adjacencyList == null) {
                warning2.setText("无相关景点！");
            } else {
                flag = true;
                StageManager.adjacencyList = adjacencyList;
                attractionText.setText(adjacencyList.toString());
                StageManager.outputScene(StageManager.adjacencyList, canvas);
            }
        }
    }


    /**
     * @param actionEvent 添加道路
     */
    public void addR(ActionEvent actionEvent) {
        flag = false;
        warning3.setText("");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 500, 500);
        StageManager.outputScene(StageManager.adjacencyList, canvas);
        if (t3.getText() == null || t3.getText().length() == 0) {
            warning3.setText("请输入信息！");
        } else {
            AdjacencyList adjacencyList = Procedure.insertRoad(t3.getText());
            if (adjacencyList == null) {
                warning3.setText("路已存在或无效路名");
            } else {
                flag = true;
                StageManager.adjacencyList = adjacencyList;
                attractionText.setText(adjacencyList.toString());
                String[] lineList = t3.getText().split("——");
                gc.setFill(Color.RED);
                gc.setStroke(Color.WHITE);
                gc.fillOval(StageManager.adjacencyList.find(lineList[0]).getPosition()[0], StageManager.adjacencyList.find(lineList[0]).getPosition()[1], 44, 44);
                gc.strokeText(lineList[0], StageManager.adjacencyList.find(lineList[0]).getPosition()[0] + 4, StageManager.adjacencyList.find(lineList[0]).getPosition()[1] + 26);
                gc.fillOval(StageManager.adjacencyList.find(lineList[1]).getPosition()[0], StageManager.adjacencyList.find(lineList[1]).getPosition()[1], 44, 44);
                gc.strokeText(lineList[1], StageManager.adjacencyList.find(lineList[1]).getPosition()[0] + 4, StageManager.adjacencyList.find(lineList[1]).getPosition()[1] + 26);
                gc.setStroke(Color.RED);
                gc.strokeLine(StageManager.adjacencyList.getGraphHashMap().get(lineList[0]).getTouristAttraction().getPosition()[0] + 8, StageManager.adjacencyList.getGraphHashMap().get(lineList[0]).getTouristAttraction().getPosition()[1] + 26, StageManager.adjacencyList.getGraphHashMap().get(lineList[1]).getTouristAttraction().getPosition()[0] + 8, StageManager.adjacencyList.getGraphHashMap().get(lineList[1]).getTouristAttraction().getPosition()[1] + 26);

            }
        }
    }

    /**
     * @param actionEvent 删除道路
     */
    public void deleteR(ActionEvent actionEvent) {
        flag = false;
        warning4.setText("");
        if (t4.getText() == null || t4.getText().length() == 0) {
            warning4.setText("请输入信息！");
        } else {
            AdjacencyList adjacencyList = Procedure.deleteRoad(t4.getText());
            if (adjacencyList == null) {
                warning4.setText("该道路不存在！");
            } else {
                flag = true;
                StageManager.adjacencyList = adjacencyList;
                attractionText.setText(adjacencyList.toString());
                StageManager.outputScene(StageManager.adjacencyList, canvas);
            }
        }
    }

    /**
     * @param string 通告内容
     * @throws IOException IO异常
     */
    public void generateNotice(String string) throws IOException {
        File file = new File("通告.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "gbk"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t\t公告").append("\n");
        stringBuilder.append("各位游客：").append("\n");
        stringBuilder.append("\t").append(string).append("\n");
        stringBuilder.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日")));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * @param actionEvent 发布删除路的通告
     * @throws IOException IO异常
     */
    public void generateNotice4(ActionEvent actionEvent) throws IOException {
        if (!flag) {
            warning4.setText("勿发布无效公告！");
        } else {
            a(t4.getText(), "给您带来的不便请谅解，祝您生活愉快！");

            Procedure.toFile();
        }
    }

    private void a(String text, String s) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String[] list = text.split("——");
        stringBuilder.append("根据园区需要，本园区内增设从 ").append(list[0]).append(" 到 ").append(list[1]).append(" 道路。");
        stringBuilder.append(s).append("\n");
        generateNotice(stringBuilder.toString());
    }

    /**
     * @param actionEvent 发布添加结点的通告
     * @throws IOException IO异常
     */
    public void generateNotice1(ActionEvent actionEvent) throws IOException {
        if (!flag) {
            warning1.setText("勿发布无效公告！");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("根据园区需要，本园区内增设 ").append(t1.getText()).append(" 景点。");
            stringBuilder.append("给您带来的不便请谅解，祝您生活愉快！").append("\n");
            generateNotice(stringBuilder.toString());

            Procedure.toFile();
        }
    }

    /**
     * @param actionEvent 发布删除结点的通告
     * @throws IOException IO异常
     */
    public void generateNotice2(ActionEvent actionEvent) throws IOException {
        if (!flag) {
            warning2.setText("勿发布无效公告！");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("根据园区需要，本园区内取消 ").append(t2.getText()).append(" 景点。");
            stringBuilder.append("给您带来的不便请谅解，祝您生活愉快！").append("\n");
            generateNotice(stringBuilder.toString());

            Procedure.toFile();
        }
    }

    /**
     * @param actionEvent 发布添加路的通告
     * @throws IOException IO异常
     */
    public void generateNotice3(ActionEvent actionEvent) throws IOException {
        if (!flag) {
            warning3.setText("勿发布无效公告！");
        } else {
            a(t3.getText(), "您可根据需要选择便利道路，祝您生活愉快！");

            Procedure.toFile();
        }
    }

    /**
     * @param actionEvent 退出
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("changeSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("change").close();
            } else {
                StageManager.CONTROLLER.put("changeSceneryControl", this);
                StageManager.STAGE.get("change").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
