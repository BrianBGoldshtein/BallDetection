package FiltersHere.Filters;

public class Kernel {
    private double[][] arr;
    public Kernel(double[][] in) throws Exception {
        setKernel(in);
    }

    public void setKernel(double[][] in) throws Exception {
        if(in.length%2 == 0){
            throw new Exception("Kernel width is even; must be odd.");
        }
        arr = in.clone();
    }

    public int getTotalWeight() {
        int sum = 0;
        for(double[] row: arr) {
            for(double val: row) {
                sum+= val;
            }
        }
        return Math.max(sum, 1);
    }

    public double getSize() {
        return arr.length;
    }
    public double getVal(int row, int col) {
        return arr[row][col];
    }
}
