package UI.Scenery.Controller;

import Structure.Procedure;
import Structure.TouristAttraction;
import UI.Main.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchSceneryController implements Initializable {
    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox box1;
    @FXML
    private TextField searchText;
    @FXML
    private Label warning;
    @FXML
    private TextArea attractionText;
    @FXML
    private RadioButton name;
    @FXML
    private RadioButton inf;

    /**
     * @param location  URL地址
     * @param resources 源
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.outputScene(StageManager.adjacencyList, canvas);
        // 设置复选框内容
        ObservableList<String> options = FXCollections.observableArrayList(
                "使用KMP算法:（可多关键字查找，用逗号分隔）",
                "使用字典树：（景点名模糊匹配）："
        );
        box1.setItems(options);
    }

    /**
     * 景区景点分布图页面
     */
    public void readScenery(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("readScenery")) {
            StageManager.CONTROLLER.put("searchControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("search").close();
        } else {
            StageManager.CONTROLLER.put("searchControl", this);
            StageManager.STAGE.get("search").close();
            StageManager.STAGE.get("readScenery").show();
        }
    }

    /**
     * 景区景点排序页面
     */
    public void sort(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("sort")) {
            StageManager.CONTROLLER.put("searchControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/sortScenery.fxml"));
            stage.setTitle("景区景点排序");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("sort", stage);
            StageManager.STAGE.get("search").close();
        } else {
            StageManager.CONTROLLER.put("searchControl", this);
            StageManager.STAGE.get("search").close();
            StageManager.STAGE.get("sort").show();
        }
    }

    /**
     * 景点间的最短路径和最短距离页面
     */
    public void shortestPath(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("shortest")) {
            StageManager.CONTROLLER.put("searchControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/shortest.fxml"));
            stage.setTitle("景点间的最短路径和最短距离");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("shortest", stage);
            StageManager.STAGE.get("search").close();
        } else {
            StageManager.CONTROLLER.put("searchControl", this);
            StageManager.STAGE.get("search").close();
            StageManager.STAGE.get("shortest").show();
        }
    }

    /**
     * 导游路线图页面
     */
    public void tourGuide(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("Hamilton")) {
            StageManager.CONTROLLER.put("searchControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/Hamilton.fxml"));
            stage.setTitle("导游路线图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("Hamilton", stage);
            StageManager.STAGE.get("search").close();
        } else {
            StageManager.CONTROLLER.put("searchControl", this);
            StageManager.STAGE.get("search").close();
            StageManager.STAGE.get("Hamilton").show();
        }
    }

    /**
     * @param actionEvent 搜索景点
     */
    public void search(ActionEvent actionEvent) {
        // 清空warning标签
        warning.setText("");
        // 检查输入
        if (searchText.getText() == null || searchText.getText().length() == 0) {
            warning.setText("请输入关键词！");
        } else if (!name.isSelected() && !inf.isSelected()) {
            warning.setText("请选择查找范围！");
        } else if (box1.getSelectionModel().getSelectedItem() == null) {
            warning.setText("请选择算法类型！");
        } else {
            // 选择算法
            ArrayList<TouristAttraction> attractions = null;
            if (box1.getSelectionModel().getSelectedItem().toString().equals("使用KMP算法:（可多关键字查找，用逗号分隔）")) {
                if (name.isSelected() && inf.isSelected()) {
                    attractions = Procedure.search(searchText.getText(), 1, 1);

                } else if (!name.isSelected() && inf.isSelected()) {
                    attractions = Procedure.search(searchText.getText(), 1, 2);
                } else if (name.isSelected() && !inf.isSelected()) {
                    attractions = Procedure.search(searchText.getText(), 1, 3);
                }

            } else if (box1.getSelectionModel().getSelectedItem().toString().equals("使用字典树：（景点名模糊匹配）：")) {
                if (name.isSelected() && inf.isSelected()) {
                    warning.setText("只可景点名搜索！");
                    return;
                } else if (!name.isSelected() && inf.isSelected()) {
                    warning.setText("只可景点名搜索！");
                    return;
                } else if (name.isSelected() && !inf.isSelected()) {
                    attractions = Procedure.search(searchText.getText(), 2, 3);
                }
            }
            if (attractions == null) {
                warning.setText("无符合条件的景点！");
            } else {
                // 生成结果的字符串
                StringBuilder stringBuilder = new StringBuilder();
                attractions.forEach(touristAttraction -> {
                    stringBuilder.append("【").append(touristAttraction.getName()).append("】\n");
                    stringBuilder.append(touristAttraction.getInformation()).append("\n");
                });
                attractionText.setText(stringBuilder.toString());
                // 在界面上画出查找到的景点
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, 500, 500);
                StageManager.outputScene(StageManager.adjacencyList, canvas);
                gc.setFill(Color.RED);
                attractions.forEach(touristAttraction -> {
                    gc.fillOval(StageManager.adjacencyList.find(touristAttraction.getName()).getPosition()[0], StageManager.adjacencyList.find(touristAttraction.getName()).getPosition()[1], 44, 44);
                    gc.strokeText(touristAttraction.getName(), StageManager.adjacencyList.find(touristAttraction.getName()).getPosition()[0] + 4, StageManager.adjacencyList.find(touristAttraction.getName()).getPosition()[1] + 26);
                });
            }
        }

    }

    /**
     * 修改景区景点页面
     */
    public void change(ActionEvent actionEvent) throws IOException {
        if(StageManager.admin){
            if(StageManager.adjacencyList!=null){
                if (!StageManager.STAGE.containsKey("change")) {
                    StageManager.CONTROLLER.put("searchControl", this);
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/changeScenery.fxml"));
                    stage.setTitle("修改景区景点");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                    StageManager.STAGE.put("change", stage);
                    StageManager.STAGE.get("search").close();
                } else {
                    StageManager.CONTROLLER.put("searchControl", this);
                    StageManager.STAGE.get("search").close();
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
                StageManager.CONTROLLER.put("searchControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("search").close();
            } else {
                StageManager.CONTROLLER.put("searchControl", this);
                StageManager.STAGE.get("search").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
