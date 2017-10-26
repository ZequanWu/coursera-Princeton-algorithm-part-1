package assignment3;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
	private LineSegment[] ls;
	private int lsPos;
	private Point[] points2;
	private Point origin;

	public FastCollinearPoints(Point[] points) {
		// finds all line segments containing 4 or more points
		if(points == null) throw new java.lang.NullPointerException();
		for(Point p:points){
			if(p == null) throw new java.lang.NullPointerException();
		}
		for(int i = 0;i<points.length;i++) {
			for(int j = i+1;j<points.length;j++) {
				if(points[i].compareTo(points[j])== 0) throw new java.lang.IllegalArgumentException();
			}
		}
		points2 = new Point[points.length-1];
		ls = new LineSegment[points.length*10];
		lsPos = 0;
		for(int i = 0;i<points.length;i++){
			int n = 0;
			for(int j = 0;j<points.length;j++) {
				if(i != j) 
					points2[n++] = points[j];
			}
			origin = points[i];
			Arrays.sort(points2,points[i].slopeOrder());
			segment();
		}
	}
	private int length(Point[] p){
		int count = 0;
		for(int i = 0;i<p.length;i++){
			if(p[i] != null) count++;
		}
		return count;
	}
	private void segment() {
		int i,j;
		int k = 0;
		Point[] subPoints = new Point[points2.length];
		Comparator<Point> comp = origin.slopeOrder();
		for(i = 0;i<points2.length;i = j){
			subPoints[k++] = points2[i];
			for(j = i+1;j<points2.length;j++){
				if(comp.compare(points2[i],points2[j]) != 0) break;
				subPoints[k++] = points2[j];
			}
			if(length(subPoints) >= 3){
				if(origin.compareTo(min(subPoints)) < 0) {
					ls[lsPos++] = new LineSegment(origin,max(subPoints));
				}
			}
			subPoints = new Point[points2.length];
			k = 0;
		}
	}
	public int numberOfSegments() {
		// the number of line segments
		int count = 0;
		for(LineSegment l:ls){
			if(l != null) count++;
		}
		return count;
	}
	private Point min(Point[] p) {
		Point mi = p[0];
		for(int i = 1;i<p.length;i++){
			if(p[i] == null) break;
			if(p[i].compareTo(mi) < 0)
				mi = p[i];
		}
		return mi;
	}
	private Point max(Point[] p) {
		Point mi = p[0];
		for(int i = 1;i<p.length;i++){
			if(p[i] == null) break;
			if(p[i].compareTo(mi) > 0)
				mi = p[i];
		}
		return mi;
	}

	public LineSegment[] segments() {
		// the line segments
		LineSegment[] ls2 = new LineSegment[numberOfSegments()];
		int n = 0;
		for(LineSegment l:ls){
			if(l != null) ls2[n++] = l;
		}
		return ls2;
	}
	public static void main(String[] args) {
		/*
		Point p1 = new Point(0,0);
		Point p2 = new Point(1,1);
		Point p3 = new Point(2,2);
		Point p4 = new Point(3,3);
		Point p5 = new Point(1,2);
		Point p6 = new Point(2,4);
		Point p7 = new Point(3,6);
		Point p9 = new Point(1234,431);
		Point p10 = new Point(211,-123);
		Point[] pl = {p1,p2,p3,p4,p5,p6,p7,p9,p10};
		StdRandom.shuffle(pl);
		FastCollinearPoints fcp = new FastCollinearPoints(pl);
		System.out.println(fcp.numberOfSegments());
		LineSegment[] ls = fcp.segments();
		for(int i = 0;i<ls.length;i++){
			System.out.println(ls[i]);
		}
		*/
	}
}