package Structure.Method;

import Structure.AdjacencyMatrix;
import Structure.Edge;
import Structure.MyPair;
import Structure.Node;

import java.util.ArrayList;

/**
 * 算最短路的方法类
 */
public class CountShortest {
    /**
     * 整数的默认最大值
     */
    private static final int INF = Integer.MAX_VALUE;
    /**
     * 算法的选择
     */
    private int choice;
    /**
     * 邻接矩阵
     */
    private int[][] matrix;
    /**
     * 结点名对应序号的数组
     */
    private String[] vertices;
    /**
     * 开始结点序号
     */
    private int start;
    /**
     * 结束结点序号
     */
    private int end;
    /**
     * 结果的集合
     */
    private ArrayList<String> result = new ArrayList<String>();
    /**
     * 最短路长度
     */
    private int length;
    /**
     * 边集
     */
    private Edge[] edges;

    /**
     * 构造器
     *
     * @param choice 算法的选择
     * @param matrix 邻接矩阵
     * @param start  开始结点序号
     * @param end    结束结点序号
     * @param edges  边集
     */
    public CountShortest(int choice, AdjacencyMatrix matrix, String start, String end, Edge[] edges) {
        this.choice = choice;
        this.matrix = processMatrix(matrix).getFirst();
        this.vertices = processMatrix(matrix).getSecond();
        this.start = processNode(start, end).getFirst();
        this.end = processNode(start, end).getSecond();
        this.edges = edges;
    }

    /**
     * @param matrix 邻接矩阵
     * @return 自己实现的Pair，第一个元素是距离的矩阵，第二个元素是景点对应序号的集合
     */
    private MyPair<int[][], String[]> processMatrix(AdjacencyMatrix matrix) {
        return matrix.processGraph();
    }

