package Queation2;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class JuiceFactoryGUI extends JFrame {

    private JComboBox<String> juiceRecipeSelector;
    private JTextField appleWeightField, pearWeightField, orangeWeightField;
    private JLabel appleLabel, pearLabel, orangeLabel;
    private JTextArea resultArea;

    public JuiceFactoryGUI() {
        setTitle("预定制果汁工厂");
        setSize(450, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 下拉菜单选择配方
        String[] recipes = {"请选择配方...", "苹果梨汁/Apple Pear", "全明星果汁/All Star"};
        juiceRecipeSelector = new JComboBox<>(recipes);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入原料重量 (g)"));

        appleLabel = new JLabel("Apple:");
        appleWeightField = new JTextField("150");
        pearLabel = new JLabel("Pear:");
        pearWeightField = new JTextField("200");
        orangeLabel = new JLabel("Orange:");
        orangeWeightField = new JTextField("100");

        inputPanel.add(appleLabel);
        inputPanel.add(appleWeightField);
        inputPanel.add(pearLabel);
        inputPanel.add(pearWeightField);
        inputPanel.add(orangeLabel);
        inputPanel.add(orangeWeightField);

        JButton makeJuiceButton = new JButton("生产果汁");
        resultArea = new JTextArea(10, 35);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        // 布局
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("选择果汁配方:"));
        topPanel.add(juiceRecipeSelector);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(makeJuiceButton, BorderLayout.SOUTH);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.add(mainPanel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // 下拉菜单事件：根据选择的配方，启用/禁用对应的输入框
        juiceRecipeSelector.addActionListener(e -> updateInputFields());

        makeJuiceButton.addActionListener(e -> produceJuice());

        // 初始化输入框状态
        updateInputFields();
    }

    private void updateInputFields() {
        int selectedIndex = juiceRecipeSelector.getSelectedIndex();
        // 默认全部禁用
        appleWeightField.setEnabled(false);
        pearWeightField.setEnabled(false);
        orangeWeightField.setEnabled(false);

        if (selectedIndex == 1) { // 苹果梨汁
            appleWeightField.setEnabled(true);
            pearWeightField.setEnabled(true);
        } else if (selectedIndex == 2) { // 全明星果汁
            appleWeightField.setEnabled(true);
            pearWeightField.setEnabled(true);
            orangeWeightField.setEnabled(true);
        }
    }

    private void produceJuice() {
        int selectedIndex = juiceRecipeSelector.getSelectedIndex();
        if (selectedIndex == 0) {
            JOptionPane.showMessageDialog(this, "Warning: 请先选择一个果汁配方", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Juice finalJuice = null;

        try {
            double appleW = Double.parseDouble(appleWeightField.getText());
            double pearW = Double.parseDouble(pearWeightField.getText());
            double orangeW = Double.parseDouble(orangeWeightField.getText());

            if (selectedIndex == 1) {
                if (appleW < 0 || pearW < 0) {
                    JOptionPane.showMessageDialog(this, "Error: 水果重量不能为负数！", "输入错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                finalJuice = new ApplePearJuice(appleW, pearW);
            } else if (selectedIndex == 2) {
                if (appleW < 0 || pearW < 0 || orangeW < 0) {
                    JOptionPane.showMessageDialog(this, "Error: 水果重量不能为负数！", "输入错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                finalJuice = new AllStarJuice(appleW, pearW, orangeW);
            }

            if (finalJuice != null) {
                resultArea.setText(finalJuice.getReport());
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: 请输入有效的数字重量！", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuiceFactoryGUI().setVisible(true));
    }
}