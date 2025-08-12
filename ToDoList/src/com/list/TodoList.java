package com.list;

import java.awt.BorderLayout;
import javax.swing.*;

public class TodoList {
	private JFrame frame;
	private JTextField taskField;
	private DefaultListModel<String> taskListModel;
	private JList<String> taskList;

	public TodoList() {

		frame = new JFrame("To-Do List");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLayout(new BorderLayout());

		// Top panel with text field + add button
		JPanel topPanel = new JPanel(new BorderLayout());
		taskField = new JTextField();
		JButton addButton = new JButton("Add Task");
		addButton.addActionListener(e -> addTask());
		topPanel.add(taskField, BorderLayout.CENTER);
		topPanel.add(addButton, BorderLayout.EAST);

		// Task list
		taskListModel = new DefaultListModel<>();
		taskList = new JList<>(taskListModel);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ✅ important
		JScrollPane scrollPane = new JScrollPane(taskList);

		JPanel bottomPanel = new JPanel();
		JButton deleteButton = new JButton("Delete Task");
		deleteButton.addActionListener(e -> deleteTask());
		bottomPanel.add(deleteButton);

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private void addTask() {
		String task = taskField.getText().trim();
		if (!task.isEmpty()) {
			taskListModel.addElement(task);
			taskField.setText("");
		}
	}

	private void deleteTask() {
		int selectedIndex = taskList.getSelectedIndex();
		if (selectedIndex != -1) {
			taskListModel.remove(selectedIndex);
		} else {
			JOptionPane.showMessageDialog(frame, "Please select a task to delete.", "No Task Selected",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(TodoList::new);
	}
}
