package FiltersHere;


import FiltersHere.Filters.*;
import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;
import java.util.Arrays;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.UP;

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
    ColorReduction fourColors;
    Isolate isolator;


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


        colorReducer = new ColorReduction(50);
        colorMasker = new ColorMasking();
        saturator = new Saturate(.2);
        //fourColors = new ColorReduction(4);
        //isolator = new Isolate(-.1);

//        ballCenterRows = calculateBallCenterRows(colorMasker);
    }

//    private ArrayList calculateBallCenterRows(ColorMasking colorMasker) {
//
//    }

    @Override
    public DImage processImage(DImage img) {
            img = blur.processImage(img);

        //saturator.setX(.3);
        //img = saturator.processImage(img);

        img = colorReducer.processImage(img);
        reducedImage = img;
        img = blur.processImage(img);
        img = colorReducer.processImage(img);


        if(!mask) return img;
       // img = isolator.processImage(img);

       img = saturator.processImage(img);




        return img;
    }

//    private void updateColors() {
//        red = getColor(colorReducer.findClosestCluster((short) 255, (short) 0, (short) 0));
//        green = getColor(colorReducer.findClosestCluster((short)0, (short)255, (short)0));
//        blue = getColor(colorReducer.findClosestCluster((short)0, (short)0, (short)255));
//        yellow = getColor(colorReducer.findClosestCluster((short)255, (short)255, (short)0));
//        colors = new short[][]{red, green, blue, yellow};
//    }

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
        if(key == ' ') {
            mask = !mask;
            System.out.println("space");
        }

        if(key == 'R') {red = getColor(colorReducer.findClosestCluster((short) 255, (short) 0, (short) 0));}
        if(key == 'g') {green = getColor(colorReducer.findClosestCluster((short) 0, (short) 255, (short) 0));
            System.out.println(Arrays.toString(green));}
        if(key == 'B') blue = getColor(colorReducer.findClosestCluster((short) 0, (short) 0, (short) 255));
        if(key == 'Y') yellow = getColor(colorReducer.findClosestCluster((short) 255, (short) 255, (short) 0));


        if(keyCode == UP) colorReducer.increaseClusters();
        if(keyCode == DOWN) colorReducer.decreaseClusters();

        colorReducer.keyPressed(key,keyCode);
    }
}
