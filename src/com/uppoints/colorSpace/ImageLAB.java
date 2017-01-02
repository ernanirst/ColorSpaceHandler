package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

public class ImageLAB {
	
	private int width ;
	private int height ;
	private float[] pixelsL ;
	private float[] pixelsA ;
	private float[] pixelsB ;
	
	/**
	 * Initialize LAB image with specified size and LAB components
	 * @param width image's width
	 * @param height image's height
	 * @param l L component of LAB image
	 * @param a A component of LAB image
	 * @param b B component of LAB image
	 */
	public ImageLAB(int width, int height, float l, float a, float b){
		this.width = width;
		this.height = height;
		
		int totalPixels = width * height;
		this.pixelsL = new float[totalPixels];
		this.pixelsA = new float[totalPixels];
		this.pixelsB = new float[totalPixels];
		
		ImageHelper.setArray(this.pixelsL, l);
		ImageHelper.setArray(this.pixelsA, a);
		ImageHelper.setArray(this.pixelsB, b);
	}
	
	/**
	 * Initialize LAB image with specified size and with black color
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageLAB(int width, int height) {
		this(width, height, 0, 0, 0);
	}
	
	/**
	 * Initialize LAB image from an array of RGB pixels with a specific size
	 * @param pixelsRGB array of RGB pixels, the position of each pixel can be defined from the width of the image
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageLAB(int[] pixelsRGB, int width, int height) {
		this.width = width;
		this.height = height;
		
		int totalPixels = width * height;
		float[] pl = new float[totalPixels];
		float[] pa = new float[totalPixels];
		float[] pb = new float[totalPixels];
		
		float[] retLAB = new float[3];
		
		for (int i = 0; i < totalPixels; i++) {
			
			// convert to LAB
			int rgb = pixelsRGB[i];
			int r = (rgb >> 16) & 0x000000ff ;
			int g = (rgb >> 8) & 0x000000ff ;
			int b = rgb & 0x000000ff ;
			ImageHelper.fromRGBtoLAB(r, g, b, retLAB);
			
			// set pixels
			pl[i] = retLAB[0];
			pa[i] = retLAB[1];
			pb[i] = retLAB[2];
			
		}
		
		this.pixelsL = pl;
		this.pixelsA = pa;
		this.pixelsB = pb;		
		
	}
	
	/**
	 * Initialize LAB image from a BufferedImage
	 * @param img BufferedImage image
	 */
	public ImageLAB(BufferedImage img) {
		this(ImageHelper.grabPixels(img), img.getWidth(), img.getHeight());
	}
	
	/**
	 * Convert LAB image to a RGB image
	 * @return an array of RGB pixels
	 */
	public int[] getPixelsRGB() {
		int totalPixels = width * height;
		int[] pixelsRGB = new int[totalPixels];
		float[] bufferRGB = new float[3];
		
		for (int i = 0; i < totalPixels; i++) {
			int rgb = ImageHelper.fromLABtoRGB(pixelsL[i], pixelsA[i], pixelsB[i], bufferRGB);
			pixelsRGB[i] = rgb ; 
		}
		
		return pixelsRGB ;
	}
	
	// getter and setters
	
	/**
	 * Create a BufferedImage from the LAB image
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
	 * @return an integer compound of the LAB components on each byte
	 */
	private int getPixelIndex(int x, int y) {
		return (width * y) + x;
	}
	
	/**
	 * Get a pixel given its position and channel
	 * @param ch pixel's channel
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a pixel with the value of the chosen channel
	 */
	public float getChannelPixel(int ch, int x, int y){
		int i = getPixelIndex(x, y);
		switch(ch){
			case 1:
				return pixelsL[i];
			case 2:
				return pixelsA[i];
			case 3:
				return pixelsB[i];
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}
	}
	
	/**
	 * Set new value to LAB components of a specific pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @param L image's L component
	 * @param A image's A component
	 * @param B image's B component
	 */
	public void setLAB(int x, int y, short L, short A, short B){
		int i =	getPixelIndex(x, y);
		pixelsL[i] = L;
		pixelsA[i] = A;
		pixelsB[i] = B;
	}
	
	/**
	 * Set new value a pixel given its position and channel
	 * @param ch pixel's channel
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @param chValue value of the chosen channel
	 */
	public void setChannel(int ch, int x, int y, short chValue){
		int i =	getPixelIndex(x, y);
		switch(ch){
			case 1:
				pixelsL[i] = chValue;
			case 2:
				pixelsA[i] = chValue;
			case 3:
				pixelsB[i] = chValue;
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}
	}
	
}
