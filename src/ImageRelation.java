import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
class ImageRelation{
	public static BufferedImage ImageLoad(String filePath) throws Exception {
	    BufferedImage image = null;
	    File imgFile = new File(filePath);
	    if (!imgFile.exists()) {
	        return null;
	    }
	
	    image = ImageIO.read(imgFile);
	  
	    int width = image.getWidth();
	    int height = image.getHeight();
	    BufferedImage rgbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    for (int x=0; x<width; x++) {
	        for (int y=0; y<height; y++) {
	            rgbImage.setRGB(x, y, image.getRGB(x, y));
	        }
	    }
	  
	     return rgbImage;
	}
}