package Structure;

import Structure.Method.CountShortest;
import Structure.Method.Sort;
import Structure.Method.TourGuide;
import UI.Main.StageManager;

import java.io.*;
import java.util.*;

/**
 * 功能实现的框架
 */
public class Procedure {
    /**
     * 表示有多少位置已经被用过了
     */
    private static int pCount = 0;
    /**
     * 一些提供的位置，用于界面绘图，暂时不需要用户自己输入
     */
    private static int[][] positionArray = {
            {200, 10}, {120, 90}, {280, 90}, {40, 170}, {160, 170}, {240, 170}, {360, 190}, {140, 260}, {50, 230}, {110, 370}, {200, 310}, {340, 370}, {240, 280}, {240, 440}, {340, 440}, {200, 500}
    };
    /**
     * 邻接链表
     */
    private static AdjacencyList adjacencyList = new AdjacencyList();
    /**
     * 邻接矩阵
     */
    private static AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix();

    /**
     * 旅游景点信息集合
     */
    private static MyHashMap<String, TouristAttraction> touristAttractionInformation = new MyHashMap<>();

    public static AdjacencyMatrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * @param file 读取文件
     * @return 创建的邻接链表
     */
    public static AdjacencyList createGraph(File file) {
        // 读边，构建图
        try {
            // 读文件，用gbk编码
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new FileInputStream(file)), "gbk"));
            String line;
            String[] lineList;
            // 读取并划分文件中每一行
            pCount = 0;
            ArrayList<Edge> edges = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                lineList = line.split("——");
                // 第一个和第二个是景点
                // 在每一个景点中同时加上位置
                for (int i = 0; i <= 1; i++) {
                    if (adjacencyList.find(lineList[i]) == null) {
                        adjacencyList.add(lineList[i], positionArray[pCount][0], positionArray[pCount][1]);
                        // 该位置已经被占用
                        pCount++;
                    }
                }
                // 现在开始构建邻接链表
                // 构建前一个景点的结点
                // 后一个参数是第二个结点与第一个结点之间的距离
                Node node1 = new Node(adjacencyList.find(lineList[0]), Integer.parseInt(lineList[2]));
                // 构建后一个景点的结点
                Node node2 = new Node(adjacencyList.find(lineList[1]), Integer.parseInt(lineList[2]));
                // 添加结点，要加两次，因为是无向图
                adjacencyList.addNode(node1, node2);
                adjacencyList.addNode(node2, node1);
                Edge newEdge1 = new Edge(node1, node2, Integer.parseInt(lineList[2]));
                Edge newEdge2 = new Edge(node2, node1, Integer.parseInt(lineList[2]));
                edges.add(newEdge1);
                edges.add(newEdge2);
            }
            adjacencyList.setEdges(edges);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adjacencyList;
    }

    /**
     * @return 输出邻接矩阵
     */
    public static String outputGraph() {
        if (adjacencyList.size() == 0) {
            return "";
        } else {
            // 创建并输出邻接矩阵
            adjacencyMatrix.createGraph(adjacencyList);
            return adjacencyMatrix.toString();
        }
    }

    /**
     * 将景点或信息更改保存到文件
     *
     * @throws IOException IO异常
     */
    public static void toFile() throws IOException {
        // 调用图的toFile()方法保存到边的文件
        adjacencyMatrix.createGraph(StageManager.adjacencyList);
        adjacencyMatrix.toFile();
        // 将景点信息保存到信息文件
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("Information.txt")), "gbk"));
        for (MyHashMap.Node<String, TouristAttraction> each : touristAttractionInformation.entrySet) {
            bufferedWriter.write(each.key);
            bufferedWriter.newLine();
            if (each.value.getInformation() == null) {
                bufferedWriter.write("");
            } else {
                bufferedWriter.write(each.value.getInformation());
            }
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();

    }

    /**
     * @param file 景点信息文件
     * @return 景点信息
     */
    public static String readInformation(File file) {
        // 用于添加景点及信息
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 读取文件
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                // 景点信息集合添加景点
                touristAttractionInformation.put(line, new TouristAttraction(line, bufferedReader.readLine()));
            }
            bufferedReader.close();
            for (MyHashMap.Node<String, TouristAttraction> each : touristAttractionInformation.entrySet) {
                stringBuilder.append("【").append(each.getKey()).append("】\n").append(each.getValue().getInformation()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 用KMP算法查找景点
     *
     * @param keyWords 要找的景点关键词
     * @param choice   查找类型：查找景点名还是信息还是两个都找
     * @return 查找到的景点集合
     */
    private static ArrayList<TouristAttraction> findTouristAttraction(String[] keyWords, int choice) {
        ArrayList<TouristAttraction> attractions = new ArrayList<>();
        boolean flag = true;
        // 用KMP比较字符串
        for (TouristAttraction each : touristAttractionInformation.values) {
            for (String keyWord : keyWords) {
                // 如果有一个关键字没找到的话，整个就没找到
                switch (choice) {
                    case 1:
                        flag = flag && (KMP(each.getInformation(), keyWord) != -1 || KMP(each.getName(), keyWord) != -1);
                        break;
                    case 2:
                        flag = flag && (KMP(each.getInformation(), keyWord) != -1);
                        break;
                    case 3:
                        flag = flag && (KMP(each.getName(), keyWord) != -1);
                }
            }
            if (flag) {
                // 如果所有关键字都找到了
                boolean flag0 = false;
                for (TouristAttraction attraction : attractions) {
                    if (attraction.getName().equals(each.getName())) {
                        flag0 = true;
                    }
                }
                if (!flag0) {
                    attractions.add(each);
                }
            }
            flag = true;
        }

        return attractions;
    }

    /**
     * KMP字符串查找算法
     *
     * @param text    代查的字符串
     * @param pattern 查找的字符串
     * @return 是否找到
     */
    private static int KMP(String text, String pattern) {
        // text拆分的数组
        char[] t = text.toCharArray();
        // pattern拆分的数组
        char[] p = pattern.toCharArray();
        int i = 0;
        int j = 0;
        int[] next = new int[p.length];
        next[0] = -1;
        int k = -1;
        // 构建next数组
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        j = 0;
        // 比对过程
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == p.length) {
            // 返回主串中第一个字符出现的下标
            return i - j;
        } else {
            // 没找到返回-1
            return -1;
        }
    }

    /**
     * 基于字典树的景点名模糊匹配
     *
     * @param keyWord 查找关键词
     * @return 查到的景点名集合
     */
    private static ArrayList<TouristAttraction> findTouristAttractionSR(String keyWord) {
        // 利用景点信息构建字典树，之后在树中进行查找
        ArrayList<TouristAttraction> arrayList = new ArrayList<>();
        if (adjacencyList.find(keyWord) != null) {
            arrayList.add(touristAttractionInformation.get(keyWord));
        } else {
            boolean flag = true;
            MyTrieTree trieTree = new MyTrieTree();
            for (TouristAttraction each : touristAttractionInformation.values) {
                trieTree.insert(each.getName());
            }
            // 查找的结果集合
            ArrayList<String> arrayList1 = trieTree.search(keyWord);
            for (String each : arrayList1) {
                arrayList.add(touristAttractionInformation.get(each));
            }
        }
        return arrayList;
    }

    /**
     * 景点查找
     * 对于外部采用顺序查找，字符串的比对可采用两种算法
     *
     * @param s       关键词，多关键词用英文逗号分隔
     * @param choice0 算法的选择
     * @param choice  查找范围的选择
     * @return 查找到的结果集合
     */
    public static ArrayList<TouristAttraction> search(String s, int choice0, int choice) {
        ArrayList<TouristAttraction> attractions = null;
        if (choice0 == 1) {
            // 选择KMP查找
            attractions = findTouristAttraction(s.split(","), choice);
        } else if (choice0 == 2) {
            // 选择用字典树进行查找
            attractions = findTouristAttractionSR(s);
        }
        if (attractions.isEmpty()) {
            return null;
        } else {
            return attractions;
        }
    }

    /**
     * 添加景点
     *
     * @param name        景点名
     * @param information 景点信息
     * @return 添加过景点后的邻接链表，用于画图
     */
    public static AdjacencyList insertAttraction(String name, String information) {
        // 如果景点已经有了，直接返回
        if (adjacencyList.getGraphHashMap().containsKey(name)) {
            return null;
        } else {
            // 插入景点
            TouristAttraction touristAttraction = new TouristAttraction(name, positionArray[pCount][0], positionArray[pCount][1]);
            pCount++;
            touristAttraction.setInformation(information);
            touristAttractionInformation.put(name, touristAttraction);
            Node newNode = new Node(touristAttraction);
            adjacencyList.add(name, positionArray[pCount][0], positionArray[pCount][1]);
            pCount++;
            return adjacencyList;
        }
    }

    /**
     * 删除景点
     *
     * @param keyWord 景点名
     * @return 删除过景点后的邻接链表，用于画图
     */
    public static AdjacencyList deleteAttraction(String keyWord) {
        // 只查名字
        if (findTouristAttraction(keyWord.split(","), 3).isEmpty()) {
            return null;
        } else {
            // 在景点信息里删除信息
            touristAttractionInformation.remove(keyWord);
            adjacencyList.remove(keyWord);
            adjacencyMatrix.createGraph(adjacencyList);
            return adjacencyList;
        }
    }

    /**
     * 删除一条路
     *
     * @param s 路的信息
     * @return 删除过路后的邻接链表，用于画图
     */
    public static AdjacencyList deleteRoad(String s) {
        // 拆分s，提取信息
        String[] lineList = s.split("——");
        boolean result = adjacencyList.removeRoad(lineList);
        if (!result) {
            return null;
        }
        return adjacencyList;

    }

    /**
     * 插入一条路
     *
     * @param s 路的信息
     * @return 插入过路后的邻接链表，用于画图
     */
    public static AdjacencyList insertRoad(String s) {
        try {
            String[] lineList = s.split("——");
            // 如果连景点都不存在，添加景点
            for (int i = 0; i <= 1; i++) {
                if (adjacencyList.find(lineList[i]) == null) {
                    adjacencyList.add(lineList[i], positionArray[pCount][0], positionArray[pCount][1]);
                    touristAttractionInformation.put(lineList[i], new TouristAttraction(lineList[i], positionArray[pCount][0], positionArray[pCount][1]));
                    pCount++;
                }
            }
            // 构建前一个景点的结点
            Node node1 = new Node(adjacencyList.find(lineList[0]), Integer.parseInt(lineList[2]));
            // 构建后一个景点的结点
            Node node2 = new Node(adjacencyList.find(lineList[1]), Integer.parseInt(lineList[2]));
            // 添加结点，要加两次，因为是无向图
            Boolean flag = adjacencyList.addNode(node1, node2);
            flag = flag && adjacencyList.addNode(node2, node1);
            if (flag) {
                adjacencyList.insertEdge(new Node(new TouristAttraction(lineList[0])), new Node(new TouristAttraction(lineList[1])), Integer.parseInt(lineList[2]));
                return adjacencyList;
            } else {
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("道路格式错误");
            return null;
        }
    }

    /**
     * 景点的排序
     *
     * @param choice  排序算法选择
     * @param choice0 排序对象选择：按名字还是按分支数排序
     * @return 排序过后的景点名的集合
     */
    public static ArrayList<TouristAttraction> sort(int choice, int choice0) {
        // 创建一个排序的方法类
        Sort sort = new Sort(choice0);
        // 根据算法选择调用不同算法
        ArrayList<TouristAttraction> touristAttractions;
        switch (choice) {
            case 1:
                // Bubble Sort
                return sort.BubbleSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values), adjacencyList);
            case 2:
                // Selection Sort
                return sort.SelectionSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values), adjacencyList);
            case 3:
                // Insertion Sort
                return sort.InsertionSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values), adjacencyList);
            case 4:
                // Shell Sort
                return sort.ShellSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values), adjacencyList);
            case 5:
                // Merge and Conquer Sort
                return sort.MergeAndConquerSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values), adjacencyList);
            case 6:
                // Quick Sort
                return sort.QuickSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values), adjacencyList);
        }
        return null;
    }

    /**
     * 计算景点间的最短路
     *
     * @param start  开始景点
     * @param end    终止景点
     * @param choice 算法的选择
     * @return 一个MyPair, 前一个元素是景点的最短路径，后一个是最短路径长度
     */
    public static MyPair<ArrayList<String>, Integer> shortestPath(String start, String end, int choice) {
        adjacencyMatrix.createGraph(adjacencyList);
        // 创建一个CountShortest方法类
        CountShortest countShortest = new CountShortest(choice, adjacencyMatrix, start, end, adjacencyList.getEdges());
        // 调用CountShortest类的shortest()方法计算最短路
        return countShortest.shortest();

    }

    /**
     * 单独写的用迪杰斯特拉算法计算最短路
     *
     * @param start  开始景点
     * @param end    终止景点
     * @param choice 算法的选择
     * @return 一个MyPair, 前一个元素是景点的最短路径，用自己实现的CarStack存，后一个是最短路径长度
     */
    public static MyPair<CarStack<Edge>, Integer> shortestPath0(String start, String end, int choice) {
        // 这里用了自己实现的栈 CarStack
        if (choice == 1) {
            return adjacencyList.getShortestPathWithDijkstra(start, end);
        } else {
            return null;
        }
    }

    /**
     * 创建导游路线图
     *
     * @param start 开始结点
     * @param end   终止结点，可以和开始结点一样
     * @return 一个MyPair, 前一个元素是景点的最短路径，后一个是导游路线长度
     */
    public static MyPair<ArrayList<String>, Integer> createTourSortGraph(String start, String end) {
        adjacencyMatrix.createGraph(adjacencyList);
        // 创建TourGuide方法类
        TourGuide search = new TourGuide(adjacencyMatrix, start, end, adjacencyList.getEdges());
        return search.searchGraph();
    }

    // 测试语句

//    public static void main(String[] args) {
//        Procedure.readInformation(new File("TouristAttractions.txt"));
//        Procedure.createGraph();
//        Procedure.outputGraph();
//        Procedure.search();
////        procedure.deleteAttraction();
////        procedure.insertAttraction();
////        procedure.insertRoad();
////        procedure.deleteRoad();
//        Procedure.sort();
//        Procedure.sort();
//        Procedure.shortestPath();
//    }
}
