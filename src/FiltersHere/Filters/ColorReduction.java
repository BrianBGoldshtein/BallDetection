package FiltersHere.Filters;

import FiltersHere.Main;
import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import javax.swing.*;
import java.util.ArrayList;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.UP;

public class ColorReduction implements PixelFilter, Interactive {
    private int numClusters;
    private boolean numClustersChanged;

    private ArrayList<Cluster> clusters;
    public ColorReduction() {
        numClusters = Integer.parseInt(JOptionPane.showInputDialog("How many colors do you want in the photo?"));
        numClustersChanged = true;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
    public ColorReduction(int n) {
        numClusters = n;
        numClustersChanged = true;
    }

    public void increaseClusters() {
        numClusters++;
    }

    public void decreaseClusters() {
        numClusters--;
    }

    @Override
    public DImage processImage(DImage img) {
        Point[][] points = Point.createPoints(img);




        boolean done = false;
        if(numClustersChanged) {clusters = Cluster.initializeClusters(numClusters, img); numClustersChanged = false;}

        clusters.get(0).setColor(Main.red[0], Main.red[1], Main.red[2]);
        clusters.get(1).setColor(Main.green[0],Main.green[1], Main.green[2]);
        clusters.get(2).setColor(Main.blue[0],Main.blue[1], Main.blue[2]);
        clusters.get(3).setColor(Main.yellow[0], Main.yellow[1], Main.yellow[2]);
        while(!done) {
            done = true;
            for(Point[] pointRow: points) for (Point p: pointRow){
                p.setOwner(p.findClosestCluster(clusters));
            }

            for(Cluster cluster: clusters) {
                done = !cluster.findNewCenter();
            }

            if(!done) Cluster.clearAllPoints(clusters);
        }

        img = Point.createImage(points, img);
        return img;

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
    }


    @Override
    public void keyPressed(char key, int keyCode) {
        if(keyCode != UP && keyCode != DOWN && key != ' ') return;
        if(keyCode == UP) numClusters++;
        if(keyCode == DOWN) numClusters--;
        if(numClusters <= 0) numClusters = 1;
        numClustersChanged = true;
    }


    public Cluster findClosestCluster(short r, short g, short b) {
        return (new Point(r,g,b).findClosestCluster(clusters));
    }
}
