package application;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import Decoder.BASE64Encoder;

public class ActivityHandeler {
	private static String type = "png";
	//private String userId;
	
	public ActivityHandeler() {
	}
	
	
	//stackoverflow.com/questions/58305/is-there-a-way-to-take-a-screenshot-using-java-and-save-it-to-some-sort-of-image
	//stackoverflow.com/questions/2781545/how-can-i-convert-an-image-to-a-base64-string-using-java
	public String captureScreen() throws Exception {
		System.out.println("Screenshot has been taken");
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Rectangle screenRectangle = new Rectangle(screenSize);
	   Robot robot = new Robot();
	   BufferedImage image = robot.createScreenCapture(screenRectangle);
	   
	   String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
	
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
			
			bos.close();
			} catch (IOException e) {
			 e.printStackTrace();
			}
		return imageString;
	   //ImageIO.write(image, "png", new File(fileName + ".png"));
       //ImageIO.write(screenShot, "JPG", new File("d:\\"+formatter.format(now.getTime())+".jpg"));
	}
	
	//grokonez.com/java/java-advanced/java-8-encode-decode-an-image-base64
	public static String encoder() {
		String base64Image = "";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		   Rectangle screenRectangle = new Rectangle(screenSize);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

		   try {
			   Robot robot = new Robot();
			   BufferedImage image = robot.createScreenCapture(screenRectangle);
			ImageIO.write(image, "png", bos);
			byte imageData[] = bos.toByteArray();
			base64Image = Base64.getEncoder().encodeToString(imageData);
		   }catch (Exception e) {
			   System.out.println(e.getMessage());
		   }
		 return base64Image;
		}

	
}
