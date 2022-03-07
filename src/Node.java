/**
 *
 * @author shinnosuke kawai
 * @version 1.0
 * @since 11/15/2021
 *
 * CS151 assignment 7 Node.java
 */
class Node<T> {
    /**
     * private generic attribute
     */
    private T value;

    /**
     * private node attributes
     */
    private Node<T> right;
    private Node<T> left;

    /**
     * defalut constructor
     */
    public Node() {}

    /**
     * parameterized constructor
     * @param value
     */
    public Node(T value) {
        this.value = value;
        this.left = this.right = null;
    }

    /**
     * parameterized constructor for new node
     * @param value
     * @param right
     * @param left
     */
    public Node(T value, Node<T> right, Node<T> left) {
        this.value = value;
        this.right = right;
        this.left = left;
    }

    /**
     * geeter for value
     * @return
     */
    public T getValue() {
        return value;
    }

    /**
     * setter for value
     * @param value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * getter for right node
     * @return
     */
    public Node<T> getRight() {
        return right;
    }

    /**
     * setter for right node
     * @param right
     */
    public void setRight(Node<T> right) {
        this.right = right;
    }

    /**
     * getter for left node
     * @return
     */
    public Node<T> getLeft() {
        return left;
    }

    /**
     * setter for left node
     * @param left
     */
    public void setLeft(Node<T> left) {
        this.left = left;
    }
}
