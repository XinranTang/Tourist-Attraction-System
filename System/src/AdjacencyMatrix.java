import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdjacencyMatrix implements Graph {
    private HashMap<String, HashMap<String, Integer>> graphHashMap;

    public AdjacencyMatrix() {
        graphHashMap = new HashMap<>();

    }

    @Override
    public void createGraph(Graph graph) {
        AdjacencyList adjacencyList = (AdjacencyList)graph;
        Iterator<Map.Entry<String, Node>> iterator = adjacencyList.getIterator();
        Map.Entry<String, Node> current;
        HashMap<String, Integer> rowHashMap;
        // 构造邻接矩阵的每一行，每一列暂时是一个空的Hash Map
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
    public Iterator<Map.Entry<String,HashMap<String,Integer>>> getIterator(){
        return graphHashMap.entrySet().iterator();
    }
    @Override
    public String toString() {
        // 把矩阵的表头打出来
        StringBuilder title = new StringBuilder();
        title.append("\t");
        // 景点名的集合
        Set<String> touristAttractionSet = graphHashMap.keySet();
        for(String each:touristAttractionSet){
            title.append(each).append("\t");
        }
        title.append("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for(String each:touristAttractionSet){
            // 景点名
            stringBuilder.append(each).append("\t");
            HashMap<String,Integer> innerGraph = graphHashMap.get(each);
            for(String innerEach:touristAttractionSet){
                if(innerGraph.get(innerEach)==null){
                    stringBuilder.append("∞").append("\t");
                }else{
                    stringBuilder.append(innerGraph.get(innerEach)).append("\t");
                }

            }
            stringBuilder.append("\n");
        }


//        Iterator<Map.Entry<String,HashMap<String,Integer>>> iterator = this.getIterator();
//        Map.Entry<String,HashMap<String,Integer>> current;
//        while(iterator.hasNext()){
//            current=iterator.next();
//            title.append(current.getKey()).append("\t");
//            stringBuilder.append(current.getKey()).append("\t");
//
//        }
        return (title.append(stringBuilder)).toString();
    }
}
