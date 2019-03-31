package Structure.Method;

import Structure.AdjacencyList;
import Structure.TouristAttraction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 景点排序的方法类
 */
public class Sort {
    /**
     * 排序算法的选择
     */
    private int choice;

    /**
     * @param choice 排序算法的选择
     */
    public Sort(int choice) {
        this.choice = choice;
    }

    /**
     * 冒泡排序
     * @param touristAttractions 景点信息
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 排序好的景点名集合
     */
    public ArrayList<TouristAttraction> BubbleSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        int choice = makeChoice();
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

    /**
     * 选择排序
     * @param touristAttractions 景点信息
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 排序好的景点名集合
     */
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

    /**
     * 快速排序
     * @param touristAttractions 景点信息
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 排序好的景点名集合
     */
    public ArrayList<TouristAttraction> QuickSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        TouristAttraction[] a = new TouristAttraction[touristAttractions.size()];
        for(int i =0;i<touristAttractions.size();i++){
            a[i] = touristAttractions.get(i);
        }
        // 利用递归
        QSort(a,0,a.length-1,adjacencyList);
        for(int i =0;i<touristAttractions.size();i++){
            touristAttractions.set(i,a[i]);
        }
        return touristAttractions;
    }

    /**
     * 快速排序
     * @param a 待排数组
     * @param lo 下界
     * @param hi 上界
     * @param adjacencyList 邻接链表，用于计算分支数
     */
    private void QSort(TouristAttraction[] a,int lo,int hi, AdjacencyList adjacencyList){
        // 递归
        if(hi<=lo) return;
        // 划分
        // j为哨兵
        int j = partition(a,lo,hi,adjacencyList);
        // 左右分别进行进一步排序
        QSort(a,lo,j-1,adjacencyList);
        QSort(a,j+1,hi,adjacencyList);
    }

    /**
     * 快速排序的左右划分
     * @param a 待排数组
     * @param lo 下界
     * @param hi 上界
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 哨兵位置
     */
    private int partition(TouristAttraction[] a,int lo,int hi, AdjacencyList adjacencyList){
        int i = lo;
        int j = hi+1;
        TouristAttraction v = a[lo];
        // 把小于哨兵的都放到一边，大于哨兵的都放到一边
        while(true){
            while(a[++i].compareTo(v,makeChoice(),adjacencyList)<0){
                if(i==hi) break;
            }
            while(v.compareTo(a[--j],makeChoice(),adjacencyList)<0){
                if(j==lo) break;
            }
            // 停止条件
            if(i>=j) break;
            TouristAttraction temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        TouristAttraction temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
        // 完成划分
        return j;
    }

    /**
     * 归并排序
     * @param touristAttractions 景点信息
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 排序好的景点名集合
     */
    public ArrayList<TouristAttraction> MergeAndConquerSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        TouristAttraction[] aux = new TouristAttraction[touristAttractions.size()];
        TouristAttraction[] a = new TouristAttraction[touristAttractions.size()];
        // 数组初始化
        for(int i =0;i<touristAttractions.size();i++){
            a[i] = touristAttractions.get(i);
        }
        // 调用递归
        MergeSort(a, aux, 0, a.length - 1, adjacencyList);
        for(int i =0;i<touristAttractions.size();i++){
            touristAttractions.set(i,a[i]);
        }
        return touristAttractions;
    }

    /**
     * 归并排序
     * @param a 待排数组
     * @param aux 暂存数组
     * @param lo 下界
     * @param hi 上界
     * @param adjacencyList 邻接链表，用于计算分支数
     */
    private void MergeSort(TouristAttraction[] a, TouristAttraction[] aux, int lo, int hi, AdjacencyList adjacencyList) {
        // 下界小于上界才能继续
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        // 递归，向下继续merge
        MergeSort(a, aux, lo, mid, adjacencyList);
        MergeSort(a, aux, mid + 1, hi, adjacencyList);
        // 合并数组
        Merge(a, aux, lo, mid, hi, adjacencyList);
    }

    /**
     * 归并排序中合并数组
     * @param a 待排数组
     * @param aux 暂存数组
     * @param lo 下界
     * @param mid 中间位置
     * @param hi 上界
     * @param adjacencyList 邻接链表，用于计算分支数
     */
    private void Merge(TouristAttraction[] a, TouristAttraction[] aux, int lo, int mid, int hi, AdjacencyList adjacencyList) {
        int i = lo;
        int j = mid + 1;
        System.arraycopy(a, lo, aux, lo, hi + 1 - lo);
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i], makeChoice(), adjacencyList) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    /**
     * 插入排序
     * @param touristAttractions 景点信息
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 排序好的景点名集合
     */
    public ArrayList<TouristAttraction> InsertionSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        int N = touristAttractions.size();
        int choice = makeChoice();
        // 把每个元素插入到自己的位置上
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && touristAttractions.get(j).compareTo(touristAttractions.get(j - 1), choice, adjacencyList) < 0; j--) {
                TouristAttraction temp = touristAttractions.get(j);
                touristAttractions.set(j, touristAttractions.get(j - 1));
                touristAttractions.set(j - 1, temp);
            }
        }
        return touristAttractions;
    }

    /**
     * 希尔排序
     * @param touristAttractions 景点信息
     * @param adjacencyList 邻接链表，用于计算分支数
     * @return 排序好的景点名集合
     */
    public ArrayList<TouristAttraction> ShellSort(ArrayList<TouristAttraction> touristAttractions, AdjacencyList adjacencyList) {
        int N = touristAttractions.size();
        int h = 1;
        int next = 0;
        // 隔固定一段距离比较，逐渐缩小增量
        for(h=1;h*3+1<N;){
            h=h*3+1;
        }
        // 分组插入
        for(;h>0;h=(h-1)/3){
            for(int j=0;j<h;j++){
                for(next = h+j;next<N;next+=h){
                    TouristAttraction temp = touristAttractions.get(next);
                    int k = 0;
                    for(k = next;k>=h&&temp.compareTo(touristAttractions.get(k-h),makeChoice(),adjacencyList)<0;k-=h){
                        touristAttractions.set(k,touristAttractions.get(k-h));
                    }
                    touristAttractions.set(k,temp);
                }
            }
        }
        return touristAttractions;
    }

    /**
     * @return 排序方式范围的选择：按景点名或分支数
     */
    private int makeChoice() {
        return choice;
    }
}
