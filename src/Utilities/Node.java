package Utilities;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    public T data;
    public Node<T> parent;
    public List<Node<T>> children = new ArrayList();
    
    public String toString() {
        String details="[";
        if(data!=null)
        {
            details+=data.toString();
            //System.out.println(data.toString());
        }
        else
            details+="";

        if(!children.isEmpty()){
            for (Node<T> treeNode : children) {
            	//System.out.println(treeNode.toString());
                details += treeNode.toString();
            }
        }
        details += "]";
        return details;
    }
}
