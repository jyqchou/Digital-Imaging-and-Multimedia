import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import java.util.Random;
import java.util.Arrays;

public class Histogram_Specification implements PlugIn {
	public static int[] Piecewise(int[] histogram, double[][] List){

		int n = List[0].length + 1;
		int k = 256;

		// Create cdf
		int j = 0;

		for (int w=0; w<k; w++){
			j = j + histogram[w];
		}
		double[] cdf = new double[k];
		int c = 0;

		for (int w=0; w<k; w++){
			c = c + histogram[w];
			cdf[w] = (double)c/j;
		}

		int[] map = new int[k];
		int a, m;
		double b;

		for (int i=0; i<k; i++){
			b = cdf[i];
			if(b<=cdf[0]){
				a = 0;
			} else if(b>=1) {
				a = k-1;
			} else {
				m = n - 1;
				while (m>0 && List[m][1]>b){
					m = m-1;
				}
				a = (int)(List[m][0] + (b-List[m][1])*(List[m+1][0] - List[m][0])/(List[m+1][1]-List[m][1]));
			}
			map[i] = a;
		}
		return map;
	}

	public void run(String arg) {
		ImagePlus im = IJ.getImage();
		ImageProcessor ip = im.getProcessor();
		int M = ip.getWidth();
		int N = ip.getHeight();
		int hist[] = new int[256];
		double list[][] = new double[6][2];

		list[0][0] = 0;
		list[0][1] = 0.1;
		list[1][0] = 50;
		list[1][1] = 0.15;
		list[2][0] = 110;
		list[2][1] = 0.3;
		list[3][0] = 170;
		list[3][1] = 0.7;
		list[4][0] = 220;
		list[4][1] = 0.95;
		list[5][0] = 255;
		list[5][1] = 1;

		int p=0;
		for (int i=0; i<M; i++){
			for (int j=0; j<N; j++){
				p = ip.getPixel(i,j);
				hist[p] ++;
			}
		}

		int map[] = Piecewise(hist, list);
		for (int u=0; u<M;u++){
			for (int v=0; v<N; v++){
				p = ip.getPixel(u,v);
				ip.set(u,v,map[p]);
			}
		}
		im.updateAndDraw();
	}
}
