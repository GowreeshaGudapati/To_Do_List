package com.list;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


public class TodoList {
    private JFrame frame;
    private JTextField taskField;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private final String FILE_NAME = "tasks.txt"; // File to store tasks

    public TodoList() {
        frame = new JFrame("To-Do List ✅");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel(new BorderLayout());
        taskField = new JTextField();
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> addTask());

        topPanel.add(taskField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new TaskRenderer());

        
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Task task = taskListModel.get(index);
                    task.setCompleted(!task.isCompleted());
                    taskList.repaint();
                    saveTasks();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskList);

        
        JPanel bottomPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(e -> deleteTask());

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.addActionListener(e -> clearAllTasks());

        bottomPanel.add(deleteButton);
        bottomPanel.add(clearAllButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        loadTasks();
        frame.setVisible(true);
    }

    private void addTask() {
        String text = taskField.getText().trim();
        if (!text.isEmpty()) {
            taskListModel.addElement(new Task(text, false));
            taskField.setText("");
            saveTasks();
        }
    }

    private void deleteTask() {
        int index = taskList.getSelectedIndex();
        if (index != -1) {
            taskListModel.remove(index);
            saveTasks();
        }
    }

    private void clearAllTasks() {
        taskListModel.clear();
        saveTasks();
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                Task t = taskListModel.get(i);
                writer.write(t.getText() + ";;" + t.isCompleted());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving tasks!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";;");
                    if (parts.length == 2) {
                        taskListModel.addElement(new Task(parts[0], Boolean.parseBoolean(parts[1])));
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error loading tasks!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoList::new);
    }
}

