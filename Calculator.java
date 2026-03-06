import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private StringBuilder expression;

    public Calculator() {
        expression = new StringBuilder();

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);

        // Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C"
        };

        for (String txt : buttons) {
            JButton btn = new JButton(txt);
            btn.setFont(new Font("Arial", Font.BOLD, 22));
            btn.addActionListener(this);
            panel.add(btn);
        }

        setLayout(new BorderLayout(5, 5));
        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setTitle("GUI Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Simple evaluator for + - * /
    private double evaluate(String exp) {
        String[] tokens = exp.replaceAll(" ", "").split("(?=[-+*/])|(?<=[-+*/])");

        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            double num = Double.parseDouble(tokens[i + 1]);

            switch (op) {
                case "+": result += num; break;
                case "-": result -= num; break;
                case "*": result *= num; break;
                case "/":
                    if (num == 0) {
                        JOptionPane.showMessageDialog(this, "Error: Division by zero");
                        return 0;
                    }
                    result /= num;
                    break;
            }
        }

        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("C")) {  // Reset button
            expression.setLength(0);
            display.setText("");
        }
        else if (cmd.equals("=")) {
            try {
                double result = evaluate(expression.toString());
                display.setText(expression + " = " + result);
                expression.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
            }
        }
        else {
            expression.append(cmd);
            display.setText(expression.toString());
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
