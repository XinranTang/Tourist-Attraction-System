package UI.Main.Controller;

import UI.Main.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {
    public Button information;
    public Button car;
    public Button logIn;
    public TextField t1;
    public PasswordField p1;
    public AnchorPane pane;
    public Label warning;
    public GridPane grid;
    public Label welcome;

    /**
     * 打开景点管理中心界面
     */
    public void informationManage(MouseEvent mouseEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("scenery")) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/Scenery.fxml"));
            stage.setTitle("景点管理中心");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            //讲第二个窗口保存到map中
            StageManager.STAGE.put("scenery", stage);
            StageManager.CONTROLLER.put("mainControl", this);
            StageManager.STAGE.get("main").close();
        } else {
            StageManager.STAGE.get("scenery").show();
            StageManager.STAGE.get("main").close();
        }
    }

    /**
     * 打开停车场控制中心界面
     */
    public void parkingManage(MouseEvent mouseEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("park")) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Parking/FxmlFile/Parking.fxml"));
            stage.setTitle("停车场控制中心");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            //讲第二个窗口保存到map中
            StageManager.STAGE.put("park", stage);
            StageManager.CONTROLLER.put("mainControl", this);
            StageManager.STAGE.get("main").close();
        } else {
            StageManager.STAGE.get("park").show();
            StageManager.STAGE.get("main").close();
        }
    }

    /**
     * 管理员登录
     *
     * @param actionEvent 点击事件
     */
    public void logIn(ActionEvent actionEvent) {
        // 登录界面框显示
        pane.setVisible(true);
        // 界面上其他信息设为不可见
        welcome.setVisible(false);
        car.setVisible(false);
        information.setVisible(false);
    }

    /**
     * 界面初始化
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 未登录不可以进行操作
        car.setDisable(true);
        information.setDisable(true);
        // 登录界面框设置为不可见
        pane.setVisible(false);

    }

    /**
     * 管理员登录流程
     *
     * @param actionEvent 鼠标点击事件
     * @throws IOException IO异常
     */
    public void log(ActionEvent actionEvent) throws IOException {
        // 读取文件信息
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Administrator.txt")));
        String ID = t1.getText();
        String ps = p1.getText();
        String line = bufferedReader.readLine();
        boolean flag = false;
        boolean fault = false;
        if (ID != null && ID.length() != 0) {
            while (line != null) {
                // 找账号，查密码是否匹配
                if (line.split(" ")[0].equals(ID)) {
                    if (line.split(" ")[1].equals(ps)) {
                        flag = true;
                    } else {
                        warning.setText("密码错误");
                        fault = true;
                    }
                }
                line = bufferedReader.readLine();

            }
            // 信息无误
            if (flag) {
                StageManager.admin = true;
                // 可以进行操作
                car.setDisable(false);
                information.setDisable(false);
                // 登录界面框设置为不可见
                pane.setVisible(false);
                // 其他设置为可见
                welcome.setVisible(true);
                car.setVisible(true);
                information.setVisible(true);
            } else {
                if (!fault) {
                    warning.setText("未找到该ID");
                }
            }
        } else {
            StageManager.admin = false;
            car.setDisable(false);
            information.setDisable(false);
            // 登录界面框设置为不可见
            pane.setVisible(false);
            // 其他设置为可见
            welcome.setVisible(true);
            car.setVisible(true);
            information.setVisible(true);
        }

    }
}
