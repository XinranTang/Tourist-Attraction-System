package Structure;

import java.util.ArrayList;

/**
 * 字典树的树结点类，用于景点排序构建前缀字典树
 */
public class TrieNode {
    /**
     * 结点的值
     */
    public String value;
    /**
     * 子结点
     */
    public ArrayList<TrieNode> ptr = null;

    /**
     * @param value 结点的值
     */
    public TrieNode(String value) {
        this.value=value;
        ptr =new ArrayList<TrieNode>();
    }
}
