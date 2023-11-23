package mines;

import java.util.concurrent.ThreadLocalRandom;

public class Mines {
	private final int fieldsize_small = 9;
	private final int fieldsize_medium = 16;
	private final int mines_small = 10;
	private final int mines_medium = 40;
	private final int mines_large = 99;
	private final int mine = -1;
	private int fieldsize_x;
	private int fieldsize_y;
	private int mines;
	private int[][] fields;

 
	public Mines(int fieldsize_x, int fieldsize_y) {
		this.fieldsize_x = fieldsize_x;
		this.fieldsize_y = fieldsize_y;
		if (fieldsize_x == fieldsize_small) {
			mines = mines_small;
		} else if (fieldsize_y == fieldsize_medium) {
			mines = mines_medium;
		} else {
			mines = mines_large;
		}
		fields = new int[fieldsize_x][fieldsize_y];
		setMines();
		createNumbers();
	}

	private void setMines() {
		for (int mines_placed = 0; mines_placed < mines; mines_placed++) {
			int fieldindex_x = ThreadLocalRandom.current().nextInt(0, fieldsize_x);
			int fieldindex_y = ThreadLocalRandom.current().nextInt(0, fieldsize_y);
			if (fields[fieldindex_x][fieldindex_y] == 0) {
				fields[fieldindex_x][fieldindex_y] = mine;
			} else {
				mines_placed--;
			}
		}
	}

	private void createNumbers() {
		for (int field_x = 0; field_x < this.fieldsize_x; field_x++) {
			for (int field_y = 0; field_y < this.fieldsize_y; field_y++) {

				int current_x;
				int xmax;
				if (field_x == 0) {
					current_x = 0;
				} else {
					current_x = field_x - 1;
				}
				if (field_x == fieldsize_x - 1) {
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
					if (field_y == this.fieldsize_y - 1) {
						ymax = field_y;
					} else {
						ymax = field_y + 1;
					}
					for (; current_y <= ymax; current_y++) {
						if (fields[field_x][field_y] == mine && fields[current_x][current_y] != mine) {
							fields[current_x][current_y] += 1;
						}
					}
				}
			}
		}
	}

	public int[][] getField() {
		return fields;
	}
}
