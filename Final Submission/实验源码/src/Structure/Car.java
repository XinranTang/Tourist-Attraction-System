package Structure;

import javafx.scene.Group;

import java.time.LocalDateTime;

/**
 * 汽车类，用于停车场的模拟
 */
public class Car {
    /**
     * 车牌号
     */
    private String number;
    /**
     * 到达时间
     */
    private LocalDateTime arriveTime;
    /**
     * 离开时间
     */
    private LocalDateTime leaveTime;
    /**
     * 停车的费用
     */
    private double fee;
    /**
     * 车在停车场停的位置
     */
    private int parkingNumber;
    /**
     * JavaFX的图形组合，给每一辆车一个车的模型，用于画图
     */
    private Group group;
    /**
     * 车模型的X坐标，用于画图
     */
    private int positionX;
    /**
     * 车模型的Y坐标，用于画图
     */
    private int positionY;

    /**
     * 构造器
     * @param number 车牌号
     * @param arriveTime 到达时间
     */
    public Car(String number, LocalDateTime arriveTime) {
        this.number = number;
        this.arriveTime = arriveTime;
    }

    public Car(String number) {
        this.number = number;
        this.arriveTime = LocalDateTime.now();
    }

    /**
     * @return 车牌号
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number 车牌号
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return 到达时间
     */
    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    /**
     * @param arriveTime 到达时间
     */
    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * @return 离开时间
     */
    public LocalDateTime getLeaveTime() {
        return leaveTime;
    }

    /**
     * @param leaveTime 离开时间
     */
    public void setLeaveTime(LocalDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    /**
     * @return 停车费用
     */
    public double getFee() {
        return fee;
    }

    /**
     * @param fee 费用
     */
    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * @return 车在停车场停的位置
     */
    public int getParkingNumber() {
        return parkingNumber;
    }

    /**
     * @param parkingNumber 车在停车场停的位置
     */
    public void setParkingNumber(int parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    /**
     * @return JavaFX的图形组合，给每一辆车一个车的模型，用于画图
     */
    public Group getGroup() {
        return group;
    }

    /**
     * @param group JavaFX的图形组合，给每一辆车一个车的模型，用于画图
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * @return 车模型的X坐标，用于画图
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * @param positionX 车模型的X坐标，用于画图
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * @return 车模型的Y坐标，用于画图
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * @param positionY 车模型的Y坐标，用于画图
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
