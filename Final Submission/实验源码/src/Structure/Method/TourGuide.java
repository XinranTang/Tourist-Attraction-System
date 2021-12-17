package Structure.Method;

import Structure.AdjacencyMatrix;
import Structure.Edge;
import Structure.MyPair;
import Structure.Procedure;

import java.util.ArrayList;

/**
 * 导游路线图的方法类
 */
public class TourGuide {
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
     * 路线长度
     */
    private int length;
    /**
     * 边集
     */
    private Edge[] edges;

    /**
     * @param matrix 邻接矩阵
     * @param start  开始结点
     * @param end    结束结点
     * @param edges  边集
     */
    public TourGuide(AdjacencyMatrix matrix, String start, String end, Edge[] edges) {
        this.matrix = processMatrix(matrix).getFirst();
        this.vertices = processMatrix(matrix).getSecond();
        this.start = processNode(start, end).getFirst();
        this.end = processNode(start, end).getSecond();
        this.edges = edges;
    }

    /**
     * @param matrix 邻接矩阵
     * @return 距离矩阵
     */
    private MyPair<int[][], String[]> processMatrix(AdjacencyMatrix matrix) {
        return matrix.processGraph();
    }

    /**
     * @param start 开始结点名
     * @param end   结束结点名
     * @return 返回一个MyPair，前一个整数是开始结点序号，后一个整数是结束结点序号
     */
    private MyPair<Integer, Integer> processNode(String start, String end) {
        // 处理方法与CountShortest方法类中的相同
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
     * 搜索路线
     *
     * @return 返回一个MyPair，前一个整数是路径，后一个整数是长度
     */
    public MyPair<ArrayList<String>, Integer> searchGraph() {
        // 对每个顶点为初始点进行比遍历寻找汉密尔顿回路
        int[] path = new int[100];
        // 作为保存当前结点
        int cur_vertex = 0;
        // 汉密尔顿回路长度
        int length = 0;
        // 最小长度
        int min = 10000;

        // 初始化访问状态数组
        int[][] dist = new int[vertices.length][vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            System.arraycopy(matrix[i], 0, dist[i], 0, vertices.length);
        }

        int[] isVisited = new int[vertices.length];
        for (int i = 0; i < isVisited.length; i++) {
            isVisited[i] = 0;
        }
        // 标记开始结点为已访问
        isVisited[start] = 1;
        // 保存到临时路径数组的第一个
        path[0] = start;
        // 保存当前顶点
        cur_vertex = start;
        // 记录路径下一个空结点的指针
        int pathl = 1;
        // 访问剩余的结点
        for (int j = 1; j < vertices.length; j++) {
            int k = 0;
            // 寻找到第一个未访问的结点
            for (k = 1; k < vertices.length; k++) {
                if (isVisited[k] == 0) {
                    break;
                }
            }
            if (k >= vertices.length) {
                break;
            }
            // 保存当前顶点到该结点的路径长度
            int tmp = dist[cur_vertex][k];
            // 向后寻找有没有路径更短的节点
            for (int m = k + 1; m < vertices.length; m++) {
                if ((isVisited[m] == 0) && (tmp > dist[cur_vertex][m])) {
                    // 更新当前最短路径
                    tmp = dist[cur_vertex][m];
                    // 更新第一个未被访问的结点
                    k = m;
                }
            }
            if (tmp > 1000) {
                // 说明已经遍历到底了，跳出找剩余结点
                break;
            } else {
                // 保存路径上的结点
                path[pathl] = k;
                // 下一个路径的位置
                pathl++;
                // 标记为已访问
                isVisited[k] = 1;
                // 更新当前结点
                cur_vertex = k;
                // 更新长度
                length += tmp;
                // 当前长度大于最小长度，则改路径无效，跳出循环
                if (length > min) {
                    break;
                }
            }

        }
        int[] tempArray = new int[vertices.length];
        // tempArray的长度
        int templ = 0;
        for (int i = 0; i < vertices.length; i++) {
            if (isVisited[i] == 0) {
                // 如果没有被遍历过，加入tempArray
                tempArray[templ] = i;
                templ++;
            }
        }
        // 如果存在未访问结点
        if (tempArray.length != 0) {
            // 对于第一个未访问结点和之前跳出循环的那个结点算最短路
            MyPair<ArrayList<String>, Integer> myPair = Procedure.shortestPath(vertices[cur_vertex], vertices[tempArray[0]], 2);
            // 在vertices中找最短路的景点对应序号，加入path中
            for (int i = 1; i < myPair.getFirst().size(); i++) {
                for (int m = 0; m < vertices.length; m++) {
                    if (vertices[m].equals(myPair.getFirst().get(i))) {
                        // path继续添加结点
                        path[pathl + i - 1] = m;
                        isVisited[m] = 1;
                        break;
                    }
                }
            }
            // 更新pathl的位置（这里一开始错了：没有减去1）
            pathl = pathl + myPair.getFirst().size() - 1;
            // 更新路径长度
            length += myPair.getSecond();
            // 对于未访问结点，每个之间以最短路径连接
            for (int j = 1; j < templ; j++) {
                myPair = Procedure.shortestPath(vertices[tempArray[j - 1]], vertices[tempArray[j]], 2);
                // 在vertices中找最短路的景点对应序号，加入path中
                for (int i = 1; i < myPair.getFirst().size(); i++) {
                    for (int m = 0; m < vertices.length; m++) {
                        if (vertices[m].equals(myPair.getFirst().get(i))) {
                            path[pathl + i - 1] = m;
                            isVisited[m] = 1;
                        }
                    }
                }
                // 更新pathl的位置
                pathl = pathl + myPair.getFirst().size() - 1;
                // 更新路径长度
                length += myPair.getSecond();
            }
            // 更新当前结点
            cur_vertex = tempArray[templ - 1];
        }
        // 收尾，最后要到达end结点
        ArrayList<String> arrayList = new ArrayList<>();
        if (dist[cur_vertex][end] > 1000) {
            // 如果一下子到不了，算最短路
            MyPair<ArrayList<String>, Integer> myPair = Procedure.shortestPath(vertices[cur_vertex], vertices[end], 2);
            for (int i = 1; i < myPair.getFirst().size(); i++) {
                for (int m = 0; m < vertices.length; m++) {
                    if (vertices[m].equals(myPair.getFirst().get(i))) {
                        path[pathl + i - 1] = m;
                        isVisited[m] = 1;
                    }
                }
            }
            pathl = pathl + myPair.getFirst().size() - 1;
            length += myPair.getSecond();
        } else {
            // 一下子到了，直接加长度
            length += dist[cur_vertex][end];
        }
        for (int i = 0; i < pathl; i++) {
            arrayList.add(vertices[path[i]]);
        }
        //返回导游路线和最小长度的Pair
        return new MyPair<>(arrayList, length);

    }
    // 算法2：状态压缩dp(动态规划)算法+弗洛伊德算法

//    public MyPair<ArrayList<String>, Integer> searchGraphDp() {
//        int maxn = 25;
//        int mat[][] = new int[maxn][maxn];
//        int dp[][] = new int[(1 << vertices.length) + 10][maxn];
//        ArrayList path[][] = new ArrayList[1<<maxn][maxn];
//        // 景点数
//        int n = vertices.length;
//        // 边数
//        int m = edges.length;
//        int length = 0;
//
//        int dist[][] = new int[vertices.length][vertices.length];
//        for (int i = 0; i < vertices.length; i++) {
//            for (int j = 0; j < vertices.length; j++) {
//                dist[i][j] = matrix[i][j];
//            }
//        }
//        int u, v, w;
//        int from = 0, to = 0;
//        while (--m > 0) {
//            for (int k = 0; k < vertices.length; k++) {
//                if (vertices[k].equals(edges[m].from.getTouristAttraction().getName())) {
//                    from = k;
//                }
//                if (vertices[k].equals(edges[m].to.getTouristAttraction().getName())) {
//                    to = k;
//                }
//            }
//            u = from;
//            v = to;
//            w = edges[m].weight;
//            if (dist[u][v] > w) {
//                dist[u][v] = dist[v][u] = w;
//            }
//        }
//        for (int i = 0; i < vertices.length; i++) {
//            for (int j = 0; j < vertices.length; j++) {
//                dist[i][j] = Procedure.shortestPath(vertices[i], vertices[j], 2).getSecond();
//            }
//        }
//        dp[1][start] = 0;
//        path[1][start].add(start);
//        for (int s = 1; s < (1 << n); s++) {
//            for (int i = 0; i < n; i++) {
//                if ((s & (1 << i)) > 0) {
//                    for (int j = 0; j < n; j++) {
//                        if ((s & (1 << j)) == 0) {
//                            path[(1 << j) | s][j] = path[s][i];
//                            path[(1 << j) | s][j].add(j);
//                            dp[s | (1 << j)][j] = dp[s | (1 << j)][i] < dp[s][i] + mat[i][j] ? dp[s | (1 << j)][i] : dp[s][i] + mat[i][j];
//                        }
//                    }
//                }
//            }
//        }
//        int ans = 0x3f3f3f3f;
//        for (int i = 1; i < n; i++) {
//            ans = ans < dp[(1 << n) - 1][i] + mat[i][start] ? ans : dp[(1 << n) - 1][i] + mat[i][start];
//            if(dp[m])
//        }
//        if (n == 1) length = 0;
//        else {
//            length = ans;
//        }
//        path[]
//    }
//
//}
}
