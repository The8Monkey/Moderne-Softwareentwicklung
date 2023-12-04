package de.mines;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

public class Startwindow {
	
	private JFrame frame;
	private JPanel panel;
	private JPanel buttonPanel;
	private JButton btn_erstellen;
	private JPanel textPanel;
	private final String[] fieldsize = {"9x9","16x16","16x30"};
	private JPanel labelPanel;
	private JLabel sizeLabel;
	private JComboBox<String> boardSize;

	public Startwindow(){
		createWindow();
		setzeEreignisse();
	}
	
	private void createWindow(){
		frame = new JFrame();
		frame.setTitle("Mines");
		frame.setContentPane(createContentPanel());
		frame.setSize(250, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private Container createContentPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(createLabelPanel(), BorderLayout.WEST);
		panel.add(createTextPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		return panel;
	}

	private Component createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		buttonPanel.add(getBtn_start(), BorderLayout.EAST);
		return buttonPanel;
	}
	
	private JButton getBtn_start() {
		btn_erstellen = new JButton();
		btn_erstellen.setText("Start Game");
		return btn_erstellen;
	}
	
	private void setzeEreignisse(){
		btn_erstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] size = ((String)boardSize.getSelectedItem()).split("x");
				int size_x = Integer.valueOf(size[0]);
				int size_y = Integer.valueOf(size[1]);
				frame.dispose();
				new MinesUI(size_x,size_y);
			}
		});
	}

	private Component createTextPanel() {
		textPanel = new JPanel();
		textPanel.add(createFieldSize());
		return textPanel;
	}

	private Component createFieldSize() {
		boardSize = new JComboBox<String>(fieldsize);
		boardSize.setSelectedIndex(0);
		return boardSize;
	}

	private Component createLabelPanel() {
		labelPanel = new JPanel();
		sizeLabel = new JLabel();
		sizeLabel.setText("Board Size:");
		labelPanel.add(sizeLabel);
		return labelPanel;
	}
}
