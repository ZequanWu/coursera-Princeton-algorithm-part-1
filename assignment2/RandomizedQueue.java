package assignment2;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.System;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int N;
	private Item[] arr;
	private int pos;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		// construct an empty randomized queue
		arr = (Item[]) new Object[2];
		N = 0;
		pos = 0;
	}

	public boolean isEmpty() {
		// is the queue empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the queue
		return N;
	}

	@SuppressWarnings("unchecked")
	private void resizing(int capacity) {
		assert capacity >= N;
		Item[] temp = (Item[]) new Object[capacity];
		int j = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				temp[j] = arr[i];
				j++;
			}
		}
		arr = temp;
	}

	public void enqueue(Item item) {
		// add the item
		if (item == null)
			throw new java.lang.NullPointerException();
		if (arr.length == pos) {
			resizing(pos * 2);
			pos = N;
		}
		arr[pos] = item;
		pos++;
		N++;
	}

	@SuppressWarnings("unchecked")
	public Item dequeue() {
		// remove and return a random item
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int i = StdRandom.uniform(0, pos);
		while (arr[i] == null) {
			i = StdRandom.uniform(0, pos);
		}
		Item item = arr[i];
		arr[i] = null;
		N--;
		if (N > 0 && N <= arr.length / 4) {
			Item[] temp = (Item[]) new Object[N * 2];
			int j = 0;
			for (int id = 0; id < arr.length; id++) {
				if (arr[id] != null) {
					temp[j] = arr[id];
					j++;
				}
			}
			arr = temp;
			pos = arr.length / 2;
		}
		return item;
	}

	public Item sample() {
		// return (but do not remove) a random item
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int i = StdRandom.uniform(0, pos);
		while (arr[i] == null) {
			i = StdRandom.uniform(0, pos);
		}
		return arr[i];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomizedQueueIterator<Item>();
	}

	@SuppressWarnings("hiding")
	private class RandomizedQueueIterator<Item> implements Iterator<Item> {
		private Item shuffled[];
		private int Num;

		@SuppressWarnings("unchecked")
		public RandomizedQueueIterator() {
			Num = N;
			shuffled = (Item[]) new Object[Num];
			int j = 0;
			for (int id = 0; id < arr.length; id++) {
				if (arr[id] != null)
					shuffled[j++] = (Item) arr[id];
			}
			StdRandom.shuffle(shuffled);
		}

		@Override
		public boolean hasNext() {
			return Num != 0;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			Item x = shuffled[Num - 1];
			Num--;
			return x;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		int N = 500;
		int r;
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		for (int i = 0; i < N; i++) {
			r = StdRandom.uniform(11);
			// double t1 = System.currentTimeMillis();
			if (r < 3)
				rq.enqueue(i);
			else if (r < 6) {
				try {
					rq.dequeue();
					// System.out.println(rq.dequeue());
				} catch (java.util.NoSuchElementException e) {
					// System.out.println("error");
				}
			} else if (r < 8) {
				try {
					rq.sample();
					// System.out.println(rq.sample());
				} catch (java.util.NoSuchElementException e) {
					// System.out.println("error");
				}
			} else if (r < 10) {
				System.out.println(rq.isEmpty());
			} else {
				System.out.println(rq.size());
			}
			// double t2 = System.currentTimeMillis();
			// System.out.println(t2-t1);
		}
		System.out.println(true);
	}

}
