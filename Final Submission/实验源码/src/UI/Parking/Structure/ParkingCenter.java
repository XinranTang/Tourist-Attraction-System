// 原停车场命令行输出框架
// 现在已经加到ParkingController类中

//package Structure;
//
//import javafx.util.Pair;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Iterator;
//import java.util.Scanner;
//
//public class ParkingCenter {
//    private CarStack<Car> parkingStack;
//    private CarStack<Car> dodgingStack;
//    private CarQueue<Car> waitingQueue;
//
//    public ParkingCenter(int length) {
//        parkingStack = new CarStack<>(length);
//        dodgingStack = new CarStack<>(length);
//        waitingQueue = new CarQueue<>();
//    }
//
//    public int park(Car car) {
//        if (parkingStack.depth() == parkingStack.length()) {
//            waitingQueue.insert(car);
//            return -1;
//        } else {
//            parkingStack.push(car);
//            car.setParkingNumber(parkingStack.depth());
//            return parkingStack.depth();
//        }
//    }
//
//    public Car leave(String number) {
//        Car car = null;
//        for (Car temp : parkingStack) {
//            if (temp.getNumber().equals(number.trim())) {
//                car = temp;
//            }
//        }
//        if(car==null){
//            return car;
//        }else{
//            while (car.getParkingNumber() != parkingStack.depth()) {
//                dodgingStack.push(parkingStack.pop());
//            }
////        System.out.println(parkingStack.depth());
//            // 退出该车
////            Car tempCar = parkingStack.pop();
////            tempCar.setLeaveTime(LocalDateTime.now());
////            countFee(tempCar);
//            Car tempCar;
//            parkingStack.pop();
//            car.setLeaveTime(LocalDateTime.now());
//            countFee(car);
//            // 避让车辆退回
//            while (dodgingStack.depth() != 0) {
//                tempCar=dodgingStack.pop();
//                parkingStack.push(tempCar);
//                tempCar.setParkingNumber(parkingStack.depth());
//            }
//            // 便道车辆进入
//            if (waitingQueue.length() != 0) {
//                tempCar = waitingQueue.getFront();
//                waitingQueue.remove();
//                tempCar.setArriveTime(LocalDateTime.now());
//                park(tempCar);
//            }
//            return car;
//        }
//    }
//
//    public void countFee(Car car) {
//        Duration duration = Duration.between(car.getArriveTime(),car.getLeaveTime());
//        Long gap = duration.toMinutes();
//        double fee = 0.6*gap.intValue();
//        car.setFee(fee);
//    }
//    public int getParkingCars(){
//        return parkingStack.depth();
//    }
//    public int getWaitingCars(){
//        return waitingQueue.length();
//    }
//    public int getDodgingCars(){
//        return dodgingStack.depth();
//    }
//
////    public static void main(String[] args) {
////        Scanner s = new Scanner(System.in);
////        System.out.println("输入车号：");
////        Car a = new Car(Integer.parseInt(s.nextLine()));
////        Car b= new Car(1234);
////        Car c = new Car(2333);
////        System.out.println("输入最大停车场空位数：");
////        ParkingCenter parkingCenter = new ParkingCenter(Integer.parseInt(s.nextLine()));
////        parkingCenter.park(a);
////        parkingCenter.park(b);
////        parkingCenter.park(c);
////        parkingCenter.leave(a);
////    }
//}
