import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.*;
import static java.lang.System.*;

/**
 *
 * @author shinnosuke kawai
 * @version 1.0
 * @since 11/15/2021
 *
 * CS151 assignment 7 Calculator.java
 */

enum Constants {
    PLUS("/OperatorImages/plus.png"),
    MINUS("/OperatorImages/minus.png"),
    MULTIPLE("/OperatorImages/multiple.png"),
    DIVIDE("/OperatorImages/divide.png"),
    EQUAL("/OperatorImages/equal.png"),
    CLEAR("/OperatorImages/AC.png"),
    NEGATIVE("/OperatorImages/negative.png"),
    DECIMAL("/OperatorImages/decimal.png"),
    LASTANSWER("/OperatorImages/reply.png"),
    NUMBERS(new ImageIcon[10]);

    /**
     * private array of imageIcon attribute
     */
    private ImageIcon[] number;

    /**
     * private imageIcon attribute
     */
    private ImageIcon operator;

    /**
     * Constructor for number images
     * @param num
     */
    Constants(ImageIcon[] num) {
        this.number = num;
        for (int i = 0; i <= 9; i++) {
            Image number = new ImageIcon(Objects.requireNonNull(Calculator.class.getResource("/NumberImages/No" + i + ".png"))).getImage();
            this.number[i] = new ImageIcon(number);
        }
    }

    /**
     * Constructor for operator
     * @param path
     */
    Constants(String path) {
        Image image = new ImageIcon(Objects.requireNonNull(Calculator.class.getResource(path))).getImage();
        this.operator = new ImageIcon(image);
    }

    /**
     * getter for images of operators
     * @return
     */
    public ImageIcon getOperatorImage() {
        return operator;
    }

    /**
     * getter for images of numbers
     * @param index
     * @return
     */
    public ImageIcon getNumberImage(int index) {
        return number[index];
    }
}

public class Calculator extends JFrame{

