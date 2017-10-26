package assignment4;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;

public class Solver {
	// private SearchNode<Board>[] snlist;

	private class SearchNode<T> implements Comparable<T> {
		private int move;
		private Board board;
		private SearchNode<Board> previous;
		private int manhattan;
		private int hamming;
		private int priority;

		public SearchNode(Board b, SearchNode<Board> sn) {
			this.board = b;
			this.previous = sn;
			if (this.previous != null)
				this.move = previous.move + 1;
			else
				this.move = 0;
			manhattan = this.board.manhattan();
			hamming = this.board.hamming();
			priority = manhattan + move;
		}

		@Override
		public int compareTo(T o) {
			@SuppressWarnings("unchecked")
			SearchNode<Board> that = (SearchNode<Board>) o;
			if (this.priority < that.priority)
				return -1;
			else if (this.priority > that.priority)
				return 1;
			else if (this.manhattan < that.manhattan)
				return -1;
			else if (this.manhattan > that.manhattan)
				return 1;
			else if (this.move < that.move)
				return -1;
			else if (this.move > that.move)
				return 1;
			else if (this.hamming < that.hamming)
				return 1;
			else if (this.hamming > that.hamming)
				return -1;
			else
				return 0;
		}
	}

	private MinPQ<SearchNode<Board>> pq;
	private MinPQ<SearchNode<Board>> pq2;
	private SearchNode<Board> sn;
	private SearchNode<Board> sn2;
	private boolean solvable;
	private boolean solvable2;
	private Stack<Board> solutionStack;
	private int moves;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		if (initial == null)
			throw new java.lang.NullPointerException();
		solvable = false;
		solvable2 = false;
		solutionStack = new Stack<Board>();
		moves = -1;
		sn = new SearchNode<Board>(initial, null);
		pq = new MinPQ<SearchNode<Board>>();
		pq.insert(sn);

		pq2 = new MinPQ<SearchNode<Board>>();
		sn2 = new SearchNode<Board>(initial.twin(), null);
		pq2.insert(sn2);
		function();

	}

	private void function() {
		SearchNode<Board> s;
		SearchNode<Board> s2;
		while ((!solvable) && (!solvable2)) {
			s = pq.delMin();
			s2 = pq2.delMin();
			if (s.board.isGoal()) {
				this.moves = s.move;
				this.solvable = true;
				solutionStack.push(s.board);
			} else if (s2.board.isGoal()) {
				this.solvable2 = true;
			} else {
				iterate(s, pq, false);
				iterate(s2, pq2, true);
			}
		}
	}

	private void iterate(SearchNode<Board> sNode, MinPQ<SearchNode<Board>> p, boolean twin) {
		Iterator<Board> iterator = sNode.board.neighbors().iterator();
		Board b1;
		SearchNode<Board> nextNode;
		while (iterator.hasNext()) {
			b1 = iterator.next();
			nextNode = new SearchNode<Board>(b1, sNode);
			if (!repeated(nextNode, p)) {
				p.insert(nextNode);
				if (nextNode.board.isGoal()) {
					if (!twin)
						formSolution(nextNode);
					else
						solvable2 = true;
					break;
				}
			}
		}

	}

	private void formSolution(SearchNode<Board> sNode) {
		SearchNode<Board> current = new SearchNode<Board>(sNode.board, sNode.previous);
		solutionStack.push(current.board);
		moves = current.move;
		solvable = true;
		while (current.previous != null) {
			current = current.previous;
			solutionStack.push(current.board);
		}
	}

	private boolean repeated(SearchNode<Board> b, MinPQ<SearchNode<Board>> p) {
		SearchNode<Board> current = b.previous;
		while (current != null) {
			if (current.board.equals(b.board))
				return true;
			current = current.previous;
		}
		/*
		 * Iterator<SearchNode<Board>> ite = p.iterator(); while (ite.hasNext())
		 * { if (ite.next().board.equals(b.board)) return true; }
		 */

		return false;
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return solvable;
	}

	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		return moves;
	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if (solvable)
			return solutionStack;
		return null;
	}

	public static void main(String[] args) {
		// solve a slider puzzle (given below)
		In in = new In(args[0]);
		int N = in.readInt();
		
		 int[][] ll = new int[N][N];
         for (int i = 0; i < N; i++) {
             for (int j = 0; j < N; j++) {
                 ll[i][j] = in.readInt();
             }
         }
		Board b = new Board(ll);
		Solver s = new Solver(b);
		Iterator<Board> ite = s.solution().iterator();
		while (ite.hasNext()) {
			System.out.println(ite.next().toString());
		}
		System.out.println(s.moves());
		System.out.println("done");
	}
}