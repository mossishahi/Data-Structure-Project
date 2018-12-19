package utility;

import java.util.*;

/**
 * Created by mostafa on 1/15/2017.
 */

public class Trie {
    public Node root;
    public Trie() {
        root = new Node();
    }

    public void insert(String word, Object object) {
        ArrayList<Data> treeData = root.data;
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            Node t;
            if (searchNode(treeData, character)) {
                t = getNode(treeData, character);
            } else {
                t = new Node(character);
                Data newData = new Data(t, character);
                treeData.add(newData);
            }
            treeData = t.data;
            if (i == word.length() - 1) {
                t.isLeaf = true;
                t.setObject(object);
            }
        }
        root.data.sort(new Data(null, '\0'));
    }

    // Returns if the word is in the trie.
    public Object search(String word) {
        Node t = searchNode(word);

        if (t != null && t.isLeaf)
            return t.getObject();
        else
            return null;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if (searchNode(prefix) == null)
            return false;
        else
            return true;
    }

    public Node searchNode(String str) {
        ArrayList<Data> treeData = new ArrayList<>();
        treeData = root.data;
//        Map<Character, Node> children = root.children;
        Node t = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (searchNode(treeData, c)) {
                t = getNode(treeData, c);
                treeData = t.data;
            } else {
                return null;
            }
        }
        return t;
    }

    public boolean searchNode(ArrayList<Data> data, char c) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).character == c)
                return true;
        }
        return false;
    }

    public Node getNode(ArrayList<Data> data, char c) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).character == c)
                return data.get(i).Node;
        }
        return null;
    }

    public Object removeItem(String objectName) {
        return (root.removeObject(objectName));
    }

    public void getItems(ArrayList<Object> items) {
        root.getItems(items);
    }

    public boolean isEmpty() {
        return root.isEmpty();
    }

}
