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
import java.util.Arrays;

public class Count_Colors implements PlugIn {
	public void run(String arg) {

		ImagePlus im = IJ.getImage();
		ImageProcessor ip = im.getProcessor();

		int M = ip.getWidth();
		int N = ip.getHeight();

		int r=0;
		int g=0;
		int b=0;
		int w=0;
		int bl=0;

		int p;
		int red;
		int green;
		int blue;

		for (int u=0; u<M;u++){
			for (int v=0; v<N; v++){

				p = ip.getPixel(u,v);
				red = (p>>16)&0xff;
				green = (p>>8)&0xff;
				blue = p&0xff;

				if(red == 0 && green == 0 && blue == 0){
					bl++;
				} else if(red == 255 && green == 255 && blue == 255){
					w++;
				} else if(red == 255 && green == 0 && blue == 0){
					r++;
				} else if(red == 0 && green == 255 && blue == 0){
					g++;
				} else if(red == 0 && green == 0 && blue == 255){
					b++;
				}
			}
		}
		IJ.showMessage(r + ", " + g + ", " + b + ", " + w + ", " + bl);
	}
}
