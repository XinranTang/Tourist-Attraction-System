package UI.Parking.Controller;

import Structure.Car;
import Structure.CarQueue;
import Structure.CarStack;
import UI.Main.StageManager;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * 停车管理系统控制类
 */
public class ParkingController implements Initializable {
    public Label l1;
    public Button b1;
    private PathTransition pathTransition0;
    private PathTransition pathTransition1;
    private PathTransition pathTransition2;
    private SequentialTransition sequentialTransition0;

    // 是否需要避让
    private boolean dodge = false;
    // 是否有车在等待
    private boolean wait = false;
    // 停车栈
    private CarStack<Car> parkingStack;
    // 避让栈
    private CarStack<Car> dodgingStack;
    // 等待队列
    private CarQueue<Car> waitingQueue;

    public AnchorPane pane;
    public Label warning1;
    public Label warning2;
    public Label prompt;
    public Canvas canvas0;
    public TextField t1;
    public TextField t2;
    public TextField t3;
    public Label warning0;


    /**
     * 打开景区景点分布图界面
     */
    public void read(ActionEvent actionEvent) throws IOException {
        if (!StageManager.STAGE.containsKey("readScenery")) {
            StageManager.CONTROLLER.put("parkControl", this);
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Scenery/FxmlFile/readScenery.fxml"));
            stage.setTitle("景区景点分布图");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            StageManager.STAGE.put("readScenery", stage);
            StageManager.STAGE.get("park").close();
        } else {
            StageManager.CONTROLLER.put("parkControl", this);
            StageManager.STAGE.get("park").close();
            StageManager.STAGE.get("readScenery").show();
        }
    }

    /**
     * 创建一个新的停车场
     *
     * @param actionEvent 事件
     */
    public void createCenter(ActionEvent actionEvent) {
        // 清空warning0标签的内容
        warning0.setText("");
        // 清空textArea的内容
        prompt.setText("");
        // 输入合法性检查
        if (t1.getText() == null || t1.getText().length() == 0) {
            warning0.setText("请输入数量！");
        } else if (!isNumeric(t1.getText())) {
            warning0.setText("请输入数字！");
        } else {
            // 画停车场的轮廓
            GraphicsContext gc = canvas0.getGraphicsContext2D();
            gc.clearRect(0, 0, 1080, 1080);
            gc.setStroke(Color.BLACK);
            int N = Integer.parseInt(t1.getText());
            if (parkingStack != null && parkingStack.length() != 0) {
                for (Car each : parkingStack) {
                    each.getGroup().setVisible(false);
                }
            }
            while (waitingQueue != null && waitingQueue.length() != 0) {
                waitingQueue.getFront().getGroup().setVisible(false);
                waitingQueue.remove();
            }
            // 新建停车栈、避让栈和等待队列
            parkingStack = new CarStack<>(N);
            dodgingStack = new CarStack<>(N);
            waitingQueue = new CarQueue<>();

            // 画简单的停车栈、避让栈和等待队列
            for (int i = 0; i < N; i++) {
                gc.strokeLine(800 - 60 * i, 200, 800 - 60 * i, 260);
                gc.strokeLine(800 - 60 * i, 200, 800 - 60 * i - 60, 200);
                gc.strokeLine(800 - 60 * i, 260, 800 - 60 * i - 60, 260);
            }
            gc.strokeLine(800 - 60 * N, 200, 800 - 60 * N - 20, 200);
            gc.strokeLine(800 - 60 * N, 260, 800 - 60 * N - 20, 260);
            gc.strokeLine(800 - 60 * N, 200, 800 - 60 * N, 260);

            gc.strokeLine(800, 100, 800 - 60 * N - 20, 100);
            gc.strokeLine(800, 160, 800 - 60 * N - 20, 160);

            gc.strokeLine(800, 300, 800, 360);
            gc.strokeLine(800, 300, 800 - 60 * N - 20, 300);
            gc.strokeLine(800, 360, 800 - 60 * N - 20, 360);

            gc.setStroke(Color.DARKCYAN);
            gc.strokeText("入口：", 800 - 60 * N - 60, 230);
            gc.strokeText("便道：", 800 - 120, 90);
            gc.strokeText("避让栈：", 800 - 120, 380);
        }
    }

