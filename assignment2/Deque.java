package assignment2;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private int N;
	private Node head;
	private Node end;

	private class Node {
		private Item item;
		private Node pre;
		private Node next;
	}

	public Deque() {
		// construct an empty deque
		N = 0;
		head = new Node();
		end = new Node();
		head.next = end;
		end.pre = head;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the deque
		return N;
	}

	public void addFirst(Item item) {
		// add the item to the front
		if (item == null)
			throw new java.lang.NullPointerException();
		Node newOne = new Node();
		newOne.item = item;
		newOne.pre = head;
		head.next.pre = newOne;
		newOne.next = head.next;
		head.next = newOne;
		N++;
	}

	public void addLast(Item item) {
		// add the item to the end
		if (item == null)
			throw new java.lang.NullPointerException();
		Node newOne = new Node();
		newOne.item = item;
		newOne.next = end;
		newOne.pre = end.pre;
		end.pre.next = newOne;
		end.pre = newOne;
		N++;
	}

	public Item removeFirst() {
		// remove and return the item from the front
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = head.next.item;
		head.next = head.next.next;
		head.next.pre = head;
		N--;
		return item;
	}

	public Item removeLast() {
		// remove and return the item from the end
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = end.pre.item;
		end.pre = end.pre.pre;
		end.pre.next = end;
		N--;
		return item;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current;

		public DequeIterator() {
			current = head;
		}

		@Override
		public boolean hasNext() {
			return current.next != end;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			current = current.next;
			return current.item;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
	}
}