package Utilities;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    public T data;
    public Node<T> parent;
    public List<Node<T>> children = new ArrayList();
    
    
}
