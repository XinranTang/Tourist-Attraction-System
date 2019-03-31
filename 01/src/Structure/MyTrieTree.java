package Structure;

import Structure.TrieNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * 自己实现的中文键树
 */

public class MyTrieTree {
    /**
     * 根节点
     */
    private static TrieNode root = null;
    /**
     * 字典树搜索的结果集合
     */
    ArrayList<String> searchResult = new ArrayList<String>();
    /**
     * 当前能构成的词
     */
    StringBuffer tempWord = new StringBuffer();
    /**
     * 开始位置
     */
    int start = 0;

    /**
     * 构造器，初始化根节点
     */
    public MyTrieTree() {
        root = new TrieNode(null);
    }

    /**
     * @param key 添加一个单词
     */
    public void insert(String key) {
        key = key;
        // 从树根开始向下遍历
        TrieNode p = root;
        String tempWord;
        boolean contains;
        TrieNode tempNode;
        // 一个字一个字找
        for (int i = 0; i < key.length(); i++) {
            tempWord = String.valueOf(key.charAt(i));
            contains = false;
            // 从找到的字的结点向下遍历
            for (TrieNode tn : p.ptr) {
                if (tn.value.equals(tempWord)) {
                    p = tn;
                    contains = true;
                    break;
                }
            }
            // 从当前结点构建新结点
            if (!contains) {
                tempNode = new TrieNode(tempWord);
                p.ptr.add(tempNode);
                p = tempNode;
            }
        }
    }

    /**
     * 模糊匹配
     *
     * @param key 单词
     * @return 符合结果的集合
     */
    public ArrayList<String> search(String key) {
        // 搜索的过程类似于插入的过程
        TrieNode p = root;
        String temp;
        boolean contains = false;
        for (int i = 0; i < key.length(); i++) {
            temp = String.valueOf(key.charAt(i));
            contains = false;
            for (TrieNode tn : p.ptr) {
                if (tn.value.equals(temp)) {
                    p = tn;
                    contains = true;
                    break;
                }
            }
            if (contains) {
                continue;
            } else {
                break;
            }
        }
        if (contains) {
            if (!(p.ptr.isEmpty())) {
                //查找到关键字
                searchResult.clear();
                tempWord.delete(0, tempWord.length());
                tempWord.append(key);
                start = key.length();
                traverseTree(p);
            } else {
                //已经查找到键树的底部
                return null;
            }
        } else {
            //没有查找到相应关键字
            return null;
        }
        return searchResult;
    }

    /**
     * @param p 当前结点
     */
    private void traverseTree(TrieNode p) {
        // 使用了递归的方法去遍历
        if (!(p.ptr.isEmpty())) {
            for (TrieNode tn : p.ptr) {
                tempWord.append(tn.value);
                start++;
                // 递归
                traverseTree(tn);
                start--;
                tempWord.delete(start, tempWord.length());
            }
        } else {
            searchResult.add(tempWord.toString());
        }
    }
}
