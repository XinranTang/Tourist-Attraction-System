import java.text.Collator;
import java.util.Locale;

public class TouristAttraction {
    private String name;
    private String information;

    public TouristAttraction(String name, String information) {
        this.name = name;
        this.information = information;
    }

    public TouristAttraction(String name) {
        this.name = name;
        this.information = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    private int compareToByName(TouristAttraction another) {
        // compareTo( )方法在对字符串进行比较时，比较的是Unicode码，并不能对汉字进行准确的排序，所以汉字比较时会出现比较混乱的结果
        Collator cmp = Collator.getInstance(Locale.CHINA);
        return cmp.compare(this.name,another.getName());
    }

    private int compareToByBranch(TouristAttraction another, AdjacencyList adjacencyList) {
        return adjacencyList.getBranches(this.name) - adjacencyList.getBranches(another.getName());
    }

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
}