    /**
     * @param str 输入内容
     * @return 输入是否是数字
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * @param actionEvent 停车
     */
    public void park(ActionEvent actionEvent) {
        // 清空warning1标签的内容
        warning1.setText("");
        // 检查输入
        if (parkingStack == null) {
            warning1.setText("请先创建停车场！");
        } else if (t2.getText() == null || t2.getText().length() == 0) {
            warning1.setText("请输入车号！");
        } else {
            // 设置停车信息
            LocalDateTime now = LocalDateTime.now();
            StringBuilder stringBuilder = new StringBuilder();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS");
            stringBuilder.append("车号：").append(t2.getText()).append("\n");
            stringBuilder.append("状态：进车场").append("\n");
            stringBuilder.append("进车场时刻：").append(now.format(dateTimeFormatter)).append("\n\n");
            Car newCar = new Car(t2.getText(), now);
            int parkingNumber = park(newCar);


            // 如果停车场已满，进入便道等待
            if (parkingNumber == -2) {
                warning1.setText("已存在车辆！");
            } else if (parkingNumber == -1) {
                // 画小车
                Group group = new Group();
                Rectangle r = new Rectangle(100, 100, 50, 10);
                Circle c1 = new Circle(115, 113, 5);
                Circle c2 = new Circle(135, 113, 5);

                //画梯形
                Polygon p = new Polygon();
                ObservableList<Double> list = p.getPoints();
                list.addAll(120.0, 90.0, 110.0, 100.0, 140.0, 100.0, 130.0, 90.0);

                r.setFill(Color.AQUAMARINE);
                c1.setFill(Color.DIMGRAY);
                c2.setFill(Color.DIMGRAY);
                p.setFill(Color.DARKCYAN);
                group.getChildren().addAll(p, r, c1, c2);
                pane.getChildren().add(group);
                // 小车添加形状
                newCar.setGroup(group);

                stringBuilder.append("停车场已满，进入便道等待");
                wait = true;
                // 设置小车坐标
                newCar.setPositionX(800 - 60 * waitingQueue.length());
                newCar.setPositionY(130);

                // 画小车进车场动画
                GraphicsContext gc = canvas0.getGraphicsContext2D();
                gc.setStroke(Color.BLACK);
                Path path = new Path();
                MoveTo moveTo = new MoveTo(100, 130);
                moveTo.setAbsolute(true);
                path.getElements().add(moveTo);
                LineTo lineTo = new LineTo(800 - 60 * waitingQueue.length(), 130);
                path.getElements().add(lineTo);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(4000));
                pathTransition.setPath(path);

                // 将group添加到pathTransition中
                pathTransition.setNode(group);
                pathTransition.setCycleCount(1);
                pathTransition.play();
                // 设置text
                prompt.setText(stringBuilder.toString());
            } else {
                // 画小车
                Group group = new Group();
                Rectangle r = new Rectangle(100, 100, 50, 10);
                Circle c1 = new Circle(115, 113, 5);
                Circle c2 = new Circle(135, 113, 5);

                //画梯形
                Polygon p = new Polygon();
                ObservableList<Double> list = p.getPoints();
                list.addAll(120.0, 90.0, 110.0, 100.0, 140.0, 100.0, 130.0, 90.0);

                r.setFill(Color.AQUAMARINE);
                c1.setFill(Color.DIMGRAY);
                c2.setFill(Color.DIMGRAY);
                p.setFill(Color.DARKCYAN);
                group.getChildren().addAll(p, r, c1, c2);
                pane.getChildren().add(group);
                // 小车添加形状
                newCar.setGroup(group);
                // 如果停车场未满，进入车场
                newCar.setPositionX(800 - 60 * parkingNumber);
                newCar.setPositionY(230);

                // 添加停车信息
                stringBuilder.append("该车停在 ").append(parkingNumber).append(" 号车位");

                // 画小车进车场动画
                Path path = new Path();
                MoveTo moveTo = new MoveTo(100, 230);
                moveTo.setAbsolute(true);
                path.getElements().add(moveTo);
                LineTo lineTo = new LineTo(800 - 60 * parkingNumber, 230);
                path.getElements().add(lineTo);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(4000));
                pathTransition.setPath(path);

                // 将group添加到pathTransition中
                pathTransition.setNode(group);
                pathTransition.setCycleCount(1);

                pathTransition.play();
                // 设置text
                prompt.setText(stringBuilder.toString());
            }

        }
    }

    /**
     * @param actionEvent 车辆离开
     */
    public void leave(ActionEvent actionEvent) {
        // 清空warning2标签的内容
        warning2.setText("");
        // 没有创建停车场不能进行操作
        if (parkingStack == null) {
            warning2.setText("请先创建停车场！");
            // 检查输入
        } else if (t3.getText() == null || t3.getText().length() == 0) {
            warning2.setText("请输入车号！");
        } else {
            // 没有车辆不能进行操作
            if (getParkingCars() == 0) {
                warning2.setText("当前车场内无车辆！");
            } else {
                // 设置离开信息
                Car car = leave(t3.getText());
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("车号：").append(car.getNumber()).append("\n");
                stringBuilder.append("状态：出车场").append("\n");
                stringBuilder.append("出车场时刻：").append(car.getLeaveTime().format(dateTimeFormatter)).append("\n\n");
                java.time.Duration duration = java.time.Duration.between(car.getArriveTime(), car.getLeaveTime());
                Long gap = duration.toMinutes();
                stringBuilder.append("车：").append(car.getNumber()).append(" 停车 ").append(gap).append(" 分钟，收费 ").append(car.getFee()).append("元");
                prompt.setText(stringBuilder.toString());
            }
        }
    }


    /**
     * @param car 要停的车辆
     * @return 停车场栈的深度，停车场满了返回-1
     */
    public int park(Car car) {
        boolean flag = false;
        for (Car car1 : parkingStack) {
            if (car1.getNumber().equals(car.getNumber())) {
                flag = true;
            }
        }
        for (Object car1 : waitingQueue) {
            if (((Car) car1).getNumber().equals(car.getNumber())) {
                flag = true;
            }
        }
        // 停车场满了返回-1
        if (flag) {
            return -2;
        } else if (parkingStack.depth() == parkingStack.length()) {
            waitingQueue.insert(car);
            return -1;
        } else {
            // 栈中加入该车
            parkingStack.push(car);
            // 停车场栈的深度
            car.setParkingNumber(parkingStack.depth());
            return parkingStack.depth();
        }
    }

    /**
     * 车辆离开
     *
     * @param number 车牌号
     * @return 离开的车辆
     */
    public Car leave(String number) {
        Car car = null;
        // 在停车栈中找该车辆
        for (Car temp : parkingStack) {
            if (temp.getNumber().equals(number.trim())) {
                car = temp;
            }
        }
        // 若该车辆不在车场内
        if (car == null) {
            warning2.setText("该车辆不在车场内！");
            return null;
        } else {
            // 该车辆在车场内
            // 画车辆出车场动画
            Path path = new Path();
            MoveTo moveTo = new MoveTo(car.getPositionX(), car.getPositionY());

            moveTo.setAbsolute(true);
            path.getElements().add(moveTo);
            LineTo lineTo = new LineTo(100, 230);
            path.getElements().add(lineTo);
            pathTransition0 = new PathTransition();
            pathTransition0.setDuration(Duration.millis(4000));
            pathTransition0.setPath(path);


            pathTransition0.setNode(car.getGroup());
            pathTransition0.setCycleCount(1);
            // 设置结束效果
            pathTransition0.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pathTransition0.getNode().setVisible(false);
                    if (dodge) {
                        sequentialTransition0.play();
                        dodge = false;
                    }
                }
            });

            int i = 1;
            SequentialTransition sequentialTransition = new SequentialTransition();
            // 如果车是栈的最后一个，即不需要避让
            if (car.getParkingNumber() == parkingStack.depth()) {
                // 设置离开信息
                car.setLeaveTime(LocalDateTime.now());
                // 计算费用
                countFee(car);
                // pathTransition0直接开始
                pathTransition0.play();
                // 出栈
                parkingStack.pop();
                return car;
            }
            // 如果需要避让
            while (car.getParkingNumber() != parkingStack.depth()) {
                dodge = true;
                // 车辆避让
                Car dodgeCar = parkingStack.pop();
                // 加入避让栈
                dodgingStack.push(dodgeCar);
                // 画避让动画
                Path dodgePath = new Path();
                moveTo = new MoveTo(dodgeCar.getPositionX(), dodgeCar.getPositionY());
                moveTo.setAbsolute(true);
                dodgePath.getElements().add(moveTo);

                QuadCurveTo quadTo = new QuadCurveTo();
                quadTo.setControlX(800 - 60 * parkingStack.length() - 160);
                quadTo.setControlY(280);
                quadTo.setX(800 - 60 * i);
                quadTo.setY(330);
                dodgePath.getElements().add(quadTo);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(4000));
                pathTransition.setPath(dodgePath);

                pathTransition.setNode(dodgeCar.getGroup());
                pathTransition.setCycleCount(1);

                sequentialTransition.getChildren().add(pathTransition);
                // 避让的车辆设置新的坐标
                dodgeCar.setPositionY(330);
                dodgeCar.setPositionX(800 - 60 * i);
                i++;
            }
            sequentialTransition.play();
            sequentialTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pathTransition0.play();
                }
            });
            // 退出该车
            Car tempCar;
            parkingStack.pop();
            car.setLeaveTime(LocalDateTime.now());
            countFee(car);
            // 避让车辆退回
            sequentialTransition0 = new SequentialTransition();
            while (dodgingStack.depth() != 0) {
                tempCar = dodgingStack.pop();
                parkingStack.push(tempCar);
                tempCar.setParkingNumber(parkingStack.depth());
                // 避让车辆退回动画
                Path dodgePath = new Path();
                moveTo = new MoveTo(tempCar.getPositionX(), tempCar.getPositionY());
                moveTo.setAbsolute(true);
                dodgePath.getElements().add(moveTo);

                QuadCurveTo quadTo = new QuadCurveTo();
                quadTo.setControlX(800 - 60 * parkingStack.length() - 160);
                quadTo.setControlY(280);
                quadTo.setX(800 - 60 * tempCar.getParkingNumber());
                quadTo.setY(230);
                dodgePath.getElements().add(quadTo);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(4000));
                pathTransition.setPath(dodgePath);

                pathTransition.setNode(tempCar.getGroup());
                pathTransition.setCycleCount(1);
                // 一个一个退回，所以用SequentialTransition
                sequentialTransition0.getChildren().add(pathTransition);
                tempCar.setPositionY(230);
                tempCar.setPositionX(800 - 60 * tempCar.getParkingNumber());

            }
            sequentialTransition0.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (waitingQueue.length() != 0 || wait) {
                        pathTransition1.play();
                        if (waitingQueue.length() == 0) wait = false;
                    }
                }
            });
            // 便道车辆进入
            if (waitingQueue.length() != 0) {
                tempCar = waitingQueue.getFront();
                park(tempCar);

                waitingQueue.remove();
                // 画便道车辆出便道动画
                path = new Path();
                moveTo = new MoveTo(tempCar.getPositionX(), tempCar.getPositionY());

                moveTo.setAbsolute(true);
                path.getElements().add(moveTo);
                lineTo = new LineTo(1200, 130);
                path.getElements().add(lineTo);
                pathTransition1 = new PathTransition();
                pathTransition1.setDuration(Duration.millis(4000));
                pathTransition1.setPath(path);
                // 设置小车图形
                pathTransition1.setNode(tempCar.getGroup());
                pathTransition1.setCycleCount(1);
                pathTransition1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        pathTransition2.play();
                    }
                });

                // 画便道车辆进入停车栈动画
                path = new Path();
                moveTo = new MoveTo(100, 230);

                moveTo.setAbsolute(true);
                path.getElements().add(moveTo);
                lineTo = new LineTo(800 - 60 * tempCar.getParkingNumber(), 230);
                path.getElements().add(lineTo);
                pathTransition2 = new PathTransition();
                pathTransition2.setDuration(Duration.millis(4000));
                pathTransition2.setPath(path);
                // 设置小车图形
                pathTransition2.setNode(tempCar.getGroup());
                pathTransition2.setCycleCount(1);

                tempCar.setArriveTime(LocalDateTime.now());

                tempCar.setPositionX(800 - 60 * tempCar.getParkingNumber());
                tempCar.setPositionY(230);
            }

            return car;
        }
    }

    /**
     * 计算费用，可按需求进行更改
     *
     * @param car 离开的车辆
     */
    public void countFee(Car car) {
        java.time.Duration duration = java.time.Duration.between(car.getArriveTime(), car.getLeaveTime());
        Long gap = duration.toMinutes();
        int intGap = gap.intValue();
        double fee;
        // 自己设定的规则：满30分钟，30分钟以上的一分钟0.6元，不满30分钟起步5元
        if (intGap < 30) fee = 5;
        else fee = 5 + 0.6 * (intGap - 30);
        car.setFee(fee);
    }

    /**
     * @return 停车的数量，不包含等待的和避让的
     */
    public int getParkingCars() {
        return parkingStack.depth();
    }

    /**
     * @return 等待的数量
     */
    public int getWaitingCars() {
        return waitingQueue.length();
    }

    /**
     * @return 避让的数量
     */
    public int getDodgingCars() {
        return dodgingStack.depth();
    }

    /**
     * @param actionEvent 离开
     * @throws IOException IO异常
     */
    public void exit(ActionEvent actionEvent) throws IOException {
        if (StageManager.adjacencyList != null) {
            if (!StageManager.STAGE.containsKey("main")) {
                StageManager.CONTROLLER.put("parkControl", this);
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UI/Main/FxmlFile/MainUI.fxml"));
                stage.setTitle("景区信息管理系统");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
                StageManager.STAGE.put("main", stage);
                StageManager.STAGE.get("park").close();
            } else {
                StageManager.CONTROLLER.put("parkControl", this);
                StageManager.STAGE.get("park").close();
                StageManager.STAGE.get("main").show();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!StageManager.admin) {
            l1.setVisible(false);
            // 默认大小：5
            t1.setText("5");
            t1.setVisible(false);
            b1.setVisible(false);
            GraphicsContext gc = canvas0.getGraphicsContext2D();
            gc.clearRect(0, 0, 1080, 1080);
            gc.setStroke(Color.BLACK);
            int N = Integer.parseInt(t1.getText());
            if (parkingStack != null && parkingStack.length() != 0) {
                for (Car each : parkingStack) {
                    each.getGroup().setVisible(false);
                }
            }
            while (waitingQueue != null && waitingQueue.length() != 0) {
                waitingQueue.getFront().getGroup().setVisible(false);
                waitingQueue.remove();
            }
            // 新建停车栈、避让栈和等待队列
            parkingStack = new CarStack<>(N);
            dodgingStack = new CarStack<>(N);
            waitingQueue = new CarQueue<>();

            // 画简单的停车栈、避让栈和等待队列
            for (int i = 0; i < N; i++) {
                gc.strokeLine(800 - 60 * i, 200, 800 - 60 * i, 260);
                gc.strokeLine(800 - 60 * i, 200, 800 - 60 * i - 60, 200);
                gc.strokeLine(800 - 60 * i, 260, 800 - 60 * i - 60, 260);
            }
            gc.strokeLine(800 - 60 * N, 200, 800 - 60 * N - 20, 200);
            gc.strokeLine(800 - 60 * N, 260, 800 - 60 * N - 20, 260);
            gc.strokeLine(800 - 60 * N, 200, 800 - 60 * N, 260);

            gc.strokeLine(800, 100, 800 - 60 * N - 20, 100);
            gc.strokeLine(800, 160, 800 - 60 * N - 20, 160);

            gc.strokeLine(800, 300, 800, 360);
            gc.strokeLine(800, 300, 800 - 60 * N - 20, 300);
            gc.strokeLine(800, 360, 800 - 60 * N - 20, 360);

            gc.setStroke(Color.DARKCYAN);
            gc.strokeText("入口：", 800 - 60 * N - 60, 230);
            gc.strokeText("便道：", 800 - 120, 90);
            gc.strokeText("避让栈：", 800 - 120, 380);
        }
    }
}
