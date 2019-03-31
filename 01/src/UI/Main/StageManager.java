package UI.Main;

import Structure.AdjacencyList;
import Structure.MyHashMap;
import Structure.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * 界面管理器
 * 用来存放加载的界面和界面控制器
 */
public class StageManager {
    /**
     * 界面存放中心
     */
    public static Map<String, Stage> STAGE = new HashMap<String, Stage>();
    /**
     * 界面控制器存放中心
     */
    public static Map<String, Object> CONTROLLER = new HashMap<String, Object>();
    /**
     * 界面标签
     */
    public static String flag;
    /**
     * 邻接链表，用于画图
     */
    public static AdjacencyList adjacencyList;
    /**
     * 是否是管理员
     */
    public static boolean admin = true;

    /**
     * 在界面上画出景点及边
     * @param adjacencyList 邻接链表，用于画图
     * @param canvas 画布
     */
    public static void outputScene(AdjacencyList adjacencyList, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // 先清除画布
        gc.clearRect(0,0,600,600);
        // 设置线宽
        gc.setLineWidth(1);
        MyHashMap<String,Node> h = adjacencyList.getGraphHashMap();
        // 画每一个景点以及邻接边
        for(MyHashMap.Node<String,Node> each:StageManager.adjacencyList.getGraphHashMap().entrySet){
            Node node = each.getValue();
            // 设置颜色
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.WHITE);
            // 画景点的结点
            gc.fillOval(node.getTouristAttraction().getPosition()[0], node.getTouristAttraction().getPosition()[1], 44, 44);
            Node currentNode = node.getNextNode();
            gc.setStroke(Color.BLACK);
            while(currentNode!=null){
                // 画边线
                gc.strokeLine(h.get(node.getTouristAttraction().getName()).getTouristAttraction().getPosition()[0]+8,h.get(node.getTouristAttraction().getName()).getTouristAttraction().getPosition()[1]+26,h.get(currentNode.getTouristAttraction().getName()).getTouristAttraction().getPosition()[0]+8,h.get(currentNode.getTouristAttraction().getName()).getTouristAttraction().getPosition()[1]+26);
                currentNode = currentNode.getNextNode();
            }
            gc.setStroke(Color.WHITE);
            // 添加景点名
            gc.strokeText(node.getTouristAttraction().getName(), node.getTouristAttraction().getPosition()[0] + 4, node.getTouristAttraction().getPosition()[1] + 26);
        }
    }
}
