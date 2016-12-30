package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

public class ImageLAB {
	
	private int width ;
	private int height ;
	private short[] pixelsL ;
	private short[] pixelsA ;
	private short[] pixelsB ;
	
	/**
	 * Initialize LAB image with specified size and LAB components
	 * @param width image's width
	 * @param height image's height
	 * @param l L component of LAB image
	 * @param a A component of LAB image
	 * @param b B component of LAB image
	 */
	public ImageLAB(int width, int height, int l, int a, int b){
		this.width = width;
		this.height = height;
		
		int totalPixels = width * height;
		this.pixelsL = new short[totalPixels];
		this.pixelsA = new short[totalPixels];
		this.pixelsB = new short[totalPixels];
		
		ImageHelper.setArray(this.pixelsL, (short)l);
		ImageHelper.setArray(this.pixelsA, (short)a);
		ImageHelper.setArray(this.pixelsB, (short)b);
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
		short[] pl = new short[totalPixels];
		short[] pa = new short[totalPixels];
		short[] pb = new short[totalPixels];
		
		float[] retLAB = new float[3];
		
		for (int i = 0; i < totalPixels; i++) {
			
			// convert to LAB
			int rgb = pixelsRGB[i];
			int r = (rgb >> 16) & 0x000000ff ;
			int g = (rgb >> 8) & 0x000000ff ;
			int b = rgb & 0x000000ff ;
			ImageHelper.fromRGBtoLAB(r, g, b, retLAB);
			
			// set pixels
			pl[i] = (short)retLAB[0];
			pa[i] = (short)retLAB[1];
			pb[i] = (short)retLAB[2];
			
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
	 * Get L component of a pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a short with the value of the L component
	 */
	public short getL(int x, int y) {
		int i = getPixelIndex(x, y);
		return pixelsL[i];
	}

	/**
	 * Get A component of a pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a short with the value of the A component
	 */
	public short getA(int x, int y) {
		int i = getPixelIndex(x, y);
		return pixelsA[i];
	}
	
	/**
	 * Get B component of a pixel given its position
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a short with the value of the B component
	 */
	public short getB(int x, int y) {
		int i = getPixelIndex(x, y);
		return pixelsB[i];
	}
	
}
