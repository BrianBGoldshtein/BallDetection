package FiltersHere.Filters;

import FiltersHere.Main;
import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

public class ColorMasking implements PixelFilter, Interactive {
    private boolean useHues = false;

    private static short threshold = 20;



    @Override
    public DImage processImage(DImage img) {

        short[][] r = img.getRedChannel();
        short[][] g = img.getGreenChannel();
        short[][] b = img.getBlueChannel();


        short[][] out = new short[img.getHeight()][img.getWidth()];


        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {

                if(getTargetDistance(r[row][col],g[row][col],b[row][col]) <= threshold) {
                    out[row][col] = 255;
                }


            }
        }

        img.setPixels(out);
        return img;
    }

    private double getTargetDistance(short r, short g, short b) {
        double smallestDist = Double.MAX_VALUE;
     for(short[] targetColor: Main.colors) {

         //if(useHues) return Math.abs(getHue(r,g,b) - getHue(targetColor[0],targetColor[1], targetColor[2]));

         double dist =  Math.sqrt((r-targetColor[0])*(r-targetColor[0])
                 + (g-targetColor[1])*(g-targetColor[1])
                 + (b-targetColor[2])*(b-targetColor[2]));

         if(dist < smallestDist) smallestDist = dist;

     }
     return smallestDist;
    }


    private static double getHue(short r, short g, short b) {
        short max = (short) Math.max(r,Math.max(g,b));
        short min = (short) Math.min(r,Math.min(g,b));

        if(max ==0) return 0;
        if(r == max ) return 60*( ((g-b)%6) / ((double)(max-min)) );
        if(g == max ) return 60*( (2.0 + (b-r)) / ((double)(max-min)) );
        if(b == max ) return 60*( ( 4.0 + (r-g)) / ((double)(max-min)) );

        return -1;
    }




    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
    }

    @Override
    public void keyPressed(char key, int keyCode) {
        if(keyCode == PApplet.UP) threshold++;
        if(keyCode == PApplet.DOWN) threshold--;
        if(key == 'h') useHues = !useHues;
        System.out.println(threshold);

    }
}
