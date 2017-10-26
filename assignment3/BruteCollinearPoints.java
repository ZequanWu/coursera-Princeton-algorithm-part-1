package assignment3;

public class BruteCollinearPoints {
	private LineSegment[] ls;
    public BruteCollinearPoints(Point[] points) {
		// finds all line segments containing 4 points
		if(points == null) throw new java.lang.NullPointerException();
		for(Point p: points)
			if(p == null) throw new java.lang.NullPointerException();
		for(int i = 0;i<points.length;i++) {
			for(int j = i+1;j<points.length;j++)
				if(points[i].compareTo(points[j])== 0) throw new java.lang.IllegalArgumentException();
		}
		ls = new LineSegment[points.length];
		int i = 0;
		double Sab, Sac, Sad;
		Point ap,bp,cp,dp, max,min;
		for(int a = 0;a<points.length;a++){
			ap = points[a];
			for(int b = a+1;b<points.length;b++){
				bp = points[b];
				Sab = ap.slopeTo(bp);
				for(int c = b+1;c<points.length;c++){
					cp = points[c];
					Sac = ap.slopeTo(cp);
					if(Sab == Sac){
						for(int d = c+1;d<points.length;d++){
							dp = points[d];
							Sad = ap.slopeTo(dp);
							if(Sad == Sab){
								max = extreme(ap,bp,cp,dp,-1);
								min = extreme(ap,bp,cp,dp,1);
								ls[i++] = new LineSegment(min,max);
							}
						}
					}
				}
			}
		}
	}
    private Point extreme(Point p1,Point p2,Point p3,Point p4,int n){
    	return compare (compare(compare(p1,p2,n),p3,n), p4 ,n);
    }
    private Point compare(Point p1,Point p2, int n){
    	if(p1.compareTo(p2) == n) return p2;
    	else return p1;
    }
	public int numberOfSegments() {
		// the number of line segments
		int count = 0;
		for(int i = 0;i<ls.length;i++) {
			if(ls[i] != null) count++;
		}
		return count;
	}
	public LineSegment[] segments() {
		// the line segments
		int n = 0;
		LineSegment[] copy = new LineSegment[numberOfSegments()];
		for(int i = 0;i<ls.length;i++)
			if(ls[i] != null) copy[n++] = ls[i];
		return copy;
	}
	public static void main(String[] args){
	}
}
