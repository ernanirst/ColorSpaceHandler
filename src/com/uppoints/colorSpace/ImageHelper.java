package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

public abstract class ImageHelper {
	
	/**
	 * Convert a YUV image to a RGB image
	 * @param y Y component of YUV image
	 * @param u U component of YUV image
	 * @param v V component of YUV image
	 * @param retRGB array that will contain the RGB pixels
	 */
	static public void fromYUVtoRGB(int y, int u, int v, int[] retRGB) {
		y -= 16;
		u -= 128;
		v -= 128;
		int r = clip((298 * y + 409 * v + 128) >> 8);
		int g = clip((298 * y - 100 * u - 208 * v + 128) >> 8);
		int b = clip((298 * y + 516 * u + 128) >> 8);
        retRGB[0] = r ;
        retRGB[1] = g ;
        retRGB[2] = b ;
	}
	
	/**
	 * Makes sure that a given pixel is inside the YUV range
	 * @param i pixel
	 * @return pixel's value if it's inside the range, or its new value
	 */
	static public int clip(int i) {
		return i < 0 ? 0 : (i > 255 ? 255 : i);
	}

	/**
	 * Converts a RGB image to a YUV image
	 * @param r R component of a RGB image
	 * @param g G component of a RGB image
	 * @param b B component of a RGB image
	 * @param retYUV array that will contain the YUV pixels
	 */
	static public void fromRGBtoYUV(int r, int g, int b, int[] retYUV) {
		int y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
		int u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
		int v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;
		retYUV[0] = (y < 0 ? 0 : (y > 255 ? 255 : y));
		retYUV[1] = (u < 0 ? 0 : (u > 255 ? 255 : u));
		retYUV[2] = (v < 0 ? 0 : (v > 255 ? 255 : v));	
	}
	
	/**
	 * Set all elements of an array with a given value
	 * @param a array that will receive new values
	 * @param v new value
	 */
	static public void setArray(short[] a, short v) {
		int totalPixels = a.length;
		for (int i = 0; i < totalPixels; i++) {
			a[i] = v;
		}
	}

	/**
	 * Creates a BufferedImage from an array of RGB pixels
	 * @param pixels array of RGB pixels
	 * @param w image's width
	 * @param h image's height
	 * @return created BufferedImage
	 */
	static public BufferedImage createImage(int[] pixels, int w, int h) {
		BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		buffImg.setRGB(0, 0, w, h, pixels, 0, w);
		return buffImg;
	}

	/**
	 * Get RGB pixels of a BufferedImage
	 * @param bufImg image
	 * @return an array of RGB pixels
	 */
	static public int[] grabPixels(BufferedImage bufImg) {
		int w = bufImg.getWidth();
		int h = bufImg.getHeight();
		int[] rgbArray = new int[w * h];
		bufImg.getRGB(0, 0, w, h, rgbArray, 0, w);
		return rgbArray;
	}
	
}
