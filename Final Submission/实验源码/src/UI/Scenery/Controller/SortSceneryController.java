package UI.Scenery.Controller;

import Structure.MyHashMap;
import Structure.Procedure;
import Structure.TouristAttraction;
import Structure.Node;
import UI.Main.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SortSceneryController implements Initializable {
    public ChoiceBox box1;
    public Label warning;
    public TextArea attractionText;
    public RadioButton c5;
    public RadioButton c6;
    private int choice;
    public Canvas canvas;
    public RadioButton c1;
    public RadioButton c2;
    public RadioButton c3;
    public RadioButton c4;

    /**
     * @param location  URL地址
     * @param resources 源
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.outputScene(StageManager.adjacencyList, canvas);

        final ToggleGroup group = new ToggleGroup();
        c1.setToggleGroup(group);
        c2.setToggleGroup(group);
        c3.setToggleGroup(group);
        c4.setToggleGroup(group);
        c5.setToggleGroup(group);
        c6.setToggleGroup(group);
        group.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                                        Toggle old_toggle, Toggle new_toggle) {
                        if (group.getSelectedToggle() != null) {
                            choice = Integer.parseInt((String) group.getSelectedToggle().getUserData());
                        }
                    }
                });

        ObservableList<String> options = FXCollections.observableArrayList(
                "按景点名排序",
                "按景点分支数排序"
        );
        box1.setItems(options);
    }

    /**
     * 查找景区景点页面
     */
    public void search(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("search")) {
                StageManager.CONTROLLER.put("sortSceneryControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/searchScenery.fxml"));
                stage.setTitle("查找景区景点");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
                StageManager.STAGE.put("search", stage);
                StageManager.STAGE.get("sort").close();
            } else {
                StageManager.CONTROLLER.put("sortSceneryControl", this);
                StageManager.STAGE.get("sort").close();
                StageManager.STAGE.get("search").show();
            }
        }
    }


    /**
     * 景点间的最短路径和最短距离页面
     */
    public void shortestPath(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("shortest")) {
            StageManager.CONTROLLER.put("sortSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/shortest.fxml"));
            stage.setTitle("景点间的最短路径和最短距离");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("shortest", stage);
            StageManager.STAGE.get("sort").close();
        } else {
            StageManager.CONTROLLER.put("sortSceneryControl", this);
            StageManager.STAGE.get("sort").close();
            StageManager.STAGE.get("shortest").show();
        }
    }

    /**
     * 导游路线图页面
     */
    public void tourGuide(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("Hamilton")) {
            StageManager.CONTROLLER.put("sortSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/Hamilton.fxml"));
            stage.setTitle("导游路线图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("Hamilton", stage);
            StageManager.STAGE.get("sort").close();
        } else {
            StageManager.CONTROLLER.put("sortSceneryControl", this);
            StageManager.STAGE.get("sort").close();
            StageManager.STAGE.get("Hamilton").show();
        }
    }

    /**
     * 修改景区景点页面
     */
    public void change(ActionEvent actionEvent) throws IOException {
        if(StageManager.admin){
            if (StageManager.adjacencyList != null) {
                if (!StageManager.STAGE.containsKey("change")) {
                    StageManager.CONTROLLER.put("sortSceneryControl", this);
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/changeScenery.fxml"));
                    stage.setTitle("修改景区景点");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show();
                    StageManager.STAGE.put("change", stage);
                    StageManager.STAGE.get("sort").close();
                } else {
                    StageManager.CONTROLLER.put("sortSceneryControl", this);
                    StageManager.STAGE.get("sort").close();
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
            StageManager.CONTROLLER.put("sortSceneryControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("sort").close();
        } else {
            StageManager.CONTROLLER.put("sortSceneryControl", this);
            StageManager.STAGE.get("sort").close();
            StageManager.STAGE.get("readScenery").show();
        }
    }

    /**
     * @param actionEvent 景点排序
     */
    public void sort(ActionEvent actionEvent) {
        warning.setText("");
        if (choice == 0) {
            warning.setText("请选择排序算法！");
        } else if (box1.getSelectionModel().getSelectedItem() == null) {
            warning.setText("请选择排序方式！");
        } else {
            // 调用procedure
            ArrayList<TouristAttraction> attractions = null;
            if(box1.getSelectionModel().getSelectedItem().toString().equals("按景点名排序")){
                attractions = Procedure.sort(choice, 1);
            }else if(box1.getSelectionModel().getSelectedItem().toString().equals("按景点分支数排序")){
                attractions = Procedure.sort(choice, 2);
            }
            if (attractions == null) {
                warning.setText("排序中断！");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("排序成功：\n\n");
                attractions.forEach(touristAttraction -> {
                    stringBuilder.append(touristAttraction.getName()).append("\n");
                });
                attractionText.setText(stringBuilder.toString());
            }
        }
    }

    /**
     * @param actionEvent 打乱顺序
     */
    public void disorder(ActionEvent actionEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        for(MyHashMap.Node<String,Node> each:StageManager.adjacencyList.getGraphHashMap().entrySet){
            stringBuilder.append(each.getValue().getTouristAttraction().getName()).append("\n");
        }
        attractionText.setText(stringBuilder.toString());
    }

    /**
     * @param actionEvent 退出
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("sortControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("sort").close();
            } else {
                StageManager.CONTROLLER.put("sortControl", this);
                StageManager.STAGE.get("sort").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
