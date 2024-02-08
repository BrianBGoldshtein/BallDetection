package FiltersHere.Filters;

import core.DImage;

import java.util.ArrayList;

public class Cluster extends Point{
    private ArrayList<Point> ownedPoints;
    public Cluster(short r, short g, short b) {
        super(r,g,b);
        ownedPoints = new ArrayList<>();
    }

    public static ArrayList<Cluster> initializeClusters(int n, DImage img) {
        ArrayList<Cluster> out = new ArrayList<>();


        for(int i = 0; i < n; i++) {
            int randRow = (int) (Math.random()*img.getHeight());
            int randCol = (int) (Math.random()*img.getWidth());
            out.add(new Cluster(
                    img.getRedChannel()[randRow][randCol],
                    img.getGreenChannel()[randRow][randCol],
                    img.getBlueChannel()[randRow][randCol]
            ));
        }
        return out;
    }

    public static void clearAllPoints(ArrayList<Cluster> clusters) {
        for(Cluster cluster: clusters) {
            cluster.clearPoints();
        }
    }

    public void addPoint(Point p) {
        ownedPoints.add(p);
    }

    public void clearPoints() {
        for(Point p: ownedPoints) {
            p.setOwner(null);
        }
        ownedPoints.clear();
    }

    public boolean findNewCenter() {
        double sumR, sumG, sumB;
        sumR = sumG = sumB = 0;
        for(Point p: ownedPoints) {
            sumR += p.getR();
            sumG += p.getG();
            sumB += p.getB();
        }
        short oldR = r; short oldG = g; short oldB = b;
        this.setColor((short) (sumR/ownedPoints.size()), (short) (sumG/ownedPoints.size()), (short) (sumB/ownedPoints.size()));

        if(r == oldR && g == oldG && b == oldB) return false;
        return true;
    }
}
