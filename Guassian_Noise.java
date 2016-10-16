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

public class Guassian_Noise implements PlugIn {
	public void run(String arg) {
		ImagePlus im = IJ.getImage();

		ImageProcessor ip = im.getProcessor();

		int M = ip.getWidth();
		int N = ip.getHeight();
		int p=0;

		Random r = new Random();
		for (int u=0; u<M;u++){
			for (int v=0; v<N; v++){
				p = ip.getPixel(u,v);
				ip.set(u,v, p + (int)(r.nextGaussian()*25));
			}
		}
		im.updateAndDraw();
	}
}
