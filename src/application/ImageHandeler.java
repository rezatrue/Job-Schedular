package application;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;


public class ImageHandeler {
	private String type = "png";
	
	public ImageHandeler() {
	}
	
	//stackoverflow.com/questions/58305/is-there-a-way-to-take-a-screenshot-using-java-and-save-it-to-some-sort-of-image
	//stackoverflow.com/questions/2781545/how-can-i-convert-an-image-to-a-base64-string-using-java
	//grokonez.com/java/java-advanced/java-8-encode-decode-an-image-base64
	public String captureScreen() {
		String base64Image = "";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		   Rectangle screenRectangle = new Rectangle(screenSize);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

		   try {
			   Robot robot = new Robot();
			   BufferedImage image = robot.createScreenCapture(screenRectangle);
			ImageIO.write(image, type , bos);
			byte imageData[] = bos.toByteArray();
			base64Image = Base64.getEncoder().encodeToString(imageData);
		   }catch (Exception e) {
			   System.out.println(e.getMessage());
		   }
		 return base64Image;
		}

	
}
