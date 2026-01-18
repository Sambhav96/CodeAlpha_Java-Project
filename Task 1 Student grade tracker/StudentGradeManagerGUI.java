import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    int marks;
    String grade;

    Student(String name, int marks, String grade) {
        this.name = name;
        this.marks = marks;
        this.grade = grade;
    }
}

public class StudentGradeManagerGUI extends JFrame {

    private JTextField nameField, marksField;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Student> students = new ArrayList<>();

    private JLabel avgLabel, highLabel, lowLabel;

    public StudentGradeManagerGUI() {

        setTitle("Student Grade Manager");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        inputPanel.add(marksField);

        JButton addButton = new JButton("Add Student");
        JButton summaryButton = new JButton("Show Summary");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(summaryButton);

        tableModel = new DefaultTableModel(new String[]{"Name", "Marks", "Grade"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));

        avgLabel = new JLabel("Average: ");
        highLabel = new JLabel("Highest: ");
        lowLabel = new JLabel("Lowest: ");

        summaryPanel.add(avgLabel);
        summaryPanel.add(highLabel);
        summaryPanel.add(lowLabel);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(summaryPanel, BorderLayout.EAST);

        addButton.addActionListener(e -> addStudent());
        summaryButton.addActionListener(e -> showSummary());
    }

    private void addStudent() {
        String name = nameField.getText();
        String marksText = marksField.getText();

        if (name.isEmpty() || marksText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields!");
            return;
        }

        try {
            int marks = Integer.parseInt(marksText);

            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100!");
                return;
            }

            String grade;
            if (marks >= 90)
                grade = "A";
            else if (marks >= 75)
                grade = "B";
            else if (marks >= 60)
                grade = "C";
            else
                grade = "Fail";

            students.add(new Student(name, marks, grade));
            tableModel.addRow(new Object[]{name, marks, grade});

            nameField.setText("");
            marksField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Marks must be numeric!");
        }
    }

    private void showSummary() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students added!");
            return;
        }

        int total = 0;
        int highest = students.get(0).marks;
        int lowest = students.get(0).marks;

        for (Student s : students) {
            total += s.marks;
            if (s.marks > highest) highest = s.marks;
            if (s.marks < lowest) lowest = s.marks;
        }

        double average = (double) total / students.size();

        avgLabel.setText("Average: " + average);
        highLabel.setText("Highest: " + highest);
        lowLabel.setText("Lowest: " + lowest);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeManagerGUI().setVisible(true);
        });
    }
}
