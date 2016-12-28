package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
	
	/**
	 * Simple test for the Color Space
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
       
		File file = new File("/home/krashnovr/Imagens/web-dev-meme.jpeg") ;
		
		BufferedImage img = ImageIO.read(file) ;
		
		ImageYUV imageYUV = new ImageYUV(img) ;
		
		ImageIO.write(imageYUV.getBufferedImage(), "PNG", new File("/tmp/test-save.png")) ;
		
    }
}
