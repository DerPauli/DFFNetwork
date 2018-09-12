package util;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * 
 * @author Paul Merker
 * @version 1.0.1
 * This class reads in an image, extracts the pixels and converts it into input for the NN
 *
 */
public class BufferedReader {

	protected File f;
	protected BufferedImage img;
	
	private int width;
	private int height;
	
	public void readImage(String path) throws IOException {
		try {
			
	      this.f = new File(path);
	      this.img = ImageIO.read(f);
	      
	      this.width = img.getWidth();
	      this.height = img.getHeight();
	      
	    } catch(IOException e){
	      System.out.println(e);
	    }
	}
	
	public float[] getPixelArray() {
		float p[] = new float[this.height * this.width];
		
		for(int i = 0; i < this.width; ++i) {
			for(int j = 0; j < this.height; ++j) {
				p[j + i * this.height] = this.img.getRGB(i, j);
			}
		}
		
		return p;
	}
}
