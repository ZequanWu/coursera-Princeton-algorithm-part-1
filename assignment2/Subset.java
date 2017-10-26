package assignment2;

import edu.princeton.cs.algs4.StdIn;

public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		while(!StdIn.isEmpty())
			rq.enqueue(StdIn.readString());
		for(int i = 0;i<k;i++)
			System.out.println(rq.dequeue());
	}

}