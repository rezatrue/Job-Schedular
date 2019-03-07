package application;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ActivityHandeler {
	private String imageFileName;
	private String userId;
	
	public ActivityHandeler() {
	}
	
	//stackoverflow.com/questions/58305/is-there-a-way-to-take-a-screenshot-using-java-and-save-it-to-some-sort-of-image
	public void captureScreen(String fileName) throws Exception {
		System.out.println("Screenshot has been taken");
		this.imageFileName = fileName;
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Rectangle screenRectangle = new Rectangle(screenSize);
	   Robot robot = new Robot();
	   BufferedImage image = robot.createScreenCapture(screenRectangle);
	   ImageIO.write(image, "png", new File(fileName + ".png"));
       //ImageIO.write(screenShot, "JPG", new File("d:\\"+formatter.format(now.getTime())+".jpg"));
	}
	
	public void storeActivity(int keyCount, int mouseCount) {
		System.out.println("Key count : " + keyCount + "Mouse count : " + mouseCount + " Image : " + imageFileName );
		
	}
	
	
	
}
