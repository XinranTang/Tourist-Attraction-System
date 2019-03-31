import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ParkingCenter {
    private CarStack<Car> parkingStack;
    private CarStack<Car> dodgingStack;
    private CarQueue<Car> waitingQueue;

    public ParkingCenter(int length) {
        parkingStack = new CarStack<>(length);
        dodgingStack = new CarStack<>(length);
        waitingQueue = new CarQueue<>();
    }

    public void park(Car car) {
        if (parkingStack.depth() == parkingStack.length()) {
            System.out.println("停车场已满，进入便道等待");
            waitingQueue.insert(car);
        } else {
            parkingStack.push(car);
            System.out.println("车:"+car.getNumber()+" 进入" + parkingStack.depth() + "号车位");
            car.setParkingNumber(parkingStack.depth());
        }
    }

    public void leave(Car car) {
        while (car.getParkingNumber() != parkingStack.depth()) {
            dodgingStack.push(parkingStack.pop());
        }
//        System.out.println(parkingStack.depth());
        // 退出该车
        Car tempCar = parkingStack.pop();
        tempCar.setLeaveTime(LocalDateTime.now());
        countFee(tempCar);
        // 避让车辆退回
        while (dodgingStack.depth() != 0) {
            tempCar=dodgingStack.pop();
            parkingStack.push(tempCar);
            tempCar.setParkingNumber(parkingStack.depth());
        }
        // 便道车辆进入
        if (waitingQueue.length() != 0) {
            tempCar = waitingQueue.getFront();
            waitingQueue.remove();
            tempCar.setArriveTime(LocalDateTime.now());
            park(tempCar);
        }
    }

    public void countFee(Car car) {
        Duration duration = Duration.between(car.getArriveTime(),car.getLeaveTime());
        Long gap = duration.toMinutes();
        double fee = 0.6*gap.intValue();
        car.setFee(fee);
        System.out.println("车："+car.getNumber()+" 停车 "+gap+" 分钟，收费 "+fee+"元");
    }

//    public static void main(String[] args) {
//        Scanner s = new Scanner(System.in);
//        System.out.println("输入车号：");
//        Car a = new Car(Integer.parseInt(s.nextLine()));
//        Car b= new Car(1234);
//        Car c = new Car(2333);
//        System.out.println("输入最大停车场空位数：");
//        ParkingCenter parkingCenter = new ParkingCenter(Integer.parseInt(s.nextLine()));
//        parkingCenter.park(a);
//        parkingCenter.park(b);
//        parkingCenter.park(c);
//        parkingCenter.leave(a);
//    }
}
