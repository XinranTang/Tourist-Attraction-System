package Structure;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 图的邻接矩阵类
 */
public class AdjacencyMatrix implements Graph {

    private HashMap<String, HashMap<String, Integer>> graphHashMap;

    /**
     * 构造器
     */
    public AdjacencyMatrix() {
        graphHashMap = new HashMap<>();

    }

    /**
     * @param graph 用邻接链表创建邻接矩阵
     */
    @Override
    public void createGraph(Graph graph) {
        graphHashMap = new HashMap<>();
        AdjacencyList adjacencyList = (AdjacencyList) graph;
        Iterator<MyHashMap.Node<String, Node>> iterator = adjacencyList.getIterator();
        MyHashMap.Node<String, Node> current;
        HashMap<String, Integer> rowHashMap;
        // 构造邻接矩阵的每一行，每一列暂时是一个空的MyHashMap
        while (iterator.hasNext()) {
            current = iterator.next();
            // 构造邻接矩阵的每一列
            rowHashMap = new HashMap<>();
            Node currentNode = current.getValue();
            rowHashMap.put(currentNode.getTouristAttraction().getName(), currentNode.getWeight());
            while (currentNode.hasNext()) {
                currentNode = currentNode.getNextNode();
                rowHashMap.put(currentNode.getTouristAttraction().getName(), currentNode.getWeight());
            }
            graphHashMap.put(current.getKey(), rowHashMap);
        }
    }

    /**
     * @return 邻接矩阵的迭代器
     */
    public Iterator<Map.Entry<String, HashMap<String, Integer>>> getIterator() {
        return graphHashMap.entrySet().iterator();
    }

    /**
     * @throws IOException 抛出IO异常
     */
    public void toFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("TouristAttractions.txt")), "gbk"));

        // 按下三角模式将邻接矩阵输出到文件
        MyPair<int[][], String[]> pair = processGraph();
        for (int i = 0; i < pair.getFirst().length; i++) {
            for (int j = 0; j < pair.getFirst().length; j++) {
                // 保证下三角
                if (j < i) {
                    if (pair.getFirst()[i][j] < 1000) {
                        bufferedWriter.write(pair.getSecond()[i] + "——" + pair.getSecond()[j] + "——" + pair.getFirst()[i][j]);
                        bufferedWriter.newLine();
                    }
                }
            }
        }
        // 关闭bufferedWriter
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * @return 邻接矩阵的字符串形式
     */
    @Override
    public String toString() {
        // 把矩阵的表头打出来
        StringBuilder title = new StringBuilder();
        title.append("\t\t");
        // 景点名的集合
        Set<String> touristAttractionSet = graphHashMap.keySet();
        for (String each : touristAttractionSet) {
            title.append(String.format("%8s\t", each));
        }
        title.append("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String each : touristAttractionSet) {
            // 景点名
            stringBuilder.append(String.format("%8s\t", each));
            HashMap<String, Integer> innerGraph = graphHashMap.get(each);
            // 打出景点之间的距离
            for (String innerEach : touristAttractionSet) {
                if (innerGraph.get(innerEach) == null) {
                    stringBuilder.append(String.format("%8s\t", "∞"));
                } else {
                    stringBuilder.append(String.format("%8s\t", innerGraph.get(innerEach)));
                }
            }
            stringBuilder.append("\n");
        }
        return (title.append(stringBuilder)).toString();
    }

    /**
     * @return 将邻接矩阵转为一个Pair，第一个元素是距离的矩阵，第二个元素是景点对应序号的集合
     */
    public MyPair<int[][], String[]> processGraph() {
        String[] vertices = new String[graphHashMap.size()];
        int[][] dist = new int[graphHashMap.size()][graphHashMap.size()];
        int i = 0;
        // 创建元素是景点对应序号的集合
        for (String s : graphHashMap.keySet()) {
            vertices[i] = s;
            i++;
        }
        i = 0;
        int j = 0;
        for (String each : graphHashMap.keySet()) {
            // 景点名
            HashMap<String, Integer> innerGraph = graphHashMap.get(each);
            j = 0;
            for (String innerEach : graphHashMap.keySet()) {
                // 如果不是邻接，将结点间距离设为一个很大的值，或者是Interger.MAX_VALUE,在这里直接设为了一个大值
                if (innerGraph.get(innerEach) == null) {
                    dist[i][j] = 100000;
                } else {
                    // 邻接结点添加距离
                    dist[i][j] = innerGraph.get(innerEach);
                }
                j++;
            }
            i++;
        }
        // 返回自己实现的MyPair
        return new MyPair<int[][], String[]>(dist, vertices);
    }
}
