package FiltersHere.Filters;

import Interfaces.PixelFilter;
import core.DImage;


public class Saturate implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        for(int row = 0; row < red.length; row++) {
            for(int col = 0; col < red[0].length; col++) {
                short[] color = saturate(red[row][col], green[row][col], blue[row][col]);
                red[row][col] = color[0];
                green[row][col] = color[1];
                blue[row][col] = color[2];
            }
        }

        img.setColorChannels(red,green,blue);
        return img;
    }

    private short[] saturate(short r, short g, short b) {
        short min = (short) Math.min(r, Math.min(g, b));
        r-=min; g-=min; b-=min;

        r = (short) (Math.round((double)(r)/255.0)*255);
        g = (short) (Math.round((double)(g)/255.0)*255);
        b = (short) (Math.round((double)(b)/255.0)*255);


        return new short[]{r,g,b};
    }
}
