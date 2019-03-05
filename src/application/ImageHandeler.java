package application;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageHandeler {

	public ImageHandeler() {
	}
	
	public void takeScreenShot() {
		System.out.println("Screenshot has been taken");
	}

	public void captureScreen(String fileName) throws Exception {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Rectangle screenRectangle = new Rectangle(screenSize);
	   Robot robot = new Robot();
	   BufferedImage image = robot.createScreenCapture(screenRectangle);
	   ImageIO.write(image, "png", new File(fileName + ".png"));
       //ImageIO.write(screenShot, "JPG", new File("d:\\"+formatter.format(now.getTime())+".jpg"));
	}
	
	
}
