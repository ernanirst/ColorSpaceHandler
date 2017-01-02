package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

public class ImageLAB extends ImageAbstract<Float> {

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
	public ImageLAB(int width, int height, Float l, Float a, Float b){
		super(width, height) ;
		
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
		this(width, height, new Float(0), new Float(0), new Float(0));
	}
	
	/**
	 * Initialize LAB image from an array of RGB pixels with a specific size
	 * @param pixelsRGB array of RGB pixels, the position of each pixel can be defined from the width of the image
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageLAB(int[] pixelsRGB, int width, int height) {
		super(width, height) ;
		
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
	@Override
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
	
	@Override
	public Float get(int ch, int x, int y){
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
	
	@Override
	public void set(int x, int y, Float L, Float A, Float B){
		int i =	getPixelIndex(x, y);
		pixelsL[i] = L;
		pixelsA[i] = A;
		pixelsB[i] = B;
	}
	
	@Override
	public void set(int ch, int x, int y, Float chValue){
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

	@Override
	public int getRGB(int x, int y) {
		int i =	getPixelIndex(x, y);		
		float[] bufferRGB = new float[3];
		return ImageHelper.fromLABtoRGB(pixelsL[i], pixelsA[i], pixelsB[i], bufferRGB);
	}

	@Override
	public short getRGB(int ch, int x, int y) {
		int i =	getPixelIndex(x, y);		
		float[] bufferRGB = new float[3];
		
		int rgb = ImageHelper.fromLABtoRGB(pixelsL[i], pixelsA[i], pixelsB[i], bufferRGB);
		int r = (rgb >> 16) & 0x000000ff ;
		int g = (rgb >> 8) & 0x000000ff ;
		int b = rgb & 0x000000ff ;
		
		switch(ch){
			case 1:
				return (short)r ;
			case 2:
				return (short)g ;
			case 3:
				return (short)b ;
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}	
	}

	@Override
	public void setRGB(int ch, int x, int y, short rgbValue) {
		int i =	getPixelIndex(x, y);
		
		float[] xyzBuffer = new float[3];		
		int rgb = ImageHelper.fromLABtoRGB(pixelsL[i], pixelsA[i], pixelsB[i], xyzBuffer);
		int r = (rgb >> 16) & 0x000000ff ;
		int g = (rgb >> 8) & 0x000000ff ;
		int b = rgb & 0x000000ff ;
		
		switch(ch){
			case 1:
				r = rgbValue;
				break;
			case 2:
				g = rgbValue;
				break;
			case 3:
				b = rgbValue;
				break;
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}
		
		float[] retLAB = new float[3];		
		ImageHelper.fromRGBtoLAB(r, g, b, retLAB);
		
		pixelsL[i] = (short)retLAB[0];
		pixelsA[i] = (short)retLAB[1];
		pixelsB[i] = (short)retLAB[2];
	}

	@Override
	public void setRGB(int x, int y, int rgb) {
		int i =	getPixelIndex(x, y);
		
		int r = (rgb >> 16) & 0x000000ff ;
		int g = (rgb >> 8) & 0x000000ff ;
		int b = rgb & 0x000000ff ;
		float[] retLAB = new float[3];
		ImageHelper.fromRGBtoLAB(r, g, b, retLAB);
		
		pixelsL[i] = (short)retLAB[0];
		pixelsA[i] = (short)retLAB[1];
		pixelsB[i] = (short)retLAB[2];
	}
	
}
