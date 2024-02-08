package FiltersHere.Filters;

import core.DImage;

import java.util.ArrayList;

public class Point {
    protected short r, g, b;

    public static Point[][] createPoints(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        Point[][] out = new Point[img.getHeight()][img.getWidth()];

        for(int row = 0; row < img.getHeight(); row++) {
            for(int col = 0; col < img.getWidth(); col++) {
                out[row][col] = new Point(red[row][col], green[row][col], blue[row][col]);
            }
        }
        return out;
    }

    public static DImage createImage(Point[][] points, DImage img) {
        short[][] red = new short[points.length][points[0].length];
        short[][] green = new short[points.length][points[0].length];
        short[][] blue = new short[points.length][points[0].length];

        for(int row = 0; row < points.length; row++) {
            for(int col = 0; col < points[0].length; col++) {
                red[row][col] = points[row][col].getOwner().getR();
                green[row][col] = points[row][col].getOwner().getG();
                blue[row][col] = points[row][col].getOwner().getB();

            }
        }

        img.setColorChannels(red,green,blue);
        return img;
    }


    public Cluster getOwner() {
        return owner;
    }

    public short getR() {
        return r;
    }

    public short getG() {
        return g;
    }

    public short getB() {
        return b;
    }

    protected Cluster owner;

    public Point(short r, short g, short b) {
        setColor(r,g,b);
    }

    public void setColor(short r, short g, short b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setOwner(Cluster newOwner) {
        if(newOwner != null) newOwner.addPoint(this);
        owner = newOwner;
    }

    public double distance(Point p1, Point p2) {
        return Math.sqrt(
                (p1.getR()-p2.getR())*(p1.getR()-p2.getR()) +
                        (p1.getG()-p2.getG())*(p1.getG()-p2.getG()) +
                        (p1.getB()-p2.getB())*(p1.getB()-p2.getB())
        );
    }
    public Cluster findClosestCluster(ArrayList<Cluster> clusters) {
        Cluster closestCluster = null;
        double smallestDistance = Double.MAX_VALUE;
        for(Cluster cluster: clusters) {
            double distance = distance(cluster, this);
            if(distance > smallestDistance) continue;
            smallestDistance = distance;
            closestCluster = cluster;

        }

        return closestCluster;
    }
}
