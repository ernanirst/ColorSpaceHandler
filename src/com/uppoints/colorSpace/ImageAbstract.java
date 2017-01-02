package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

abstract public class ImageAbstract<T> {

	protected int width ;
	protected int height ;
	
	public ImageAbstract(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Convert to a RGB image.
	 * @return an array of RGB pixels.
	 */
	abstract public int[] getPixelsRGB() ;
	
	/**
	 * Create a BufferedImage from the YUV image.
	 * @return a BufferedImage.
	 */
	public BufferedImage getBufferedImage() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] pixelsRGB = getPixelsRGB();
		img.setRGB(0, 0, width, height, pixelsRGB, 0, width);
		return img ;
	}
	
	/**
	 * Get width.
	 * @return image's width.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get Height.
	 * @return image's height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get a pixel index (in channel array) given its position in the image (x,y).
	 * @param x pixel's horizontal axis position.
	 * @param y pixel's vertical axis position.
	 * @return an integer compound of the YUV components on each byte.
	 */
	protected int getPixelIndex(int x, int y) {
		return (width * y) + x;
	}
	
	/**
	 * Get a pixel given its channel and position.
	 * @param ch pixel's channel
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @return a pixel with the value of the chosen channel
	 */
	abstract public T get(int ch, int x, int y) ;
	
	/**
	 * Set new pixel value of an image.
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @param Y image's Y component
	 * @param U image's U component
	 * @param V image's V component
	 */
	abstract public void set(int x, int y, T C1, T C2, T C3) ;
	
	/**
	 * Set new value of a pixel channel, given its channel id and position.
	 * @param ch pixel's channel
	 * @param x pixel's horizontal axis position
	 * @param y pixel's vertical axis position
	 * @param chValue value of the chosen channel
	 */
	abstract public void set(int ch, int x, int y, T chValue) ;
	
}