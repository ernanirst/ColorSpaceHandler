package com.uppoints.colorSpace;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
	
	/**
	 * Creates YUV image from file, converts to RGB and save it
	 * @throws IOException
	 */
	public static void testYUVcreateImageFromFile() throws IOException{
		File file = new File("/home/krashnovr/Imagens/rainbow.png") ;
		
		BufferedImage img = ImageIO.read(file) ;
		
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
		
		File file = new File("/home/krashnovr/Imagens/web-dev-meme.jpeg") ;
		BufferedImage image = ImageIO.read(file) ;
		ImageLAB imageYUV = new ImageLAB(image) ;
		BufferedImage img = imageYUV.getBufferedImage();
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates LAB image from file, converts to RGB and save it
	 * @throws IOException
	 */
	public static void testLABcreateImageFromFile() throws IOException{
		File file = new File("/home/krashnovr/Imagens/rainbow.png") ;
		
		BufferedImage img = ImageIO.read(file) ;
		
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
		
		File file = new File("/home/krashnovr/Imagens/rainbow.png") ;
		BufferedImage image = ImageIO.read(file) ;
		ImageLAB imageLAB = new ImageLAB(image) ;
		BufferedImage img = imageLAB.getBufferedImage();
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
