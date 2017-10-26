package assignment1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {
	private boolean[][] grid;
	private int len;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	public Percolation2(int N){
		if(N<=0)
			throw new IllegalArgumentException();
		len = N;
		grid = new boolean[N][N];
		for(int i = 0;i<N;i++)
			for(int j = 0;j<N;j++)
				grid[i][j] = false;
		uf = new WeightedQuickUnionUF(2+N*N);
		uf2 = new WeightedQuickUnionUF(1+N*N);
	}
	private int xyTo1D(int i,int j){
		 return (i-1)*len+(j-1);
	}
	private boolean existAndOpen(int i,int j){
		if(i>len || i<1 || j>len || j<1)
			return false;
		return isOpen(i,j);
	}
	private boolean existAndFull(int i,int j){
		if(i>len || i<1 || j>len || j<1)
			return false;
		return isFull(i,j);
	}
	public void open(int i,int j){
		if(i>len||i<1||j>len||j<1)
			throw new IndexOutOfBoundsException();
		grid[i-1][j-1] = true;
		if(i == 1){
			uf.union(len*len, xyTo1D(i,j));
			if(existAndOpen(i+1,j)){
				uf2.union(xyTo1D(i+1,j), xyTo1D(i,j));
				uf.union(xyTo1D(i,j), xyTo1D(i+1,j));
			}
			if(existAndOpen(i-1,j)){
				uf2.union(xyTo1D(i-1,j), xyTo1D(i,j));
				uf.union(xyTo1D(i,j), xyTo1D(i-1,j));
			}
			if(existAndOpen(i,j-1)){
				uf2.union(xyTo1D(i,j-1), xyTo1D(i,j));
				uf.union(xyTo1D(i,j), xyTo1D(i,j-1));
			}
			if(!(j== len && i == len)){
				if(existAndOpen(i,j+1)){
					uf2.union(xyTo1D(i,j+1), xyTo1D(i,j));
					uf.union(xyTo1D(i,j), xyTo1D(i,j+1));
				}
			}
		}
		else{
			if(existAndFull(i+1,j)){
				uf2.union(xyTo1D(i+1,j), xyTo1D(i,j));
				uf.union(xyTo1D(i,j), xyTo1D(i+1,j));
			}
			if(existAndFull(i-1,j)){
				uf2.union(xyTo1D(i-1,j), xyTo1D(i,j));
				uf.union(xyTo1D(i,j), xyTo1D(i-1,j));
			}
			if(existAndFull(i,j-1)){
				uf2.union(xyTo1D(i,j-1), xyTo1D(i,j));
				uf.union(xyTo1D(i,j), xyTo1D(i,j-1));
			}
			if(!(j== len && i == len)){
				if(existAndFull(i,j+1)){
					uf2.union(xyTo1D(i,j+1), xyTo1D(i,j));
					uf.union(xyTo1D(i,j), xyTo1D(i,j+1));
				}
			}
			if(existAndOpen(i+1,j)){
				uf.union(xyTo1D(i,j), xyTo1D(i+1,j));
			}
			if(existAndOpen(i-1,j)){
				uf.union(xyTo1D(i,j), xyTo1D(i-1,j));
			}
			if(existAndOpen(i,j-1)){
				uf.union(xyTo1D(i,j), xyTo1D(i,j-1));
			}
			if(!(j== len && i == len)){
				if(existAndOpen(i,j+1)){
					uf.union(xyTo1D(i,j), xyTo1D(i,j+1));
				}
			}
		}
	}
	public boolean isOpen(int i,int j){
		if(i>len||i<1||j>len||j<1)
			throw new IndexOutOfBoundsException();
		return grid[i-1][j-1];
	}
	public boolean isFull(int i, int j){
		if(i>len||i<1||j>len||j<1)
			throw new IndexOutOfBoundsException();
		return uf.connected(xyTo1D(i,j), len*len);
	}
	public boolean percolates(){
		return uf.connected(len*len, len*len+1);
	}
	public static void main(String[] args){
		Percolation2 p = new Percolation2(3);
		p.open(3, 1);
		p.open(2, 2);
		p.open(2, 1);
		p.open(1, 1);
		System.out.println(p.percolates());
	}
}
