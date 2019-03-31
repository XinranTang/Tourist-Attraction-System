package Structure;

import java.text.Collator;
import java.util.Locale;

/**
 * 旅游景点类
 */
public class TouristAttraction {
    /**
     * 旅游景点名
     */
    private String name;
    /**
     * 旅游景点信息
     */
    private String information;
    /**
     * 在界面中的位置，用于画图
     */
    private int[] position = new int[2];

    /**
     * @param name 旅游景点名
     * @param x    旅游景点X坐标
     * @param y    旅游景点Y坐标
     */
    public TouristAttraction(String name, int x, int y) {
        this.name = name;
        this.position[0] = x;
        this.position[1] = y;
    }

    /**
     * @param name        旅游景点名
     * @param information 旅游景点信息
     */
    public TouristAttraction(String name, String information) {
        this.name = name;
        this.information = information;
    }

    /**
     * @param name 旅游景点名
     */
    public TouristAttraction(String name) {
        this.name = name;
        this.information = null;
    }

    /**
     * @return 旅游景点名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 旅游景点名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 旅游景点信息
     */
    public String getInformation() {
        return information;
    }

    /**
     * @param information 旅游景点信息
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * @param another 进行比较的另一个景点
     * @return 比较结果
     */
    private int compareToByName(TouristAttraction another) {
        // compareTo( )方法在对字符串进行比较时，比较的是Unicode码，并不能对汉字进行准确的排序，所以汉字比较时会出现比较混乱的结果
        Collator cmp = Collator.getInstance(Locale.CHINA);
        return cmp.compare(this.name, another.getName());
    }

    /**
     * 通过分支数进行比较
     *
     * @param another       进行比较的另一个景点
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 比较结果
     */
    private int compareToByBranch(TouristAttraction another, AdjacencyList adjacencyList) {
        int compare = adjacencyList.getBranches(this.name) - adjacencyList.getBranches(another.getName());
        // 分支数少返回-1
        if (compare < 0) return -1;
            // 分支数相等返回0
        else if (compare == 0) return 0;
        else {
            // 分支数大返回1
            return 1;
        }
    }

    /**
     * 比较景点
     *
     * @param another       进行比较的另一个景点
     * @param choice        比较范围选择：比较景点名或景点分支数
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 比较结果
     */
    public int compareTo(TouristAttraction another, int choice, AdjacencyList adjacencyList) {
        switch (choice) {
            case 1:
                // compare by name
                return compareToByName(another);
            case 2:
                // compare by branches
                return compareToByBranch(another, adjacencyList);
            default:
                return 0;
        }
    }

    /**
     * @return 景点在界面的位置
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * @param x 景点在界面的位置X坐标
     * @param y 景点在界面的位置Y坐标
     */
    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }
}
