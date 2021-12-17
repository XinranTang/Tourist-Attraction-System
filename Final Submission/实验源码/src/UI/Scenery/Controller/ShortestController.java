package UI.Scenery.Controller;

import Structure.CarStack;
import Structure.Edge;
import Structure.MyPair;
import Structure.Procedure;
import UI.Main.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShortestController implements Initializable {

    public Canvas canvas;
    public TextField t1;
    public TextField t2;
    public Label warning;
    public TextArea attractionText;
    public ChoiceBox box1;

    /**
     * 页面初始化
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.outputScene(StageManager.adjacencyList, canvas);
        ObservableList<String> options = FXCollections.observableArrayList(
                "Dijkstra算法",
                "Floyd算法",
                "Bellman-Ford算法",
                "SPFA算法"
        );
        box1.setItems(options);
    }

    /**
     * 查找景区景点页面
     */
    public void search(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("search")) {
                StageManager.CONTROLLER.put("shortestControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/searchScenery.fxml"));
                stage.setTitle("查找景区景点");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();
                StageManager.STAGE.put("search", stage);
                StageManager.STAGE.get("shortest").close();
            } else {
                StageManager.CONTROLLER.put("shortestControl", this);
                StageManager.STAGE.get("shortest").close();
                StageManager.STAGE.get("search").show();
            }
        }
    }


    public void shortestPath(ActionEvent actionEvent) {
    }

    /**
     * 导游路线图页面
     */
    public void tourGuide(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("Hamilton")) {
            StageManager.CONTROLLER.put("shortestControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/Hamilton.fxml"));
            stage.setTitle("导游路线图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("Hamilton", stage);
            StageManager.STAGE.get("shortest").close();
        } else {
            StageManager.CONTROLLER.put("shortestControl", this);
            StageManager.STAGE.get("shortest").close();
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
                   StageManager.CONTROLLER.put("shortestControl", this);
                   Stage stage = new Stage();
                   Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/changeScenery.fxml"));
                   stage.setTitle("修改景区景点");
                   stage.setResizable(false);
                   stage.setScene(new Scene(root));
                   stage.show();
                   StageManager.STAGE.put("change", stage);
                   StageManager.STAGE.get("shortest").close();
               } else {
                   StageManager.CONTROLLER.put("shortestControl", this);
                   StageManager.STAGE.get("shortest").close();
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
            StageManager.CONTROLLER.put("shortestControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("shortest").close();
        } else {
            StageManager.CONTROLLER.put("shortestControl", this);
            StageManager.STAGE.get("shortest").close();
            StageManager.STAGE.get("readScenery").show();
        }
    }

    /**
     * 景区景点排序页面
     */
    public void sort(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("sort")) {
            StageManager.CONTROLLER.put("shortestControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/sortScenery.fxml"));
            stage.setTitle("景区景点排序");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            StageManager.STAGE.put("sort", stage);
            StageManager.STAGE.get("shortest").close();
        } else {
            StageManager.CONTROLLER.put("shortestControl", this);
            StageManager.STAGE.get("shortest").close();
            StageManager.STAGE.get("sort").show();
        }
    }

    /**
     * @param actionEvent 查找最短路
     */
    public void searchShortest(ActionEvent actionEvent) {
        warning.setText("");
        if (t1.getText() == null || t1.getText().length() == 0 || t2.getText() == null || t2.getText().length() == 0) {
            warning.setText("请输入景点！");
        } else if (box1.getSelectionModel().getSelectedItem() == null) {
            warning.setText("请选择算法！");
        } else {

            if (box1.getSelectionModel().getSelectedItem().toString().equals("Dijkstra算法")) {
                MyPair<CarStack<Edge>, Integer> pair = Procedure.shortestPath0(t1.getText(), t2.getText(), 1);
                if (pair == null) {
                    warning.setText("景点不存在！");
                } else {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, 500, 500);
                    StageManager.outputScene(StageManager.adjacencyList, canvas);
                    CarStack<Edge> stack = pair.getFirst();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("*开始结点：").append(t1.getText()).append("\n");
                    stringBuilder.append("*终止结点：").append(t2.getText()).append("\n");
                    stringBuilder.append(t1.getText());

                    gc.setFill(Color.RED);
                    gc.setStroke(Color.WHITE);
                    gc.fillOval(StageManager.adjacencyList.find(t1.getText()).getPosition()[0], StageManager.adjacencyList.find(t1.getText()).getPosition()[1], 44, 44);
                    gc.strokeText(t1.getText(), StageManager.adjacencyList.find(t1.getText()).getPosition()[0] + 4, StageManager.adjacencyList.find(t1.getText()).getPosition()[1] + 26);
                    Edge temp;
                    while (stack.depth() != 0) {
                        temp = stack.pop();
                        String string = temp.getTo().getTouristAttraction().getName();
                        stringBuilder.append("->").append(string);
                        gc.fillOval(StageManager.adjacencyList.find(string).getPosition()[0], StageManager.adjacencyList.find(string).getPosition()[1], 44, 44);
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(3);
                        gc.strokeLine(StageManager.adjacencyList.find(temp.getFrom().getTouristAttraction().getName()).getPosition()[0] + 8, StageManager.adjacencyList.find(temp.getFrom().getTouristAttraction().getName()).getPosition()[1] + 26, StageManager.adjacencyList.find(string).getPosition()[0] + 8, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                        gc.setStroke(Color.WHITE);
                        gc.setLineWidth(1);
                        gc.strokeText(string, StageManager.adjacencyList.find(string).getPosition()[0] + 4, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                    }
                    stringBuilder.append("\n最短距离为：").append(pair.getSecond()).append("\n");
                    attractionText.setText(stringBuilder.toString());
                }
            } else if (box1.getSelectionModel().getSelectedItem().toString().equals("Floyd算法")) {
                MyPair<ArrayList<String>, Integer> pair = Procedure.shortestPath(t1.getText(), t2.getText(), 2);
                if (pair == null) {
                    warning.setText("景点不存在！");
                } else {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, 500, 500);
                    StageManager.outputScene(StageManager.adjacencyList, canvas);
                    ArrayList<String> arrayList = pair.getFirst();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("*开始结点：").append(t1.getText()).append("\n");
                    stringBuilder.append("*终止结点：").append(t2.getText()).append("\n");
                    gc.setFill(Color.RED);
                    gc.setStroke(Color.WHITE);
                    int i = 0;
                    while (i < arrayList.size()) {
                        String string = arrayList.get(i);
                        i++;
                        if (i != 1) {
                            stringBuilder.append("->");
                        }
                        stringBuilder.append(string);
                        gc.fillOval(StageManager.adjacencyList.find(string).getPosition()[0], StageManager.adjacencyList.find(string).getPosition()[1], 44, 44);
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(3);
                        if (i != 1) {
                            gc.strokeLine(StageManager.adjacencyList.find(arrayList.get(i - 2)).getPosition()[0] + 8, StageManager.adjacencyList.find(arrayList.get(i - 2)).getPosition()[1] + 26, StageManager.adjacencyList.find(string).getPosition()[0] + 8, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                        }
                        gc.setFill(Color.RED);
                        gc.setStroke(Color.WHITE);
                        gc.setLineWidth(1);
                        gc.strokeText(string, StageManager.adjacencyList.find(string).getPosition()[0] + 4, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                    }
                    stringBuilder.append("\n最短距离为：").append(pair.getSecond()).append("\n");
                    attractionText.setText(stringBuilder.toString());
                }
            } else if (box1.getSelectionModel().getSelectedItem().toString().equals("Bellman-Ford算法")) {
                MyPair<ArrayList<String>, Integer> pair = Procedure.shortestPath(t1.getText(), t2.getText(), 3);
                if (pair == null) {
                    warning.setText("景点不存在！");
                } else {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, 500, 500);
                    StageManager.outputScene(StageManager.adjacencyList, canvas);
                    ArrayList<String> arrayList = pair.getFirst();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("*开始结点：").append(t1.getText()).append("\n");
                    stringBuilder.append("*终止结点：").append(t2.getText()).append("\n");
                    gc.setFill(Color.RED);
                    gc.setStroke(Color.WHITE);
                    int i = arrayList.size() - 1;
                    while (i >= 0) {
                        String string = arrayList.get(i);
                        i--;
                        if (i != arrayList.size() - 2) {
                            stringBuilder.append("->");
                        }
                        stringBuilder.append(string);
                        gc.fillOval(StageManager.adjacencyList.find(string).getPosition()[0], StageManager.adjacencyList.find(string).getPosition()[1], 44, 44);
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(3);
                        if (i != arrayList.size() - 2) {
                            gc.strokeLine(StageManager.adjacencyList.find(arrayList.get(i + 2)).getPosition()[0] + 8, StageManager.adjacencyList.find(arrayList.get(i + 2)).getPosition()[1] + 26, StageManager.adjacencyList.find(string).getPosition()[0] + 8, StageManager.adjacencyList.find(string).getPosition()[1] + 26);

                        }
                        gc.setStroke(Color.WHITE);
                        gc.setLineWidth(1);
                        gc.strokeText(string, StageManager.adjacencyList.find(string).getPosition()[0] + 4, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                    }
                    stringBuilder.append("\n最短距离为：").append(pair.getSecond()).append("\n");
                    attractionText.setText(stringBuilder.toString());
                }
            } else if (box1.getSelectionModel().getSelectedItem().toString().equals("SPFA算法")) {
                MyPair<ArrayList<String>, Integer> pair = Procedure.shortestPath(t1.getText(), t2.getText(), 4);
                if (pair == null) {
                    warning.setText("景点不存在！");
                } else {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, 500, 500);
                    StageManager.outputScene(StageManager.adjacencyList, canvas);
                    ArrayList<String> arrayList = pair.getFirst();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("*开始结点：").append(t1.getText()).append("\n");
                    stringBuilder.append("*终止结点：").append(t2.getText()).append("\n");
                    gc.setFill(Color.RED);
                    gc.setStroke(Color.WHITE);
                    int i = arrayList.size() - 1;
                    while (i >= 0) {
                        String string = arrayList.get(i);
                        i--;
                        if (i != arrayList.size() - 2) {
                            stringBuilder.append("->");
                        }
                        stringBuilder.append(string);
                        gc.fillOval(StageManager.adjacencyList.find(string).getPosition()[0], StageManager.adjacencyList.find(string).getPosition()[1], 44, 44);
                        gc.setLineWidth(3);
                        gc.setStroke(Color.RED);
                        if (i != arrayList.size() - 2) {
                            gc.strokeLine(StageManager.adjacencyList.find(arrayList.get(i + 2)).getPosition()[0] + 8, StageManager.adjacencyList.find(arrayList.get(i + 2)).getPosition()[1] + 26, StageManager.adjacencyList.find(string).getPosition()[0] + 8, StageManager.adjacencyList.find(string).getPosition()[1] + 26);

                        }
                        gc.setStroke(Color.WHITE);
                        gc.setLineWidth(1);
                        gc.strokeText(string, StageManager.adjacencyList.find(string).getPosition()[0] + 4, StageManager.adjacencyList.find(string).getPosition()[1] + 26);
                    }
                    stringBuilder.append("\n最短距离为：").append(pair.getSecond()).append("\n");
                    attractionText.setText(stringBuilder.toString());
                }
            }


        }

    }

    /**
     * @param actionEvent 退出
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("shortestControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("shortest").close();
            } else {
                StageManager.CONTROLLER.put("shortestControl", this);
                StageManager.STAGE.get("shortest").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }
}
