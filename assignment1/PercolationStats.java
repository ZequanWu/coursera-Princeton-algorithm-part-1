package assignment1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
	private double[] statics;
	private int expCount;
	public PercolationStats(int N, int T)
	{
		// perform T independent experiments on an N-by-N grid
		if(N<=0 || T<=0)
			throw new IllegalArgumentException();
		expCount = T;
		statics = new double[T];
		for(int i = 0;i<T;i++)
		{
			Percolation p = new Percolation(N);
			double count = 0;
			int x,y;
			while(! p.percolates())
			{
				x = StdRandom.uniform(N)+1;
				y = StdRandom.uniform(N)+1;
				if(!p.isOpen(x,y))
				{
					p.open(x,y);
					count++;
				}
			}
			statics[i] = (count/(N*N));
		}
	}

	public double mean()
	{
		return StdStats.mean(statics);
	}
	public double stddev()       
	{// sample standard deviation of percolation threshold
		if(expCount == 1) return Double.NaN;
		return StdStats.stddev(statics);
	}
	public double confidenceLo()
	{// low  endpoint of 95% confidence interval
		return mean()-1.96*stddev()/Math.sqrt(expCount);
	}
    public double confidenceHi()
    {// high endpoint of 95% confidence interval
    	return mean()+1.96*stddev()/Math.sqrt(expCount);
    }
	public static void main(String[] args) {
		PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		System.out.printf("%-25s= %.16f\n", "mean", p.mean());
		System.out.printf("%-25s= %.16f\n","stddev",p.stddev());
		System.out.printf("%-25s= %.16f, %.16f\n","95% confidence interval",p.confidenceLo(),p.confidenceHi());
	}

}
