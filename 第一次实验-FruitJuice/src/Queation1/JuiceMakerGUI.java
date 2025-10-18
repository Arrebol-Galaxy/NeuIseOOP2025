package Queation1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuiceMakerGUI extends JFrame {
    private JTextField appleWeightField;
    private JTextField pearWeightField;
    private JTextArea resultArea;

    public JuiceMakerGUI(){
        setTitle("非多态果汁设计程序");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Weight please(g)"));

        inputPanel.add(new JLabel("Apple:"));
        appleWeightField = new JTextField("150");
        inputPanel.add(appleWeightField);

        inputPanel.add(new JLabel("Pear:"));
        pearWeightField = new JTextField("200");
        inputPanel.add(pearWeightField);

        JButton calculationButton = new JButton("Juice it UP!");

        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // 布局
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(calculationButton, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        calculationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeJuice();
            }
        });
    }

    private void makeJuice(){
        try {
            double appleWeight = Double.parseDouble(appleWeightField.getText());
            double pearWeight = Double.parseDouble(pearWeightField.getText());

            Apple appleInstance = new Apple(appleWeight);
            Pear pearInstance = new Pear(pearWeight);

            Juice juice = new Juice(appleInstance, pearInstance);

            resultArea.setText(juice.getReport());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(
                    this,
                    "请输入有效的数字重量",
                    "输入错误",
                    JOptionPane.ERROR_MESSAGE
                    );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuiceMakerGUI().setVisible(true);
            }
        });
    }

    public static void main__(String[] args){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        for (String name : fontNames) {
            System.out.println(name);
        }
    }
}
