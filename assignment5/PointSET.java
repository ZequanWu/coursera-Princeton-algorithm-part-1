package assignment5;

import java.util.Iterator;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private SET<Point2D> sop;

	public PointSET() {
		// construct an empty set of points
		sop = new SET<Point2D>();
	}

	public boolean isEmpty() {
		// is theset empty?
		return sop.size() == 0;
	}

	public int size() {
		// number of points in the set
		return sop.size();
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		if (p == null)
			throw new java.lang.NullPointerException();
		sop.add(p);
	}

	public boolean contains(Point2D p) {
		// does the set contain point p?
		if (p == null)
			throw new java.lang.NullPointerException();
		return sop.contains(p);
	}

	public void draw() {
		// draw all points to standard draw
		Iterator<Point2D> ite = sop.iterator();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		while (ite.hasNext()) {
			Point2D p = ite.next();
			p.draw();
		}
		

	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle
		if (rect == null)
			throw new java.lang.NullPointerException();
		SET<Point2D> ps = new SET<Point2D>();
		Iterator<Point2D> ite = sop.iterator();
		while (ite.hasNext()) {
			Point2D p = ite.next();
			if (rect.contains(p))
				ps.add(p);
		}
		return ps;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty
		if (p == null)
			throw new java.lang.NullPointerException();
		if (sop.isEmpty())
			return null;
		double min = 10;
		Point2D n = null;
		Iterator<Point2D> ite = sop.iterator();
		while (ite.hasNext()) {
			Point2D point = ite.next();
			double d = point.distanceTo(p);
			if (min >= d) {
				min = d;
				n = point;
			}
		}
		return n;
	}

	public static void main(String[] args) {
		// // unit testing of the methods (optional)
		// PointSET ps = new PointSET();
		// Point2D p1 = new Point2D(0.5, 0.5);
		// Point2D p2 = new Point2D(0.6, 0.3);
		// Point2D p3 = new Point2D(0.7, 0.7);
		// Point2D p4 = new Point2D(0.6, 0.8);
		// Point2D p5 = new Point2D(0.2, 0.7);
		// Point2D p6 = new Point2D(0.3, 0.4);
		// ps.insert(p1);
		// ps.insert(p2);
		// ps.insert(p3);
		// ps.insert(p4);
		// ps.insert(p5);
		// ps.insert(p6);
		// // System.out.println(ps.nearest(new Point2D(0.78,0.1)).toString());
		// RectHV rect = new RectHV(0.4, 0.2, 0.7, 0.6);
		// Iterator<Point2D> ite = ps.sop.iterator();
		// while (ite.hasNext())
		// System.out.println(ite.next().toString());
		// // while (ite.hasNext()) {
		// // System.out.println(ite.next().toString());
		// // }
		// ps.draw();
	}

}
