import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Procedure {
    private AdjacencyList adjacencyList = new AdjacencyList();
    private AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix();

    private HashMap<String, TouristAttraction> touristAttractionInformation = new HashMap<>();

    public void createGraph() {
        // 读边，构建图
        try {
            // 读文件，用gbk编码
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new FileInputStream("TouristAttractions.txt")), "gbk"));
            String line;
            String[] lineList;
            // 读取并划分文件中每一行
            while ((line = bufferedReader.readLine()) != null) {
                lineList = line.split("——");
                // 第一个和第二个是景点
                for (int i = 0; i <= 1; i++) {
                    if (adjacencyList.find(lineList[i]) == null) {
                        adjacencyList.add(lineList[i]);
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
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(adjacencyList.toString());
    }

    public void outputGraph() {
        adjacencyMatrix.createGraph(adjacencyList);
        System.out.println(adjacencyMatrix.toString());
    }

    public void readInformation() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("Information.txt"), "gbk"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                touristAttractionInformation.put(line, new TouristAttraction(line, bufferedReader.readLine()));
            }
            bufferedReader.close();
            touristAttractionInformation.forEach((name, touristAttraction) -> {
                System.out.println(name);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<TouristAttraction> findTouristAttraction(String[] keyWords) {
        HashSet<TouristAttraction> attractions = new HashSet<>();
        boolean flag = true;
        // 用KMP比较字符串
        for (TouristAttraction each : touristAttractionInformation.values()) {
            for (String keyWord : keyWords) {
                // 如果有一个关键字没找到的话，整个就没找到
                flag = flag && (KMP(each.getInformation(), keyWord) != -1 || KMP(each.getName(), keyWord) != -1);
            }
            if (flag) {
                // 如果所有关键字都找到了
                attractions.add(each);
            }
            flag = true;
        }

        return attractions;
    }

    public int KMP(String text, String pattern) {
        char[] t = text.toCharArray();
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

    public void search() {
        Scanner s = new Scanner(System.in);
        System.out.println("搜索关键字：");
        HashSet<TouristAttraction> attractions = findTouristAttraction(s.nextLine().split(","));
        if (attractions.isEmpty()) {
            System.out.println("没有符合条件的结果！");
        } else {
            attractions.forEach(touristAttraction -> {
                System.out.println(touristAttraction.getName());
                System.out.println(touristAttraction.getInformation());
            });
        }
    }

    public void insertAttraction() {
        Scanner s = new Scanner(System.in);
        System.out.println("景点名：");
        String name = s.nextLine();
        System.out.println("景点介绍：");
        String information = s.nextLine();
        TouristAttraction touristAttraction = new TouristAttraction(name, information);
        touristAttractionInformation.put(name, touristAttraction);
        Node newNode = new Node(touristAttraction);
        adjacencyList.add(name);
    }

    public void deleteAttraction() {
        Scanner s = new Scanner(System.in);
        System.out.println("景点名：");
        String keyWord = s.nextLine();
        if (findTouristAttraction(keyWord.split(",")).isEmpty()) {
            System.out.println("没有相关景点！");
        } else {
            // 在景点信息里删除信息
            touristAttractionInformation.remove(keyWord);
            adjacencyList.remove(keyWord);
            adjacencyMatrix.createGraph(adjacencyList);
            System.out.println(adjacencyList.toString());
        }

    }

    public void deleteRoad() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入想删除的路：");
        String[] lineList = s.nextLine().split("——");
        boolean result = adjacencyList.removeRoad(lineList);
        System.out.println(adjacencyList.toString());
        if (!result) {
            System.out.println("未找到对应信息！");
        }
        ;
    }

    public void insertRoad() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入想添加的路：");
        String[] lineList = s.nextLine().split("——");
        // 如果连景点都不存在，添加景点
        for (int i = 0; i <= 1; i++) {
            if (adjacencyList.find(lineList[i]) == null) {
                adjacencyList.add(lineList[i]);
            }
        }
        // 构建前一个景点的结点
        Node node1 = new Node(adjacencyList.find(lineList[0]), Integer.parseInt(lineList[2]));
        // 构建后一个景点的结点
        Node node2 = new Node(adjacencyList.find(lineList[1]), Integer.parseInt(lineList[2]));
        // 添加结点，要加两次，因为是无向图
        adjacencyList.addNode(node1, node2);
        adjacencyList.addNode(node2, node1);
        System.out.println(adjacencyList.toString());
    }

    public void sort() {
        Scanner s = new Scanner(System.in);
        System.out.println("排序方式，1:冒泡，2:选择，3：");
        Sort sort = new Sort();
        int choice = Integer.parseInt(s.nextLine());
        ArrayList<TouristAttraction> touristAttractions;
        switch (choice) {
            case 1:
                // Bubble Sort
                touristAttractions = sort.BubbleSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values()), adjacencyList);
                touristAttractions.forEach(touristAttraction -> {
                    System.out.println(touristAttraction.getName());
                });
                break;
            case 2:
                // Selection Sort
                touristAttractions = sort.SelectionSort(new ArrayList<TouristAttraction>(touristAttractionInformation.values()), adjacencyList);
                touristAttractions.forEach(touristAttraction -> {
                    System.out.println(touristAttraction.getName());
                });
                break;
        }
    }

    public void shortestPath() {
        Scanner s = new Scanner(System.in);
        System.out.println("输入要查询距离的两个景点的名称：");
        String start = s.nextLine();
        String end = s.nextLine();
        Stack<Edge> stack = adjacencyList.getShortestPath(start, end);
        System.out.print(start);
        while (!stack.isEmpty()) {
            System.out.print("->" + stack.pop().to.getTouristAttraction().getName());
        }
        System.out.println();
    }

    public void CreateTourSortGraph() {
        Scanner s = new Scanner(System.in);
        System.out.println("1.A进A出 2.A进B出");
        int choice = Integer.parseInt(s.nextLine());
        if (choice == 1) {
            System.out.println("请输入入口：");
            String in = s.nextLine();
            adjacencyList.singleTourSortGraph(in);
        } else if (choice == 2) {
            System.out.println("请输入入口和出口：");
            String in =s.nextLine();
            String out = s.nextLine();
        }
    }

    public static void main(String[] args) {
        Procedure procedure = new Procedure();
        procedure.readInformation();
        procedure.createGraph();
//        procedure.outputGraph();
//        procedure.search();
//        procedure.deleteAttraction();
//        procedure.insertAttraction();
//        procedure.insertRoad();
//        procedure.deleteRoad();
//        procedure.sort();
//        procedure.shortestPath();
    }
}
