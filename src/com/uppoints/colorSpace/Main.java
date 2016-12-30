package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	/**
	 * Simple test for the Color Space
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
       
		testYUVcreateBlackImage();
		//testYUVcreateImageFromFile();
		
		//testLABcreateBlackImage();
		//testLABcreateImageFromFile();
		
    }
	
	/**
	 * Creates YUV image from file, converts to RGB and save it
	 * @throws IOException
	 */
	public static void testYUVcreateImageFromFile() throws IOException{
		File file = new File("/home/krashnovr/Imagens/web-dev-meme.jpeg") ;
		
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
	 * Creates LAB image from file, converts to RGB and save it
	 * @throws IOException
	 */
	public static void testLABcreateImageFromFile() throws IOException{
		File file = new File("/home/krashnovr/Imagens/web-dev-meme.jpeg") ;
		
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
	
}