    /**
     * private boolean attribute
     */
    private boolean isDecimalPressed = false;
    /**
     * private CalculatorLogic attribute
     */
    private CalculatorLogic calculator;
    /**
     * private final Timer attribute
     */
    private final Timer myTimer = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultLabel.setText("");
        }
    });

    /**
     * private string attribute
     */
    private String lastAns;

    /**
     * private JPanel attributes
     */
    private JPanel buttonsPanel, operatorPanel, resultAndInputPanel;

    /**
     * private final array of JButton attribute
     */
    private JButton[] numButtons;

    /**
     * private last answer button attribute
     */
    private JButton lastAnsButton;

    /**
     * private final hashMap of JButton attribute
     */
    private final HashMap<Constants, JButton> mapOfOperators = new HashMap<>(
            Map.of(
                    Constants.PLUS, new JButton(),
                    Constants.MINUS, new JButton(),
                    Constants.MULTIPLE, new JButton(),
                    Constants.DIVIDE, new JButton(),
                    Constants.CLEAR, new JButton(),
                    Constants.EQUAL, new JButton(),
                    Constants.DECIMAL, new JButton(),
                    Constants.NEGATIVE, new JButton()
            )
    );

    /**
     * private final JTextField attribute
     */
    private JTextField inputField;

    /**
     * private final JLabel attribute
     */
    private JLabel resultLabel;

    /**
     * Default Constructor
     */
    public Calculator() {
        setResultLabel();
        setInputField();
        myTimer.setRepeats(false);
        setBounds(100,100,400, 650);
        setResizable(false);
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initializeNumButtons();
        setImages();
        setNumButtonPanel();
        setOperatorPanel();
        setResultAndInputPanel();
        addActToButtons();

        add(resultAndInputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(operatorPanel, BorderLayout.EAST);
        setVisible(true);
    }

    /**
     * initialize array of buttons
     */
    private void initializeNumButtons() {
        this.numButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numButtons[i] = new JButton();
        }
        lastAnsButton = new JButton();
    }

    /**
     * setter for input field
     */
    private void setInputField() {
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension( 400, 50 ));
        inputField.setFont(new Font("SF Pro Text", Font.PLAIN, 20));
    }

    /**
     * Setter for result lable
     */
    private void setResultLabel() {
        resultLabel = new JLabel();
        resultLabel.setPreferredSize(new Dimension(380,50));
        resultLabel.setFont(new Font("SF Pro Text", Font.PLAIN, 20));
    }

    /**
     * Set the images to all the buttons
     */
    private void setImages() {
        ImageIcon[] numImages = new ImageIcon[10];
        for (int i = 0; i <=9; i++){
            numImages[i] = Constants.NUMBERS.getNumberImage(i);
            numButtons[i].setIcon(numImages[i]);
        }

        mapOfOperators.get(Constants.PLUS).setIcon(Constants.PLUS.getOperatorImage());
        mapOfOperators.get(Constants.MINUS).setIcon(Constants.MINUS.getOperatorImage());
        mapOfOperators.get(Constants.MULTIPLE).setIcon(Constants.MULTIPLE.getOperatorImage());
        mapOfOperators.get(Constants.DIVIDE).setIcon(Constants.DIVIDE.getOperatorImage());
        mapOfOperators.get(Constants.EQUAL).setIcon(Constants.EQUAL.getOperatorImage());
        mapOfOperators.get(Constants.CLEAR).setIcon(Constants.CLEAR.getOperatorImage());
        mapOfOperators.get(Constants.NEGATIVE).setIcon(Constants.NEGATIVE.getOperatorImage());
        mapOfOperators.get(Constants.DECIMAL).setIcon(Constants.DECIMAL.getOperatorImage());
        lastAnsButton.setIcon(Constants.LASTANSWER.getOperatorImage());
    }

    /**
     * Add action listener to all the buttons
     */
    private void addActToButtons() {
        // adding action listener to operator buttons
        mapOfOperators.get(Constants.EQUAL).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.DIVIDE).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.MULTIPLE).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.MINUS).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.PLUS).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.CLEAR).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.DECIMAL).addActionListener(new CustomActionListener()::operatorPressed);
        mapOfOperators.get(Constants.NEGATIVE).addActionListener(new CustomActionListener()::operatorPressed);
        lastAnsButton.addActionListener(new CustomActionListener()::operatorPressed);

        //adding action listener to number buttons
        for (JButton numButton : numButtons) {
            numButton.addActionListener(new CustomActionListener()::numbersPressed);
        }
    }

    /**
     * Set the panel for operator buttons.
     */
    private void setOperatorPanel(){
        operatorPanel = new JPanel();
        operatorPanel.setBackground(Color.white);
        operatorPanel.setLayout(new GridLayout(7,1,2,2));
        operatorPanel.add(lastAnsButton);
        operatorPanel.add(mapOfOperators.get(Constants.CLEAR));
        operatorPanel.add(mapOfOperators.get(Constants.DIVIDE));
        operatorPanel.add(mapOfOperators.get(Constants.MULTIPLE));
        operatorPanel.add(mapOfOperators.get(Constants.MINUS));
        operatorPanel.add(mapOfOperators.get(Constants.PLUS));
        operatorPanel.add(mapOfOperators.get(Constants.EQUAL));
    }

    /**
     * Set the panel for number buttons.
     */
    private void setNumButtonPanel(){
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new GridLayout(4,3,2,2));
        buttonsPanel.add(numButtons[7]); buttonsPanel.add(numButtons[8]); buttonsPanel.add(numButtons[9]);
        buttonsPanel.add(numButtons[4]); buttonsPanel.add(numButtons[5]); buttonsPanel.add(numButtons[6]);
        buttonsPanel.add(numButtons[1]); buttonsPanel.add(numButtons[2]); buttonsPanel.add(numButtons[3]);
        buttonsPanel.add(numButtons[0]);
        buttonsPanel.add(mapOfOperators.get(Constants.DECIMAL)); buttonsPanel.add(mapOfOperators.get(Constants.NEGATIVE));
    }

    /**
     * Set the panel for input feild and result label.
     */
    private void setResultAndInputPanel(){
        resultAndInputPanel = new JPanel();
        resultAndInputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        resultAndInputPanel.setBackground(Color.white);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        resultAndInputPanel.add(inputField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        resultAndInputPanel.add(resultLabel, gridBagConstraints);
    }

    /**
     * private custom listener class
     */
    private class CustomActionListener{

        /**
         * Method that is triggered when a number is pressed.
         * @param e -> action event object
         */
        public void numbersPressed(ActionEvent e){
            Object pressedNum = e.getSource();

            if (pressedNum.equals(numButtons[0])){
                inputField.setText(inputField.getText() + "0");
            }
            if (pressedNum.equals(numButtons[1])){
                inputField.setText(inputField.getText() + "1");
            }
            if (pressedNum.equals(numButtons[2])){
                inputField.setText(inputField.getText() + "2");
            }
            if (pressedNum.equals(numButtons[3])){
                inputField.setText(inputField.getText() + "3");
            }
            if (pressedNum.equals(numButtons[4])){
                inputField.setText(inputField.getText() + "4");
            }
            if (pressedNum.equals(numButtons[5])){
                inputField.setText(inputField.getText() + "5");
            }
            if (pressedNum.equals(numButtons[6])){
                inputField.setText(inputField.getText() + "6");
            }
            if (pressedNum.equals(numButtons[7])){
                inputField.setText(inputField.getText() + "7");
            }
            if (pressedNum.equals(numButtons[8])){
                inputField.setText(inputField.getText() + "8");
            }
            if (pressedNum.equals(numButtons[9])){
                inputField.setText(inputField.getText() + "9");
            }
        }

        /**
         * Method that is triggered when an operator is pressed.
         * @param e -> action event object
         */
        private void operatorPressed(ActionEvent e){
            Object pressedOpe = e.getSource();
            if (pressedOpe.equals(lastAnsButton)) {
                inputField.setText(inputField.getText() + Objects.requireNonNullElse(lastAns, ""));
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.PLUS))) {
                if (inputField.getText().isEmpty() && lastAns == null){
                    resultLabel.setText("Error: cannot start with +");
                    myTimer.start();
                }
                else if (inputField.getText().isEmpty() && !lastAns.isEmpty()) {
                    inputField.setText(lastAns + "+");
                }
                else {
                    if (isOperator(inputField.getText().charAt(inputField.getText().length()-1))){
                        resultLabel.setText("Error: 2 operator in a row");
                        myTimer.start();
                    }
                    else {
                        inputField.setText(inputField.getText() + "+");
                        isDecimalPressed = false;
                    }
                }
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.MINUS))) {
                if (inputField.getText().isEmpty() && lastAns == null){
                    resultLabel.setText("Error: cannot start with -");
                    myTimer.start();
                }
                else if (inputField.getText().isEmpty() && !lastAns.isEmpty()) {
                    inputField.setText(lastAns + "-");
                }
                else {
                    if (isOperator(inputField.getText().charAt(inputField.getText().length()-1))){
                        resultLabel.setText("Error: 2 operator in a row");
                        myTimer.start();
                    }
                    else {
                        inputField.setText(inputField.getText() + "-");
                        isDecimalPressed = false;
                    }
                }
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.MULTIPLE))) {
                if (inputField.getText().isEmpty() && lastAns == null){
                    resultLabel.setText("Error: cannot start with *");
                    myTimer.start();
                }
                else if (inputField.getText().isEmpty() && !lastAns.isEmpty()) {
                    inputField.setText(lastAns + "*");
                }
                else {
                    if (isOperator(inputField.getText().charAt(inputField.getText().length()-1))){
                        resultLabel.setText("Error: 2 operator in a row");
                        myTimer.start();
                    }
                    else {
                        inputField.setText(inputField.getText() + "*");
                        isDecimalPressed = false;
                    }
                }
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.DIVIDE))) {
                if (inputField.getText().isEmpty() && lastAns == null){
                    resultLabel.setText("Error: cannot start with /");
                    myTimer.start();
                }
                else if (inputField.getText().isEmpty() && !lastAns.isEmpty()) {
                    inputField.setText(lastAns + "/");
                }
                else {
                    if (isOperator(inputField.getText().charAt(inputField.getText().length()-1))){
                        resultLabel.setText("Error: 2 operator in a row");
                        myTimer.start();
                    }
                    else {
                        inputField.setText(inputField.getText() + "/");
                        isDecimalPressed = false;
                    }
                }
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.NEGATIVE))){
                if (inputField.getText().isEmpty()){
                    inputField.setText("-");
                }
                else {
                    char lastCharacter;
                    try {
                        lastCharacter = inputField.getText().charAt(inputField.getText().length()-1);
                    } catch (IndexOutOfBoundsException error) {
                        lastCharacter = 0;
                    }
                    if (isOperator(lastCharacter)){
                        inputField.setText(inputField.getText() + "-");
                    }
                    else {
                        calculator = new CalculatorLogic();
                        ArrayList<String> expression = this.splitExpression(inputField.getText());

                        BigDecimal newLastNum = calculator.getPositiveOrNegative(expression);

                        expression.set(expression.size()-1, String.valueOf(newLastNum));

                        StringBuilder newLine = new StringBuilder();
                        for (String line : expression){
                            newLine.append(line);
                        }
                        inputField.setText(newLine.toString());
                    }
                }

            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.DECIMAL))){
                if (!isDecimalPressed) {
                    inputField.setText(inputField.getText()+".");
                    isDecimalPressed = true;
                }
                else {
                    resultLabel.setText("Error: Multiple decimal points");
                    myTimer.start();
                }
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.EQUAL))) {
                if (inputField.getText().isEmpty()) {
                    resultLabel.setText("");
                }else {
                    calculator = new CalculatorLogic();
                    ArrayList<String> expression = this.splitExpression(inputField.getText());
                    out.println("Infix expression: "+expression);

//              call solver by passing array list
                    Object result = this.getFinalResultWith(expression);

                    if (isNumber(result.toString())) {
                        lastAns = result.toString();
                    }

//              update UI
                    resultLabel.setText(result.toString());
                    inputField.setText("");
                    isDecimalPressed = false;
                }
            }
            if (pressedOpe.equals(mapOfOperators.get(Constants.CLEAR))){
                isDecimalPressed = false;
                inputField.setText("");
                resultLabel.setText("");
            }
        }

        /**
         * Split math expression based on operators, numbers, and negative sign
         * @param value
         * @return arraylist of string
         */
        private ArrayList<String> splitExpression(String value) {
            String[] list = value.split("(?<=[\\d.])(?=[^\\d.])|(?<=[^\\d.])(?=[\\d.])");
            ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(list));

            for (int i = 0; i < stringArrayList.size(); i++){
                if (stringArrayList.get(i).equals("+-") || stringArrayList.get(i).equals("*-") || stringArrayList.get(i).equals("/-") || stringArrayList.get(i).equals("--")){
                    char[] charArray = stringArrayList.get(i).toCharArray();

                    char operator = charArray[0];
                    char minus = charArray[1];

                    stringArrayList.set(i, String.valueOf(operator));
                    stringArrayList.set(i+1, minus+stringArrayList.get(i+1));
                }
                if (stringArrayList.get(0).equals("-")) {
                    stringArrayList.remove(0);
                    String num = stringArrayList.get(0);
                    stringArrayList.set(0, "-"+num);
                }
            }
            return stringArrayList;
        }

        /**
         * Get final result by calling calulate method
         * @param expression
         * @return
         */
        private Object getFinalResultWith(ArrayList<String> expression){
            Object result;
            result = calculator.calculate(expression);
            return result;
        }

        /**
         * Check if char is an operator
         * @param ope
         * @return
         */
        private boolean isOperator(char ope){
            return ope == '+' || ope == '-'|| ope == '*'||ope == '/';
        }

        /**
         * Check if string is numeric value
         * @param input
         * @return
         */
        private boolean isNumber(String input) {
            boolean isInt, isDecimal;
            try {
                Integer.parseInt(input);
                isInt = true;
            } catch (NumberFormatException error) {
                isInt = false;
            }
            try {
                Double.parseDouble(input);
                isDecimal = true;
            } catch (NumberFormatException error) {
                isDecimal = false;
            }
            return isInt || isDecimal;
        }
    }
}
