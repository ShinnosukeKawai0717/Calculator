import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import static java.lang.System.out;

/**
 *
 * @author shinnosuke kawai
 * @version 1.0
 * @since 11/15/2021
 *
 * CS151 assignment 7 BinaryTreeExpression.java
 */

class BinaryTreeExpression {

    /**
     * private arraylist attribute
     */
    private ArrayList<String> postfixExpression;

    /**
     * Default constructor
     */
    public BinaryTreeExpression() {
    }

    /**
     * Parameterised constructor
     */
    public BinaryTreeExpression(ArrayList<String> infixExpression) {
        this.postfixExpression = this.convertToPostfix(infixExpression);
        out.println("Postfix expression: "+this.postfixExpression);
    }

    /**
     * Convert infix expression array into postfix expression
     * return array of string in order of postfix
     *
     * @param infixExpression of string
     * @return postfix expression
     */
    private ArrayList<String> convertToPostfix(ArrayList<String> infixExpression) {

        ArrayList<String> postfixExpression = new ArrayList<>();
        Stack<String> stackOfOperators = new Stack<>();

        for (String s : infixExpression) {
            // check if string is numerical value
            if (isParsable(s)) {
                postfixExpression.add(s);
            } else {
                // As long as priority level of operator in the stack is greater than or
                // equal to an operator in the list of the infix expression,
                // continue adding the top of the stack to the list of postfix expression
                while (!stackOfOperators.isEmpty() && (getPriorityLevel(stackOfOperators.peek()) >= getPriorityLevel(s))) {
                    postfixExpression.add(stackOfOperators.pop());
                }
                stackOfOperators.push(s);
            }
        }
        while (!stackOfOperators.isEmpty()) {
            postfixExpression.add(stackOfOperators.pop());
        }

        return postfixExpression;
    }

    /**
     * construct expression tree from postfix expression.
     *
     * @return node that contains the tree
     */
    public Node<String> getExpressionTree() {

        Stack<Node<String>> nodeStack = new Stack<>();

        for (String element : this.postfixExpression) {
            if (isParsable(element)) {
                nodeStack.push(new Node<>(element));
            } else {
                Node<String> left;
                Node<String> right;
                try {
                    left = nodeStack.pop();
                     right = nodeStack.pop();
                } catch (EmptyStackException e) {
                    left = right = null;
                }
                Node<String> newNode = new Node<>(element, right, left);

                nodeStack.push(newNode);
            }
        }
        return nodeStack.peek();
    }

    /**
     * @return priority level
     */
    private int getPriorityLevel(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
        }
        return -1;
    }

    /**
     * Check if string is parsable
     * @return boolean
     */
    private boolean isParsable(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException | NullPointerException error) {
            return false;
        }
    }
}
