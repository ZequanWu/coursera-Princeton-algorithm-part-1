package assignment5;

//import java.util.Iterator;
//
//import edu.princeton.cs.algs4.In;

//import java.util.Iterator;

//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private class Node {
		private Point2D p;
		private Node lb;
		private Node rt;
		private RectHV rect;

		private Node(Point2D p, double xmin, double ymin, double xmax, double ymax) {
			this.p = p;
			rect = new RectHV(xmin, ymin, xmax, ymax);
		}

	}

	private Node root;
	private int size;
	private Point2D nearest;
	private double sqdtp;

	public KdTree() {
		// construct an empty set of points
		size = 0;
	}

	public boolean isEmpty() {
		// is the set empty?
		return size == 0;
	}

	public int size() {
		// number of points in the set
		return size;
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null)
			throw new java.lang.NullPointerException();
		// if (root == null) {
		// root = new Node(p, 0, 0, 1, 1);
		// size++;
		// return;
		// }
		insert(p, root, 1);
		size++;
	}

	private void insert(Point2D p, Node x, int n) {
		if (x == null) {
			this.root = new Node(p, 0, 0, 1, 1);
			return;
		}
		if (n % 2 == 0) {
			double cmp = x.p.y() - p.y();
			if (cmp > 0) {
				if (x.lb == null) {
					x.lb = new Node(p, x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
				} else
					insert(p, x.lb, n + 1);
			} else {
				if (p.equals(x.p))
					size--;
				else if (x.rt == null) {
					x.rt = new Node(p, x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
				} else
					insert(p, x.rt, n + 1);
			}
		} else {
			double cmp = x.p.x() - p.x();
			if (cmp > 0) {
				if (x.lb == null) {
					x.lb = new Node(p, x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
				} else
					insert(p, x.lb, n + 1);
			} else {
				if (p.equals(x.p))
					size--;
				else if (x.rt == null) {
					x.rt = new Node(p, x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
				} else
					insert(p, x.rt, n + 1);
			}
		}
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		return contains(root, p, 1);
	}

	private boolean contains(Node x, Point2D p, int n) {
		if (p == null)
			return false;
		if (x == null)
			return false;
		if (n % 2 == 0) {
			double cmp = x.p.y() - p.y();
			if (cmp > 0)
				return contains(x.lb, p, n + 1);
			if (cmp < 0)
				return contains(x.rt, p, n + 1);
			else {
				if (x.p.x() == p.x())
					return true;
				else
					return contains(x.rt, p, n + 1);
			}
		} else {
			double cmp = x.p.x() - p.x();
			if (cmp > 0)
				return contains(x.lb, p, n + 1);
			if (cmp < 0)
				return contains(x.rt, p, n + 1);
			else {
				if (x.p.y() == p.y())
					return true;
				else
					return contains(x.rt, p, n + 1);
			}
		}
	}

	public void draw() {
		// draw all points to standard draw
		draw(root, 1);
	}

	private void draw(Node x, int n) {
		if (x == null)
			return;
		StdDraw.setPenRadius(.01);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.point(x.p.x(), x.p.y());
		StdDraw.setPenRadius();
		if (n % 2 == 0) {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		} else {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		}
		draw(x.lb, n + 1);
		draw(x.rt, n + 1);
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect == null)
			throw new java.lang.NullPointerException();
		Stack<Point2D> sp = new Stack<Point2D>();
		range(root, rect, sp);
		return sp;
	}

	private void range(Node x, RectHV rect, Stack<Point2D> sp) {
		if (x == null || !x.rect.intersects(rect))
			return;
		if (rect.contains(x.p))
			sp.push(x.p);
		range(x.lb, rect, sp);
		range(x.rt, rect, sp);
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null)
			throw new java.lang.NullPointerException();
		if (isEmpty())
			return null;
		this.nearest = root.p;
		this.sqdtp = p.distanceSquaredTo(this.nearest);
		nearest(p, root, 1);
		return this.nearest;
	}

	private void nearest(Point2D p, Node x, int n) {
		if (x == null)
			return;
		double xtp = x.p.distanceSquaredTo(p);
		if (xtp < sqdtp) {
			nearest = x.p;
			sqdtp = xtp;
		}
		if (n % 2 == 0) {
			double cmp = x.p.y() - p.y();
			if (cmp > 0) {
				nearest(p, x.lb, n + 1);
				if (x.rt != null && sqdtp > x.rt.rect.distanceSquaredTo(p))
					nearest(p, x.rt, n + 1);
			} else {
				nearest(p, x.rt, n + 1);
				if (x.lb != null && sqdtp > x.lb.rect.distanceSquaredTo(p))
					nearest(p, x.lb, n + 1);
			}
		} else {
			double cmp = x.p.x() - p.x();
			if (cmp > 0) {
				nearest(p, x.lb, n + 1);
				if (x.rt != null && sqdtp > x.rt.rect.distanceSquaredTo(p))
					nearest(p, x.rt, n + 1);
			} else {
				nearest(p, x.rt, n + 1);
				if (x.lb != null && sqdtp > x.lb.rect.distanceSquaredTo(p))
					nearest(p, x.lb, n + 1);
			}
		}

	}

	public static void main(String[] args) {
		// Point2D[] ps = new Point2D[9];
		// int i = 0;
		// KdTree kd = new KdTree();
		// String filename = args[0];
		// In in = new In(filename);
		// while (!in.isEmpty()) {
		// double x = in.readDouble();
		// double y = in.readDouble();
		// Point2D p = new Point2D(x, y);
		// ps[i++] = p;
		// kd.insert(p);
		// }
		// RectHV rect = new RectHV(0.3, 0.5, 0.85, 0.6);
		// Iterator<Point2D> ite = kd.range(rect).iterator();
		// while (ite.hasNext())
		// System.out.print(ite.next() + ", ");
		// System.out.println();
		// System.out.println(kd.contains(ps[4]));
		// Point2D p11 = new Point2D(0.15, 0.345492);
		// kd.insert(p11);
		// ps[i++] = p11;
		// Point2D test = new Point2D(0.215, 0.4);
		// for(Point2D p: ps)
		// System.out.print(kd.contains(p)+ ", ");

		// kd.draw();

		// Point2D p1 = new Point2D(0.5, 0.5);
		// Point2D p2 = new Point2D(0.6, 0.3);
		// Point2D p3 = new Point2D(0.7, 0.7);
		// Point2D p4 = new Point2D(0.6, 0.8);
		// Point2D p5 = new Point2D(0.2, 0.7);
		// Point2D p6 = new Point2D(0.3, 0.4);
		// Point2D p0 = new Point2D(0.0, 0.0);
		// kd.insert(p1);
		// kd.insert(p2);
		// kd.insert(p3);
		// kd.insert(p4);
		// kd.insert(p5);
		// kd.insert(p6);
		// kd.insert(p0);
		// kd.insert(p0);
		// System.out.println(kd.size());
	}
}