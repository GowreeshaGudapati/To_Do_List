package com.list;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

 public class TaskRenderer extends JCheckBox implements ListCellRenderer<Task> {
    private static final Icon GREEN_TICK_ICON = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(new Color(0, 150, 0)); // green
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(x + 3, y + getIconHeight() / 2, x + getIconWidth() / 2, y + getIconHeight() - 3);
            g2.drawLine(x + getIconWidth() / 2, y + getIconHeight() - 3, x + getIconWidth() - 3, y + 3);
        }
        @Override
        public int getIconWidth() { return 16; }
        @Override
        public int getIconHeight() { return 16; }
    };
    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(value.getText());
        setSelected(value.isCompleted());
        setIcon(value.isCompleted() ? GREEN_TICK_ICON : UIManager.getIcon("CheckBox.icon"));

        setForeground(value.isCompleted() ? new Color(0, 128, 0) : Color.BLACK);
        setFont(value.isCompleted() ? getFont().deriveFont(Font.BOLD) : getFont().deriveFont(Font.PLAIN));
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        return this;
    }
}

