package FiltersHere.Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Isolate implements PixelFilter {
    private double x;

    public Isolate(double x) {
        this.x = x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        for(int row = 0; row < red.length; row++) {
            for(int col = 0; col < red[0].length; col++) {
                short[] color = isolate(red[row][col], green[row][col], blue[row][col]);
                red[row][col] = color[0];
                green[row][col] = color[1];
                blue[row][col] = color[2];
            }
        }

        img.setColorChannels(red,green,blue);
        return img;
    }

    private short[] isolate(short r, short g, short b) {
        short nR = (short) (Math.round((double)(r)/255.0 + x)*255);
        short nG = (short) (Math.round((double)(g)/255.0 + x)*255);
        short nB = (short) (Math.round((double)(b)/255.0 + x)*255);

        if(nR == 0) r = 0;
        if(nG == 0) g = 0;
        if(nB == 0) b = 0;

        return new short[]{r,g,b};
    }
}
