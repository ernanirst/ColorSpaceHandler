package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

import com.uppoints.colorSpace.ImageHelper;

public class ImageYUV {

	private int width ;
	private int height ;
	private short[] pixelsY ;
	private short[] pixelsU ;
	private short[] pixelsV ;
	
	/**
	 * Initialize YUV image with specified size and YUV components
	 * @param width image's width
	 * @param height image's height
	 * @param y Y component of YUV image
	 * @param u U component of YUV image
	 * @param v V component of YUV image
	 */
	public ImageYUV(int width, int height, int y, int u, int v) {
		this.width = width;
		this.height = height;
		
		int totalPixels = width * height;
		this.pixelsY = new short[totalPixels];
		this.pixelsU = new short[totalPixels];
		this.pixelsV = new short[totalPixels];
		
		ImageHelper.setArray(this.pixelsY, (short)y);
		ImageHelper.setArray(this.pixelsU, (short)u);
		ImageHelper.setArray(this.pixelsV, (short)v);
	}
	
	/**
	 * Initialize YUV image with specified with black background
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageYUV(int width, int height) {
		this(width, height, 0, 128, 128);
	}

	/**
	 * Initialize YUV image from a BufferedImage
	 * @param img BufferedImage image
	 */
	public ImageYUV(BufferedImage img) {
		this(ImageHelper.grabPixels(img), img.getWidth(), img.getHeight());
	}
	
	/**
	 * Initialize YUV image from an array of RGB pixels with a specific size
	 * @param pixelsRGB array of RGB pixels, the position of each pixel can be defined from the width of the image
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageYUV(int[] pixelsRGB, int width, int height) {
		this.width = width;
		this.height = height;
		
		int totalPixels = width * height;
		short[] py = new short[totalPixels];
		short[] pu = new short[totalPixels];
		short[] pv = new short[totalPixels];
		
		int[] retYUV = new int[3];
		
		for (int i = 0; i < totalPixels; i++) {
			
			// convert to YUV
			int rgb = pixelsRGB[i];
			int r = (rgb >> 16) & 0x000000ff ;
			int g = (rgb >> 8) & 0x000000ff ;
			int b = rgb & 0x000000ff ;
			ImageHelper.fromRGBtoYUV(r, g, b, retYUV);
			
			// set pixels
			py[i] = (short)retYUV[0];
			pu[i] = (short)retYUV[1];
			pv[i] = (short)retYUV[2];
			
		}
		
		this.pixelsY = py;
		this.pixelsU = pu;
		this.pixelsV = pv;		
		
	}
	
	/**
	 * Convert YUV image to a RGB image
	 * @return an array of RGB pixels
	 */
	public int[] getPixelsRGB() {
		int totalPixels = width * height;
		int[] pixelsRGB = new int[totalPixels];
		int[] retRGB = new int[3];
		
		for (int i = 0; i < totalPixels; i++) {
			ImageHelper.fromYUVtoRGB(pixelsY[i], pixelsU[i], pixelsV[i], retRGB);
			int r = retRGB[0] ;
			int g = retRGB[1] ;
			int b = retRGB[2] ;
		
			int rgb = ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF) ;
			
			pixelsRGB[i] = rgb ; 
		}
		
		return pixelsRGB ;
	}
	
	// getter and setters
	
	/**
	 * Create a BufferedImage from the YUV image
	 * @return a BufferedImage
	 */
	public BufferedImage getBufferedImage() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, width, height, getPixelsRGB(), 0, width);
		return img ;
	}
	
	/**
	 * Get width
	 * @return image's width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get Height
	 * @return image's height
	 */
	public int getheight() {
		return height;
	}

	/**
	 * Get a pixel given its position in the image
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return an integer compound of the YUV components on each byte
	 */
	private int getPixelIndex(int x, int y) {
		return (width * y) + x;
	}
	
	/**
	 * Get Y component of a pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a short with the value of the Y component
	 */
	public short getY(int x, int y) {
		int i = getPixelIndex(x, y);
		return pixelsY[i];
	}

	/**
	 * Get U component of a pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a short with the value of the U component
	 */
	public short getU(int x, int y) {
		int i = getPixelIndex(x, y);
		return pixelsU[i];
	}
	
	/**
	 * Get V component of a pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a short with the value of the V component
	 */
	public short getV(int x, int y) {
		int i = getPixelIndex(x, y);
		return pixelsV[i];
	}
	
	/**
	 * Give new value to YUV components of a specific pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @param Y image's Y component
	 * @param U image's U component
	 * @param V image's V component
	 */
	public void setYUV(int x, int y, short Y, short U, short V){
		int i =	getPixelIndex(x, y);
		pixelsY[i] = Y;
		pixelsU[i] = U;
		pixelsV[i] = V;
	}
	
}