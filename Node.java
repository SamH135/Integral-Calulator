public class Node<G> {

    

    private G data;     // generic data that node contains

    private Node<G> left;   // children of node
    private Node<G> right;

    // constructor
    Node(G data)
    {
        this.data = data;
        left = null;
        right = null;
    }

    // getters/setters
    
    public G getData() {return data;}
    public void setData(G data) {this.data = data;}

    public Node<G> getLeft() {return left;}
    public void setLeft(Node<G> left) {this.left = left;}
    
    public Node<G> getRight() {return right;}
    public void setRight(Node<G> right) {this.right = right;}

}

