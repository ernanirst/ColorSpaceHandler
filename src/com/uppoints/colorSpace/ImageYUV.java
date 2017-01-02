package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

public class ImageYUV extends ImageAbstract<Short> {

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
		super(width, height) ;
		
		int totalPixels = width * height;
		this.pixelsY = new short[totalPixels];
		this.pixelsU = new short[totalPixels];
		this.pixelsV = new short[totalPixels];
		
		ImageHelper.setArray(this.pixelsY, (short)y);
		ImageHelper.setArray(this.pixelsU, (short)u);
		ImageHelper.setArray(this.pixelsV, (short)v);
	}
	
	/**
	 * Initialize YUV image with specified size and with black color
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageYUV(int width, int height) {
		this(width, height, 0, 128, 128);
	}
	
	/**
	 * Initialize YUV image from an array of RGB pixels with a specific size
	 * @param pixelsRGB array of RGB pixels, the position of each pixel can be defined from the width of the image
	 * @param width image's width
	 * @param height image's height
	 */
	public ImageYUV(int[] pixelsRGB, int width, int height) {
		super(width, height) ;
		
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
	 * Initialize YUV image from a BufferedImage
	 * @param img BufferedImage image
	 */
	public ImageYUV(BufferedImage img) {
		this(ImageHelper.grabPixels(img), img.getWidth(), img.getHeight());
	}
	
	/**
	 * Convert YUV image to a RGB image
	 * @return an array of RGB pixels
	 */
	@Override
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
	
	@Override
	public Short get(int ch, int x, int y){
		int i = getPixelIndex(x, y);
		switch(ch){
			case 1:
				return pixelsY[i];
			case 2:
				return pixelsU[i];
			case 3:
				return pixelsV[i];
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}
	}
	
	@Override
	public void set(int x, int y, Short Y, Short U, Short V) {
		int i =	getPixelIndex(x, y);
		pixelsY[i] = Y;
		pixelsU[i] = U;
		pixelsV[i] = V;
	}
	
	@Override
	public void set(int ch, int x, int y, Short chValue){
		int i =	getPixelIndex(x, y);
		switch(ch){
			case 1:
				pixelsY[i] = chValue;
			case 2:
				pixelsU[i] = chValue;
			case 3:
				pixelsV[i] = chValue;
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}
	}

	@Override
	public int getRGB(int x, int y) {
		int i =	getPixelIndex(x, y);

		int[] retRGB = new int[3];		
		ImageHelper.fromYUVtoRGB(pixelsY[i], pixelsU[i], pixelsV[i], retRGB);
		int r = retRGB[0] ;
		int g = retRGB[1] ;
		int b = retRGB[2] ;
	
		return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF) ;
	}

	@Override
	public short getRGB(int ch, int x, int y) {
		int i =	getPixelIndex(x, y);

		int[] retRGB = new int[3];		
		ImageHelper.fromYUVtoRGB(pixelsY[i], pixelsU[i], pixelsV[i], retRGB);
		int r = retRGB[0] ;
		int g = retRGB[1] ;
		int b = retRGB[2] ;
		
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
		
		int[] retRGB = new int[3];		
		ImageHelper.fromYUVtoRGB(pixelsY[i], pixelsU[i], pixelsV[i], retRGB);
		
		switch(ch){
			case 1:
				retRGB[0] = rgbValue;
				break;
			case 2:
				retRGB[1] = rgbValue;
				break;
			case 3:
				retRGB[2] = rgbValue;
				break;
			default:
				throw new UnsupportedOperationException("Unknown channel: " + ch);
		}
		
		int[] retYUV = new int[3];		
		ImageHelper.fromRGBtoYUV(retRGB[0],retRGB[1],retRGB[2], retYUV);
		
		pixelsY[i] = (short)retYUV[0];
		pixelsU[i] = (short)retYUV[1];
		pixelsV[i] = (short)retYUV[2];
	}

	@Override
	public void setRGB(int x, int y, int rgb) {
		int i =	getPixelIndex(x, y);
		
		// convert to YUV
		int r = (rgb >> 16) & 0x000000ff ;
		int g = (rgb >> 8) & 0x000000ff ;
		int b = rgb & 0x000000ff ;
		int[] retYUV = new int[3];
		ImageHelper.fromRGBtoYUV(r, g, b, retYUV);
		
		// set channels
		pixelsY[i] = (short)retYUV[0];
		pixelsU[i] = (short)retYUV[1];
		pixelsV[i] = (short)retYUV[2];
	}
	
}