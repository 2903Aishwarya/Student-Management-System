import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class StudentManagementSystem {
    static String USER_ID = "AishwaryaVerma2004";
    static String PASSWORD = "1234";

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 350);
        loginFrame.setLayout(null);

        JLabel userLabel = new JLabel("User ID:");
        JTextField userField = new JTextField();
        userLabel.setBounds(40, 40, 100, 30);
        userField.setBounds(150, 40, 100, 30);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        passwordLabel.setBounds(40, 80, 100, 30);
        passwordField.setBounds(150, 80, 100, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 120, 100, 30);
        loginButton.addActionListener(e -> {
            String userId = userField.getText();
            String password = new String(passwordField.getPassword());

            if (USER_ID.equals(userId) && PASSWORD.equals(password)) {
                loginFrame.dispose();
                showMainWindow();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    static void showMainWindow() {
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(700, 500);
        frame.setLayout(new GridLayout(3, 1));

        JLabel display = new JLabel("Welcome to Student Management System", SwingConstants.CENTER);
        display.setFont(new Font("Times New Roman", Font.BOLD, 30));
        display.setForeground(Color.BLUE);
        frame.add(display);

        JPanel panel = new JPanel();
        frame.add(panel);

        addButton(panel, "Add a new Student", e -> addStudent());
        addButton(panel, "View all Students", e -> viewAllStudents());
        addButton(panel, "Show details of a Student", e -> viewStudent());
        addButton(panel, "Delete Student details", e -> removeStudent());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(Color.ORANGE);
        button.setForeground(Color.DARK_GRAY);
        button.addActionListener(listener);
        panel.add(button);
    }

    static void addStudent() {
        JFrame frame = new JFrame("Add Student");
        frame.setSize(400, 400);

        JLabel label1 = new JLabel("Enter Student ID:");
        JTextField studentIdField = new JTextField();
        JPanel panel1 = new JPanel(new GridLayout(1, 2));
        panel1.add(label1);
        panel1.add(studentIdField);

        JLabel label2 = new JLabel("Enter Student Name:");
        JTextField studentNameField = new JTextField();
        JPanel panel2 = new JPanel(new GridLayout(1, 2));
        panel2.add(label2);
        panel2.add(studentNameField);

        JLabel label3 = new JLabel("Enter Course:");
        JTextField courseField = new JTextField();
        JPanel panel3 = new JPanel(new GridLayout(1, 2));
        panel3.add(label3);
        panel3.add(courseField);

        JLabel label4 = new JLabel("Enter Year of Enrollment:");
        JTextField yearField = new JTextField();
        JPanel panel4 = new JPanel(new GridLayout(1, 2));
        panel4.add(label4);
        panel4.add(yearField);

        JLabel label5 = new JLabel("Enter Email:");
        JTextField emailField = new JTextField();
        JPanel panel5 = new JPanel(new GridLayout(1, 2));
        panel5.add(label5);
        panel5.add(emailField);

        JLabel label6 = new JLabel("Enter Address:");
        JTextField addressField = new JTextField();
        JPanel panel6 = new JPanel(new GridLayout(1, 2));
        panel6.add(label6);
        panel6.add(addressField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String id = studentIdField.getText();
            String name = studentNameField.getText();
            String course = courseField.getText();
            String year = yearField.getText();
            String email = emailField.getText();
            String address = addressField.getText();

            try (BufferedReader reader = new BufferedReader(new FileReader("Student.xls"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("NewRec.xls"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                writer.write(String.join("\t", id, name, course, year, email, address) + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            deleteFile();
            renameFile();
            JOptionPane.showMessageDialog(null, "Details have been updated!");
            frame.dispose();
        });

        frame.setLayout(new GridLayout(7, 1));
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.add(submitButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    static void deleteFile() {
        File oldFile = new File("Student.xls");
        oldFile.delete();
    }

    static void renameFile() {
        File newFile = new File("NewRec.xls");
        File oldFile = new File("Student.xls");
        newFile.renameTo(oldFile);
    }

    public static void viewAllStudents() {
        JFrame frame = new JFrame("View All Students");
        frame.setSize(500, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader("Student.xls"))) {
            textArea.read(reader, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void viewStudent() 
    {
        JFrame frame = new JFrame("View Student");
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        JTextField studentIdField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        JLabel inputLabel = new JLabel("Enter Student ID:");
        inputPanel.add(inputLabel);
        inputPanel.add(studentIdField);
        frame.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        frame.add(searchButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String studentId = studentIdField.getText();
            try (BufferedReader reader = new BufferedReader(new FileReader("Student.xls"))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] records = line.split("\t");
                    if (records[0].equals(studentId)) {
                        resultArea.setText(String.join("\n", "Student ID: " + records[0],
                                "Student Name: " + records[1],
                                "Course: " + records[2],
                                "Year of Enrollment: " + records[3],
                                "Email: " + records[4],
                                "Address: " + records[5]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    resultArea.setText("Student not found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void removeStudent() {
        JFrame frame = new JFrame("Remove Student");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextField studentIdField = new JTextField();
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        JLabel inputLabel = new JLabel("Enter Student ID:");
        inputPanel.add(inputLabel);
        inputPanel.add(studentIdField);
        frame.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete");
        frame.add(deleteButton, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> {
            String studentId = studentIdField.getText();
            try (BufferedReader reader = new BufferedReader(new FileReader("Student.xls"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("NewRec.xls"))) {
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    if (!line.split("\t")[0].equals(studentId)) {
                        writer.write(line + "\n");
                    } else {
                        found = true;
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(null, "Student record deleted.");
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            deleteFile();
            renameFile();
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}