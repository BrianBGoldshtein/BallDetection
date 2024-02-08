package FiltersHere;


import FiltersHere.Filters.*;
import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class Main implements PixelFilter, Interactive {

    boolean mask = true;

    public static short[] blue = {0,0,255};
    public static short[] green = {0,255,0};
    public static short[] red = {255, 0, 0};
    public static short[] yellow = {255,255,0};
    public static short[][] colors = {red, green, blue, yellow};


    Convolution blur;
    ColorReduction colorReducer;

    ColorMasking colorMasker;
    Saturate saturator;

    ArrayList ballCenterRows = new ArrayList<>();

    public Main() throws Exception {
        blur = new Convolution(new double[][]{{1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1}
        });


        colorReducer = new ColorReduction(6);
        colorMasker = new ColorMasking();
        saturator = new Saturate();

//        ballCenterRows = calculateBallCenterRows(colorMasker);
    }

//    private ArrayList calculateBallCenterRows(ColorMasking colorMasker) {
//
//    }

    @Override
    public DImage processImage(DImage img) {

            img = blur.processImage(img);
        img = saturator.processImage(img);
        if(!mask) return img;


        img = colorReducer.processImage(img);
        reducedImage = img;
        updateColors();
        img = saturator.processImage(img);




        return img;
    }

    private void updateColors() {
        red = getColor(colorReducer.findClosestCluster((short) 255, (short) 0, (short) 0));
        green = getColor(colorReducer.findClosestCluster((short)0, (short)255, (short)0));
        blue = getColor(colorReducer.findClosestCluster((short)0, (short)0, (short)255));
        yellow = getColor(colorReducer.findClosestCluster((short)255, (short)255, (short)0));
        colors = new short[][]{red, green, blue, yellow};
    }

    DImage reducedImage;

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {

    }


    public short[] getColor(Cluster cluster) {
        short[] out = new short[3];
        out[0] = cluster.getR();
        out[1] = cluster.getG();
        out[2] = cluster.getB();
        return out;
    }

    @Override
    public void keyPressed(char key, int keyCode) {
        if(key == ' ') mask = !mask;
        colorReducer.keyPressed(key,keyCode);
    }
}