    /**
     * @param start 开始结点名
     * @param end   终止结点名
     * @return 返回一个MyPair，前一个整数是开始结点序号，后一个整数是结束结点序号
     */
    private MyPair<Integer, Integer> processNode(String start, String end) {
        MyPair<Integer, Integer> newPair = new MyPair<>(0, 0);
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].equals(start)) {
                newPair.setFirst(i);
            }
            if (vertices[i].equals(end)) {
                newPair.setSecond(i);
            }
        }
        return newPair;
    }

    /**
     * 用弗洛伊德算法算最短路
     *
     * @return MyPair:前一个是具体路径的集合，后一个是最短路径长度
     */
    private MyPair<ArrayList<String>, Integer> shortestWithFloyd() {
        int[][] path = new int[vertices.length][vertices.length];
        int[][] dist = new int[vertices.length][vertices.length];

        // 方法一，不太好，弃用

//        // 初始化
//        for (int i = 0; i < vertices.length; i++) {
//            for (int j = 0; j < vertices.length; j++) {
//                dist[i][j] = matrix[i][j];    // "顶点i"到"顶点j"的路径长度为"i到j的权值"。
//                path[i][j] = j;                // "顶点i"到"顶点j"的最短路径是经过顶点j。
//            }
//        }
//
//        // 计算最短路径
//        for (int k = 0; k < vertices.length; k++) {
//            for (int i = 0; i < vertices.length; i++) {
//                for (int j = 0; j < vertices.length; j++) {
//                    // 如果经过下标为k顶点路径比原两点间路径更短，则更新dist[i][j]和path[i][j]
//                    int tmp = (dist[i][k] == INF || dist[k][j] == INF) ? INF : (dist[i][k] + dist[k][j]);
//                    if (dist[i][j] > tmp) {
//                        // "i到j最短路径"对应的值设，为更小的一个(即经过k)
//                        dist[i][j] = tmp;
//                        // "i到j最短路径"对应的路径，经过k
//                        path[i][j] = path[i][k];
//                    }
//                }
//            }
//        }
//
//        // 打印floyd最短路径的结果
////        System.out.printf("floyd: \n");
////        for (int i = 0; i < vertices.length; i++) {
////            for (int j = 0; j < vertices.length; j++)
////                System.out.printf("%2d  ", dist[i][j]);
////            System.out.printf("\n");
////        }
//        findPath(start,end,path,dist);

        // 方法二

        floyd(matrix, path, dist);
        // 最短路添加开始结点
        result.add(vertices[start]);
        // 补全最短路径
        findPath(start, end, path, dist);
        // 最短路添加结束结点
        result.add(vertices[end]);
        // 最短路径长度
        length = dist[start][end];
        return new MyPair<>(result, length);
    }

    /**
     * 用弗洛伊德算法算最短路
     *
     * @param matrix 邻接矩阵
     * @param path   最短路径数组
     * @param dist   当前距离矩阵
     */
    private void floyd(int[][] matrix, int[][] path, int[][] dist) {
        int size = matrix.length;
        //初始化距离矩阵和路径数组
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = -1;
                dist[i][j] = matrix[i][j];
            }
        }
        // 更新距离矩阵和路径数组
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF &&
                            dist[k][j] != INF &&
                            dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }

    }

    private void findPath(int i, int j, int[][] path, int[][] dist) {
        int k = path[i][j];
        // 如果不存在下一个点，返回
        if (k == -1) {
            return;
        }
        //递归
        findPath(i, k, path, dist);
        // 添加结点
        result.add(vertices[k]);
        // 递归
        findPath(k, j, path, dist);
    }

    /**
     * 用SPFA算法算最短路
     *
     * @return MyPair:前一个是具体路径的集合，后一个是最短路径长度
     */
    private MyPair<ArrayList<String>, Integer> shortestWithSPFA() {
        // find代表是否找到最短路
        boolean find = true;
        // 用于找最短路径
        int[] pre = new int[vertices.length];
        // 距离矩阵
        int[][] dist0 = new int[vertices.length][vertices.length];
        // 初始化距离矩阵
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                dist0[i][j] = matrix[i][j];
            }
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        int[] result0 = new int[vertices.length];
        // 表示结点是否遍历的数组
        boolean[] used = new boolean[vertices.length];
        // 被遍历次数
        int[] number = new int[vertices.length];
        // 初始化
        for (int i = 0; i < vertices.length; i++) {
            result0[i] = 10000;
            used[i] = false;
        }
        //第start个顶点到自身距离为0
        result0[start] = 0;
        //表示第start个顶点进入数组队
        used[start] = true;
        //表示第start个顶点已被遍历一次
        number[start] = 1;
        //第start个顶点入队
        arrayList.add(start);
        while (arrayList.size() != 0) {
            //获取队中第一个元素
            int a = arrayList.get(0);
            //删除队中第一个元素
            arrayList.remove(0);
            for (int i = 0; i < edges.length; i++) {
                Node to = edges[i].getTo();
                Node from = edges[i].getFrom();
                int toNum = 0;
                int fromNum = 0;
                // 找结点to和from对应的编号
                for (int k = 0; k < vertices.length; k++) {
                    if (vertices[k].equals(to.getTouristAttraction().getName())) {
                        toNum = k;
                    }
                    if (vertices[k].equals(from.getTouristAttraction().getName())) {
                        fromNum = k;
                    }
                }
                //当队的第一个元素等于从from到to的结点的边的起点时
                if (a == fromNum && result0[toNum] > (result0[fromNum] + dist0[fromNum][toNum])) {
                    result0[toNum] = result0[fromNum] + dist0[fromNum][toNum];
                    pre[toNum] = fromNum;
                    if (!used[toNum]) {
                        arrayList.add(toNum);
                        number[toNum]++;
                        if (number[toNum] > vertices.length) {
                            find = false;
                        }
                        //表示边的终点已进入队中
                        used[toNum] = true;
                    }
                }
            }
            //表示a已经移出队外
            used[a] = false;
        }
        if (!find) return null;
        return new MyPair<ArrayList<String>, Integer>(findPathB(start, end, pre), result0[end]);
    }

    /**
     * 用贝尔曼 福德算法算最短路
     *
     * @return MyPair:前一个是具体路径的集合，后一个是最短路径长度
     */
    private MyPair<ArrayList<String>, Integer> shortestWithBellmanFord() {
        // 初始化距离矩阵
        int[][] dist0 = new int[vertices.length][vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                dist0[i][j] = matrix[i][j];
            }
        }
        // 前一个结点到当前结点的距离数组
        int[] dist = new int[vertices.length];
        // 用于找最短路径的数组
        int[] pre = new int[vertices.length];
        // 初始化
        for (int i = 0; i < vertices.length; i++)
            dist[i] = (i == start ? 0 : 100000);
        // 对于每一个结点，对每一条边都算最短路，与边的松弛结合
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < edges.length; j++) {
                Node to = edges[j].getTo();
                Node from = edges[j].getFrom();
                int toNum = 0;
                int fromNum = 0;
                for (int k = 0; k < vertices.length; k++) {
                    if (vertices[k].equals(to.getTouristAttraction().getName())) {
                        toNum = k;
                    }
                    if (vertices[k].equals(from.getTouristAttraction().getName())) {
                        fromNum = k;
                    }
                }

                if (dist[toNum] > (dist[fromNum] + dist0[fromNum][toNum])) {
                    dist[toNum] = dist[fromNum] + dist0[fromNum][toNum];
                    pre[toNum] = fromNum;
                }
            }

        }
        // 检测负权回路
        boolean flag = true;
        for (int i = 0; i < edges.length; i++) {
            Node to = edges[i].getTo();
            Node from = edges[i].getFrom();
            int toNum = 0;
            int fromNum = 0;
            for (int k = 0; k < vertices.length; k++) {
                if (vertices[k].equals(to.getTouristAttraction().getName())) {
                    toNum = k;
                }
                if (vertices[k].equals(from.getTouristAttraction().getName())) {
                    fromNum = k;
                }
            }
            if (dist[toNum] > dist[fromNum] + dist0[fromNum][toNum]) {
                flag = false;
                break;
            }

        }

        if (!flag) {
            return null;
        }
        return new MyPair<ArrayList<String>, Integer>(findPathB(start, end, pre), dist[end]);
    }

    /**
     * 找Bellman-Ford 和SPFA算法的最短路径
     *
     * @param start 开始结点
     * @param root  当前结点
     * @param pre   之前一个结点
     * @return 最短路径
     */
    private ArrayList<String> findPathB(int start, int root, int[] pre) {
        // 反向查找
        ArrayList<String> arrayList = new ArrayList<>();
        // 当前结点不为开始结点
        while (root != start) {
            arrayList.add(vertices[root]);
            root = pre[root];
        }
        arrayList.add(vertices[start]);
        return arrayList;
    }

    /**
     * 计算Bellman-Ford和SPFA算法的最短路径长度
     *
     * @param start 开始结点
     * @param root  当前结点
     * @param pre   之前一个结点
     * @param dist  距离矩阵
     * @return 最短路径长度
     */
    private int countPathB(int start, int root, int[] pre, int[] dist) {
        int length = 0;

        while (root != start) {
            length += dist[root];
            root = pre[root];
        }
        return length;
    }

    /**
     * 算法选择
     *
     * @return 最短路径和最短路长度
     */
    public MyPair<ArrayList<String>, Integer> shortest() {
        switch (choice) {
            case 2:
                return shortestWithFloyd();
            case 3:
                return shortestWithBellmanFord();
            case 4:
                return shortestWithSPFA();
            default:
                return null;
        }
    }
}
