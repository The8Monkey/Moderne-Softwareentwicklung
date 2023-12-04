package de.mines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MinesUI {
	static JPanel buttonPanel;
	static JPanel mainPanel;
	static JFrame mainJFrame;
	private static int[][] field;
	private static JButton[][] buttonField;
	private static ArrayList<String> buttonPos;
	private static int size_x;
	private static int size_y;
	private static int windowSize_x;
	private static int windowSize_y;
	private static MouseAdapter mouseAdapter;
	private static JLabel score;
	private static int scoreNumber;
	private static JButton btnNewGame;
	private static int maxscore;
	private final static String victoryString = "YOU WON!!!";
	private final static String looseString = "YOU LOST!!!";
	private final static String titleString = "Mines";
	private final static String scoreString = "Score: ";
	private final static String newGameString = "New Game";
	private final static int maxscore_small = 71;
	private final static int maxscore_medium = 216;
	private final static int maxscore_large = 381;
	private final static int fieldsize_small = 9;
	private final static int fieldsize_medium = 16;
	private final static int mine = -1;

	public MinesUI(int size_x, int size_y) {
		MinesUI.size_x = size_x;
		MinesUI.size_y = size_y;
		maxscore = getMaxscore(size_y);
		setWindowsize(maxscore);
		field = new Mines(size_x, size_y).getField();
		buttonField = new JButton[size_x][size_y];
		buttonPos = new ArrayList<String>();
		scoreNumber = 0;
		prepareWindow();
	}

	private static void setWindowsize(int maxscore) {
		switch (maxscore) {
			case maxscore_small:
				windowSize_x = 500;
				windowSize_y = 500;
				break;
			case maxscore_medium:
				windowSize_x = 800;
				windowSize_y = 800;
				break;
			case maxscore_large:
				windowSize_x = 1400;
				windowSize_y = 800;
				break;
		}
	}

	private static int getMaxscore(int size_y) {
		if (size_y == fieldsize_small) {
			return maxscore_small;
		} else if (size_y == fieldsize_medium) {
			return maxscore_medium;
		} else {
			return maxscore_large;
		}
	}

	private static void prepareWindow() {
		createMouseAdapter();
		createWindow();
		getButtonPos();
	}

	private static void createMouseAdapter() {
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					leftClick(e);
				}
				if (e.getButton() == MouseEvent.BUTTON1) {
					rightClick(e);
				}
				if (scoreNumber == maxscore) {
					JOptionPane.showMessageDialog(null, victoryString);
				}
			}

			private void leftClick(MouseEvent e) {
				if (e.getComponent().getBackground() == Color.BLUE) {
					e.getComponent().setBackground(Color.LIGHT_GRAY);
				} else {
					e.getComponent().setBackground(Color.BLUE);
				}
			}

			private void rightClick(MouseEvent e) {
				if (e.getComponent().getBackground() != Color.BLUE) {
					for (int field_x = 0; field_x < size_x; field_x++) {
						for (int field_y = 0; field_y < size_y; field_y++) {
							if (buttonField[field_x][field_y] == e.getSource()) {
								if (field[field_x][field_y] == mine) {
									e.getComponent().setBackground(Color.RED);
									((JButton) e.getComponent()).setText("" + field[field_x][field_y]);
									removeAllListener();
									JOptionPane.showMessageDialog(null, looseString);
								} else {
									((JButton) e.getComponent()).setText("" + field[field_x][field_y]);
									e.getComponent().setBackground(Color.GRAY);
									e.getComponent().removeMouseListener(this);
									scoreNumber++;
									score.setText(scoreString + scoreNumber);
									if (field[field_x][field_y] == 0) {
										revealNextButton(field_x, field_y);
									}
								}
							}
						}
					}
				}
			}

			/* Recursiv methode to reveal all Buttons according to the rules of mines */
			private void revealNextButton(int field_x, int field_y) {
				if (field[field_x][field_y] == 0) {
					int current_x;
					int xmax;
					if (field_x == 0) {
						current_x = 0;
					} else {
						current_x = field_x - 1;
					}
					if (field_x == MinesUI.size_x - 1) {
						xmax = field_x;
					} else {
						xmax = field_x + 1;
					}
					for (; current_x <= xmax; current_x++) {
						int current_y;
						int ymax = 0;
						if (field_y == 0) {
							current_y = 0;
						} else {
							current_y = field_y - 1;
						}
						if (field_y == MinesUI.size_y - 1) {
							ymax = field_y;
						} else {
							ymax = field_y + 1;
						}
						for (; current_y <= ymax; current_y++) {
							String buttonPosition = buttonPos.get(((current_x * MinesUI.size_y) + current_y));
							String[] buttonPositionArray = buttonPosition.split(";");
							int buttonPosition_x = Integer.valueOf(buttonPositionArray[0]);
							int buttonPosition_y = Integer.valueOf(buttonPositionArray[1]);
							if (buttonPanel.getComponentAt(buttonPosition_x, buttonPosition_y).getBackground() != (Color.GRAY)) {
								buttonPanel.getComponentAt(buttonPosition_x, buttonPosition_y).setBackground(Color.GRAY);
								((JButton) buttonPanel.getComponentAt(buttonPosition_x, buttonPosition_y)).setText("" + field[current_x][current_y]);
								buttonPanel.getComponentAt(buttonPosition_x, buttonPosition_y).removeMouseListener(mouseAdapter);
								scoreNumber++;
								score.setText(scoreString + scoreNumber);
								if (field[current_x][current_y] == 0
										&& (buttonPanel.getComponentAt(buttonPosition_x, buttonPosition_y).getBackground() != (Color.GRAY)
												|| buttonPanel.getComponentAt(buttonPosition_x, buttonPosition_y).getBackground() != (Color.RED))) {
									revealNextButton(current_x, current_y);
								}
							}

						}
					}
					return;
				}
			}

			private void removeAllListener() {
				for (Component button : buttonPanel.getComponents()) {
					button.removeMouseListener(this);
				}
			}
		};
	}

	private static void createWindow() {
		mainJFrame = new JFrame();
		mainJFrame.setTitle(titleString);
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainJFrame.setSize(windowSize_x, windowSize_y);
		score = new JLabel(scoreString + scoreNumber);
		mainJFrame.add(score);
		mainJFrame.add(createPanel());
		mainJFrame.setResizable(false);
		mainJFrame.setLocationRelativeTo(null);
		mainJFrame.setVisible(true);
	}

	private static Component createPanel() {
		score = new JLabel(scoreString + scoreNumber);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(score, BorderLayout.NORTH);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(size_x, size_y));
		createButton();
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		btnNewGame = new JButton(newGameString);
		btnNewGame.addActionListener(arg0 -> {
			restartGame();
		});
		mainPanel.add(btnNewGame, BorderLayout.SOUTH);
		return mainPanel;
	}

	private static void restartGame() {
		int size_x = MinesUI.size_x;
		int size_y = MinesUI.size_y;
		mainJFrame.dispose();
		new MinesUI(size_x, size_y);
	}

	private static void createButton() {
		for (int position_x = 0; position_x < size_x; position_x++) {
			for (int position_y = 0; position_y < size_y; position_y++) {
				JButton button = new JButton((""));
				button.setBackground(Color.LIGHT_GRAY);
				button.addMouseListener(mouseAdapter);
				buttonField[position_x][position_y] = button;
				buttonPanel.add(button);
			}
		}
	}

	private static void getButtonPos() {
		buttonPos = new ArrayList<String>();
		for (Component component : buttonPanel.getComponents()) {
			buttonPos.add(component.getX() + ";" + component.getY());
		}
	}
}
