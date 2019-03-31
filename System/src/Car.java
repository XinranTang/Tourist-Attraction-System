import java.time.LocalDateTime;

public class Car {
    private int number;
    private LocalDateTime arriveTime;
    private LocalDateTime leaveTime;
    private double fee;
    private int parkingNumber;
    public Car(int number, LocalDateTime arriveTime) {
        this.number = number;
        this.arriveTime = arriveTime;
    }

    public Car(int number) {
        this.number = number;
        this.arriveTime = LocalDateTime.now();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public LocalDateTime getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(LocalDateTime leaveTime) {
        this.leaveTime = leaveTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(int parkingNumber) {
        this.parkingNumber = parkingNumber;
    }
}
