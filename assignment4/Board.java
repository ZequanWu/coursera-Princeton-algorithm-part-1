package assignment4;

import edu.princeton.cs.algs4.Stack;

public class Board {
	private int dimension;
	private int[][] board;

	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)
		dimension = blocks.length;
		board = new int[dimension][dimension];
		for(int i = 0;i<dimension;i++) {
			for(int j = 0;j<dimension;j++) {
				board[i][j] = blocks[i][j];
			}
		}
	}
	
	public int dimension() {
		// board dimension N
		return dimension;
	}

	public int hamming() {
		// number of blocks out of place
		int count = 0;
		for(int i = 0;i<dimension;i++) {
			for(int j = 0;j<dimension;j++) {
				if(board[i][j] != 0 && board[i][j] != i*dimension+j+1)
					count++;
			}
		}
		return count;
	}
	
	private int distance(int row,int col,int n) {
		int d = 0;
		int r;
		if(n%dimension != 0) r = n/dimension;
		else r = n/dimension-1;
		int c = n-r*dimension -1;
		d += Math.abs(r-row)+Math.abs(c-col);
		return d;
	}
	
	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int count = 0;
		boolean find;
		for(int n = 1;n<dimension*dimension;n++) {
			find = false;
			for(int i = 0;i<dimension;i++) {
				for(int j = 0;j<dimension;j++) {
					if(board[i][j] == n){
						count += distance(i,j,n);
						find = true;
						break;
					}
				}
				if(find) break;
			}
		}
		return count;
	}
	
	public boolean isGoal() {
		// is this board the goal board?
		return hamming() == 0;
	}
	
	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		int[][] board2 = new int[dimension][dimension];
		for(int i = 0;i<dimension;i++) {
			for(int j = 0;j<dimension;j++) {
				board2[i][j] = board[i][j];
			}
		}
		int x = 0,y = 0, n = 0;
		for(int i = 0;i<dimension;i++) {
			for(int j = 0;j<dimension;j++) {
				if(n < 1 && board2[i][j] != 0) {
					x = i;
					y = j;
					n++;
				}
				else if(n == 1 && board2[i][j] != 0){
					int temp = board2[i][j];
					board2[i][j] = board2[x][y];
					board2[x][y] = temp;
					n++;
				}
			}
		}
		Board twin = new Board(board2);
		return twin;
	}
	
	private void swap(int[][] cl,int r1, int c1, int r2, int c2) {
		int temp = cl[r1][c1];
		cl[r1][c1] = cl[r2][c2];
		cl[r2][c2] = temp;
	}
	
	public boolean equals(Object y) {
		// does this board equal y?
		if(y == null) return false;
		if(y.getClass() != this.getClass()) return false;
		if(y == this) return true;
		Board that = (Board) y;
		if(that.board.length != this.board.length) return false;
		for(int i = 0;i<dimension;i++){
			for(int j = 0;j<dimension;j++){
				if(that.board[i][j] != this.board[i][j]) return false;
			}
		}
		return true;
	}
	
	private Board[] find() {
		int row = -1, col = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if(board[i][j] == 0) {
					row = i;
					col = j;
					break;
				}
			}
			if(row != -1) break;
		}
		Board[] b = new Board[4];
		int n = 0;
		if(col != 0)
			function(b,n++,row,col,row,col-1);
		if(col != dimension-1)
			function(b,n++,row,col,row,col+1);
		if(row == 0)
			function(b,n++,row,col,row+1,col);
		else if(row == dimension-1)
			function(b,n++,row,col,row-1,col);
		else{
			function(b,n++,row,col,row+1,col);
			function(b,n++,row,col,row-1,col);
		}
		return b;
	}
	private int[][] copy(int[][] original) {
		int[][] copy = new int[original.length][original.length];
		for(int i = 0;i<dimension;i++) {
			for(int j = 0;j<dimension;j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}
	private void function(Board[] b,int n, int r1,int c1, int r2, int c2){
		int[][]copy = copy(this.board);
		swap(copy,r1,c1,r2,c2);
		b[n] = new Board(copy);
	}
	
	public Iterable<Board> neighbors() {
		// all neighboring boards
		Stack<Board> ite = new Stack<Board>();
		for(Board b: find()) {
			if(b != null) ite.push(b);
		}
		return ite;
	}
	
	public String toString() {
		// string representation of this board (in the output format specified below)
		StringBuilder s = new StringBuilder();
	    s.append(dimension + "\n");
	    for (int i = 0; i < dimension; i++) {
	        for (int j = 0; j < dimension; j++) {
	            s.append(String.format("%2d ", board[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	public static void main(String[] args) {
		// unit tests (not graded)
		int[][] ll = {{10, 13, 12,6},{15,1,9,8},{4,0,11,7},{14,2,5,3}};
		Board b = new Board(ll);
		System.out.println(b.toString());
		System.out.println(b.hamming());
	}
	
}