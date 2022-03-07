import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
/**
 *
 * @author shinnosuke kawai
 * @version 1.0
 * @since 11/15/2021
 *
 * CS151 assignment 7 CalculatorLogic.java
 */
public class CalculatorLogic {

    /**
     * evaluate expression tree
     * calculate the total
     * @param root
     * @return
     */
    private Object evaluate(Node<String> root)  {
        BigDecimal right = null;
        BigDecimal left = null;
        try {
            if (isLeaf(root)){
                if (isParsable(root.getValue())) {
                    return new BigDecimal(root.getValue());
                } else {
                    return "ERROR: Invalid input -> '"+root.getValue() + "'";
                }
            } else {
                right = new BigDecimal(evaluate(root.getRight()).toString());
                left = new BigDecimal(evaluate(root.getLeft()).toString());
            }
        }
        catch (NumberFormatException numberFormatException) {
            // check the return of the evaluate method is ArithmeticException
            if (evaluate(root.getRight()).toString().contains("/ by zero") || evaluate(root.getLeft()).toString().contains("/ by zero")) {
                return "ERROR: / by zero";
            }
        }
        return compute(right, left, root.getValue());
    }

    /**
     *
     * @param right
     * @param left
     * @param operator
     * @return
     */
    private Object compute(BigDecimal right, BigDecimal left, String operator) {
        BigDecimal bigDecimal;
        String resultString;
        try {
            switch (operator){
                case "+":
                    bigDecimal = right.add(left);
                    resultString = bigDecimal.toString();
                    if (bigDecimal.stripTrailingZeros().scale() >= 0) {
                        resultString = bigDecimal.stripTrailingZeros().toString();
                    }
                    break;
                case "-":
                    bigDecimal = right.subtract(left);
                    resultString = bigDecimal.toString();
                    if (bigDecimal.stripTrailingZeros().scale() >= 0) {
                        resultString = bigDecimal.stripTrailingZeros().toString();
                    }
                    break;
                case "*":
                    bigDecimal = right.multiply(left);
                    resultString = bigDecimal.toString();
                    if (bigDecimal.stripTrailingZeros().scale() >= 0) {
                        resultString = bigDecimal.stripTrailingZeros().toString();
                    }
                    break;
                case "/":
                    bigDecimal = right.divide(left);
                    resultString = bigDecimal.toString();
                    if (bigDecimal.stripTrailingZeros().scale() >= 0) {
                        resultString = bigDecimal.stripTrailingZeros().toString();
                    }
                    break;
                default:
                    return "ERROR: Invalid input -> " + "'" +operator + "'";
            }
        } catch (ArithmeticException | NullPointerException error) {
            try {
                bigDecimal = right.divide(left,7,RoundingMode.HALF_UP);
                resultString = bigDecimal.toString();
            } catch (ArithmeticException exception) {
                return "ERROR: " + exception.getLocalizedMessage();
            }
        }
        return resultString;
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

    /**
     * check if node is leaf
     * @param root
     * @return
     */
    private boolean isLeaf(Node<String> root) {
        return root.getRight() == null && root.getLeft() == null;
    }

    /**
     * Convert number to positive or negative number
     * @param expression
     * @return
     */
    public BigDecimal getPositiveOrNegative(ArrayList<String> expression){
        String lastNum = expression.get(expression.size()-1);

        return new BigDecimal(lastNum).multiply(new BigDecimal("-1"));
    }

    /**
     *
     * @param expression
     * @return
     */
    public Object calculate(ArrayList<String> expression){

        Object result;

        BinaryTreeExpression myTree = new BinaryTreeExpression(expression);

        result = evaluate(myTree.getExpressionTree());

        return result;
    }
}
