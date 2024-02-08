package FiltersHere.Filters;

import Interfaces.PixelFilter;
import core.DImage;


public class Convolution implements PixelFilter {
    private Kernel kernel;
    public Convolution() throws Exception {
        kernel = new Kernel(new double[][]{
                {-1,-1,-1},
                {-1,8,-1},
                {-1,-1,-1}});
    }

    public Convolution(double[][] kernel) throws Exception {
        this.kernel = new Kernel(kernel);
    }

    public void setKernel(double[][] newKernel) throws Exception {
        kernel.setKernel(newKernel);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = runConvolution( img.getRedChannel());
        short[][] green = runConvolution(img.getGreenChannel());
        short[][] blue = runConvolution(img.getBlueChannel());

        img.setColorChannels(runConvolution(red),runConvolution(green),runConvolution(blue));
        //img.setPixels(runConvolution(img.getBWPixelGrid()));
        return img;

    }


    public short[][] runConvolution(short[][] channel) {
        short[][] newChannel = copyArray(channel);

        for(int row = (int) ((kernel.getSize())/2); row+(int) ((kernel.getSize())/2) < channel.length; row++) {
            for(int col = (int) ((kernel.getSize())/2); col+(int) ((kernel.getSize())/2) < channel[0].length; col++) {

                newChannel[row][col] = findNewVal(channel, row, col);

            }
        }

        return newChannel;
    }

    private short[][] copyArray(short[][] channel) {
        short[][] newChannel = new short[channel.length][channel[0].length];
        for (int i = 0; i < channel.length; i++) {
            System.arraycopy(channel[i], 0, newChannel[i], 0, channel[i].length);
        }
        return newChannel;
    }

    public short findNewVal(short[][] channel, int row, int col) {
        double out = 0;

        for(int kernelR = -(int) ((kernel.getSize())/2.0); kernelR <= (int) ((kernel.getSize())/2.0); kernelR++) {
            for(int kernelC = -(int) ((kernel.getSize())/2.0); kernelC <= (int) ((kernel.getSize())/2.0); kernelC++) {

                out += (kernel.getVal(kernelR+(int) ((kernel.getSize())/2.0),kernelC+(int) ((kernel.getSize())/2.0))
                        * channel[row+kernelR][col+kernelC]);
            }
        }

        out /= (kernel.getTotalWeight()*1.0);

        out = clipColor(out);
        return (short) out;
    }

    private static double clipColor(double in) {
        if(in < 0) in = 0;
        if(in > 255) in = 255;
        return in;
    }
}
