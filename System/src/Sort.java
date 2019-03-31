import java.util.ArrayList;
import java.util.Scanner;

public class Sort {
    private Scanner s = new Scanner(System.in);

    private int makeChoice() {
        System.out.println("选择排序类型：1，按名字 2，按分支数量");
        return Integer.parseInt(s.nextLine());
    }

    public ArrayList<TouristAttraction> BubbleSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        int choice = makeChoice();
        // TODO:倒序排列
        for (int i = touristAttractions.size() - 1; i >= 1; i--) {
            for (int j = 0; j < i; j++) {
                if (touristAttractions.get(j).compareTo(touristAttractions.get(j + 1), choice, adjacencyList) > 0) {
                    TouristAttraction temp;
                    temp = touristAttractions.get(j);
                    touristAttractions.set(j, touristAttractions.get(j + 1));
                    touristAttractions.set(j + 1, temp);
                }
            }
        }
        return touristAttractions;
    }

    public ArrayList<TouristAttraction> SelectionSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        int max;
        int choice = makeChoice();
        TouristAttraction temp;
        for (int i = touristAttractions.size() - 1; i >= 1; i--) {
            max = i;
            for (int j = 0; j <= i; j++) {
                if (touristAttractions.get(max).compareTo(touristAttractions.get(j), choice, adjacencyList) < 0) {
                    max = j;
                }
            }
            temp = touristAttractions.get(i);
            touristAttractions.set(i, touristAttractions.get(max));
            touristAttractions.set(max, temp);
        }
        return touristAttractions;
    }

    public ArrayList<TouristAttraction> QuickSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        // TODO: implementation
        return touristAttractions;
    }

    public ArrayList<TouristAttraction> MergeAndConquerSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        // TODO: implementation
        return touristAttractions;
    }
}
