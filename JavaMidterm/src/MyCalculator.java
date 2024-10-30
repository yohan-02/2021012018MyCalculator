import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyCalculator extends JFrame {
    private JTextField numField;
    private JButton sum, sub, mult, div, cal, AC;
    private double result;
    private String opt = "";
    private boolean isNewNumber = true;

    MyCalculator() {
        this.setTitle("📱 계산기");
        showNorth();
        showCenter();
        setSize(400, 400);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void showNorth() {
        numField = new JTextField("0");
        numField.setHorizontalAlignment(JTextField.RIGHT);
        Font font = new Font("Arial", Font.PLAIN, 40);
        numField.setFont(font);
        numField.setEditable(false);
        setLayout(new BorderLayout());
        add(numField, BorderLayout.NORTH);
    }

    void showCenter() {
        JPanel btnP = new JPanel(new GridLayout(5, 4, 5, 5));
        sum = createButton("+");
        sub = createButton("-");
        mult = createButton("*");
        div = createButton("/");
        cal = createButton("=");
        AC = createButton("A/C");


        String[] buttons = {
            "A/C", "+/-", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "📱", "0", ".", "="
        };


        for (String label : buttons) {
            if (label.equals("+")) btnP.add(sum);
            else if (label.equals("-")) btnP.add(sub);
            else if (label.equals("*")) btnP.add(mult);
            else if (label.equals("/")) btnP.add(div);
            else if (label.equals("=")) btnP.add(cal);
            else if (label.equals("A/C")) btnP.add(AC);
            else {
                JButton btn = createButton(label);
                if (label.matches("[0-9.]")) {
                    btn.addActionListener(e -> handleNumber(label));
                } else if (label.equals("+/-")) {
                    btn.addActionListener(e -> handlePlusMinus());
                } else if (label.equals("%")) {
                    btn.addActionListener(e -> handlePercent());
                }
                btnP.add(btn);
            }
        }

        add(btnP, BorderLayout.CENTER);


        sum.addActionListener(e -> handleOperator("+"));
        sub.addActionListener(e -> handleOperator("-"));
        mult.addActionListener(e -> handleOperator("*"));
        div.addActionListener(e -> handleOperator("/"));
        cal.addActionListener(e -> calculateResult());
        AC.addActionListener(e -> clearCalculator());
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        return button;
    }

    private void handleNumber(String num) {
        if (isNewNumber) {
            numField.setText(num);
            isNewNumber = false;
        } else {
            if (num.equals(".") && numField.getText().contains(".")) {
                return;
            }
            numField.setText(numField.getText() + num);
        }
    }

    private void handleOperator(String operator) {
        if (!isNewNumber) {
            calculateResult();
        }
        opt = operator;
        result = getCurrentNumber();
        isNewNumber = true;
    }

    private void handlePlusMinus() {
        double current = getCurrentNumber();
        numField.setText(String.valueOf(-current));
    }

    private void handlePercent() {
        double current = getCurrentNumber();
        numField.setText(String.valueOf(current / 100));
    }

    private void calculateResult() {
        if (opt.isEmpty()) return;

        double currentNum = getCurrentNumber();
        switch (opt) {
            case "+":
                result += currentNum;
                break;
            case "-":
                result -= currentNum;
                break;
            case "*":
                result *= currentNum;
                break;
            case "/":
                if (currentNum != 0) {
                    result /= currentNum;
                } else {
                    numField.setText("Error");
                    return;
                }
                break;
        }
        

        if (result == (long) result) {
            numField.setText(String.valueOf((long) result));
        } else {
            numField.setText(String.valueOf(result));
        }
        opt = "";
        isNewNumber = true;
    }

    private void clearCalculator() {
        numField.setText("0");
        result = 0;
        opt = "";
        isNewNumber = true;
    }

    private double getCurrentNumber() {
        try {
            return Double.parseDouble(numField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        new MyCalculator();
    }
}