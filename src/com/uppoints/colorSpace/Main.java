package com.uppoints.colorSpace;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.uppoints.colorSpace.filter.FilterAutoContrast;
import com.uppoints.colorSpace.filter.FilterGreyScale;
import com.uppoints.colorSpace.filter.FilterReduceBright;

public class Main {
	
	/**
	 * Simple test for the Color Space
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
       
		//testYUVcreateBlackImage();
		//testYUVcreateImageFromFile();
		testYUVShowImageOnJFrame();
		
		//testLABcreateBlackImage();
		//testLABcreateImageFromFile();
		
		testLABShowImageOnJFrame();
		
    }
	
	private static void showImage(BufferedImage img, String title) {
		JFrame frame = new JFrame(title);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static public BufferedImage loadImage() throws IOException {
		//URL url = new URL("http://combiboilersleeds.com/images/vivid/vivid-6.jpg") ;
		URL url = new URL("http://www.fotoblur.com/imgs/0/0/3/0/2/8/6/432429.jpg") ;
		
		System.out.println("-- loading image: "+ url);
		BufferedImage image = ImageIO.read(url) ;
		System.out.println("-- Image loaded: "+ image);
		return image ;
	}
	
	/**
	 * Creates YUV image from file, converts to RGB and save it
	 * @throws IOException
	 */
	public static void testYUVcreateImageFromFile() throws IOException{
		BufferedImage img = loadImage() ;
		
		ImageYUV imageYUV = new ImageYUV(img) ;
		
		ImageIO.write(imageYUV.getBufferedImage(), "PNG", new File("/tmp/test-save.png")) ;
	}
	
	/**
	 * Creates YUV image with black color, converts to RGB and save it
	 * @throws IOException 
	 */
	public static void testYUVcreateBlackImage() throws IOException{
		ImageYUV imageYUV = new ImageYUV(128, 128) ;
		ImageIO.write(imageYUV.getBufferedImage(), "PNG", new File("/tmp/test-save.png")) ;
	}
	
	/**
	 * Create ImageLAB Object, converts to BufferedImage and show it on a JFrame
	 * @throws IOException
	 */
	public static void testYUVShowImageOnJFrame() throws IOException{
		
		BufferedImage image = loadImage() ;
		
		ImageYUV imageYUV = new ImageYUV(image) ;
		
		//new FilterGreyScale(imageYUV).applyFilter();
		
		//new FilterAutoContrast(imageYUV).applyFilter();
		
		new FilterReduceBright(imageYUV,0.5f).applyFilter(); 
		
		BufferedImage img = imageYUV.getBufferedImage();
		
		showImage(img,"YUV");
	}

	
	/**
	 * Creates LAB image from file, converts to RGB and save it
	 * @throws IOException
	 */
	public static void testLABcreateImageFromFile() throws IOException{
		BufferedImage img = loadImage() ;
		
		ImageLAB imageLAB = new ImageLAB(img) ;
		
		ImageIO.write(imageLAB.getBufferedImage(), "PNG", new File("/tmp/test-save.png")) ;
	}
	
	/**
	 * Creates LAB image with black color, converts to RGB and save it
	 * @throws IOException 
	 */
	public static void testLABcreateBlackImage() throws IOException{
		ImageLAB imageLAB = new ImageLAB(128, 128) ;
		ImageIO.write(imageLAB.getBufferedImage(), "PNG", new File("/tmp/test-save.png")) ;
	}
	
	/**
	 * Create ImageLAB Object, converts to BufferedImage and show it on a JFrame
	 * @throws IOException
	 */
	public static void testLABShowImageOnJFrame() throws IOException{
		
		BufferedImage image = loadImage() ;
		ImageLAB imageLAB = new ImageLAB(image) ;
		BufferedImage img = imageLAB.getBufferedImage();
		
		showImage(img,"LAB");
	}
	
}
