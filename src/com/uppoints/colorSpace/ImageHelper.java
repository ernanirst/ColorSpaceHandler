package com.uppoints.colorSpace;

import java.awt.image.BufferedImage;

public abstract class ImageHelper {
	
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
	 * Set all elements of an array with a given value
	 * @param a array that will receive new values
	 * @param v new value
	 */
	static public void setArray(float[] a, float v) {
		int totalPixels = a.length;
		for (int i = 0; i < totalPixels; i++) {
			a[i] = v;
		}
	}
	
	//===========================================================================================
	//== YUV HELPERS ============================================================================
	
	/**
	 * Convert a YUV pixel to a RGB pixel
	 * @param y Y component of YUV pixel
	 * @param u U component of YUV pixel
	 * @param v V component of YUV pixel
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
	 * Make sure that a given pixel is inside the YUV range
	 * @param i pixel
	 * @return pixel's value if it's inside the range, or its new value
	 */
	static public int clip(int i) {
		return i < 0 ? 0 : (i > 255 ? 255 : i);
	}

	/**
	 * Converts a RGB pixel to a YUV pixel
	 * @param r R component of a RGB pixel
	 * @param g G component of a RGB pixel
	 * @param b B component of a RGB pixel
	 * @param retYUV array that will contain the YUV pixel
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
	
	//===========================================================================================
	//== RGB HELPERS ============================================================================
	static public void RGB_to_XYZ(int r, int g, int b, float[] xyz) {

		float rf = r / 255f;

		float gf = g / 255f;

		float bf = b / 255f;

		if (rf <= 0.04045f) {

			rf = rf / 12.92f;

		} else {

			rf = (float) Math.pow(((rf + 0.055f) / 1.055f), 2.4);

		}

		if (gf <= 0.04045) {

			gf = gf / 12.92f;

		} else {

			gf = (float) Math.pow(((gf + 0.055f) / 1.055f), 2.4);

		}

		if (bf <= 0.04045) {

			bf = bf / 12.92f;

		} else {

			bf = (float) Math.pow(((bf + 0.055f) / 1.055f), 2.4);

		}

		rf *= 100f;

		gf *= 100f;

		bf *= 100f;

		xyz[0] = (rf * 0.4124f) + (gf * 0.3576f) + (bf * 0.1805f);

		xyz[1] = (rf * 0.2126f) + (gf * 0.7152f) + (bf * 0.0722f);

		xyz[2] = (rf * 0.0193f) + (gf * 0.1192f) + (bf * 0.9505f);

	}
	
	
	
	static public int YUV_to_intRGB(int y, int u, int v) {

		y -= 16;

		u -= 128;

		v -= 128;

		int r = clip((298 * y + 409 * v + 128) >> 8);

		int g = clip((298 * y - 100 * u - 208 * v + 128) >> 8);

		int b = clip((298 * y + 516 * u + 128) >> 8);

		int intRGB = (r << 16) | (g << 8) | b;

		return intRGB;

	}
	
	//===========================================================================================
	//== LAB HELPERS ============================================================================
	
	// LAB constants
	static public final float LAB_MIN_VAL_L = 0;
	static public final float LAB_MIN_VAL_A = -87f;
	static public final float LAB_MIN_VAL_B = -108f;
	static public final float LAB_MIN_VAL_AB = Math.min(LAB_MIN_VAL_A, LAB_MIN_VAL_B);
	static public final float LAB_MAX_VAL_L = 100f;
	static public final float LAB_MAX_VAL_A = 99f;
	static public final float LAB_MAX_VAL_B = 95f;
	static public final float LAB_MAX_VAL_AB = Math.max(LAB_MAX_VAL_A, LAB_MAX_VAL_B);
	static public final float LAB_SCALE_VAL_L = LAB_MAX_VAL_L - LAB_MIN_VAL_L;
	static public final float LAB_SCALE_VAL_A = LAB_MAX_VAL_A - LAB_MIN_VAL_A;
	static public final float LAB_SCALE_VAL_B = LAB_MAX_VAL_B - LAB_MIN_VAL_B;
	
	/**
	 * Make sure that the L component of a LAB image is in its range
	 * @param i L component of a LAB image
	 * @return L component value on the appropriate range
	 */
	static public float clipLAB_L(float i) {
		return i < LAB_MIN_VAL_L ? 0 : (i > LAB_MAX_VAL_L ? LAB_MAX_VAL_L : i);
	}

	/**
	 * Make sure that the A component of a LAB image is in its range
	 * @param i A component of a LAB image
	 * @return A component value on the appropriate range
	 */
	static public float clipLAB_A(float i) {
		return i < LAB_MIN_VAL_A ? 0 : (i > LAB_MAX_VAL_A ? LAB_MAX_VAL_A : i);
	}

	/**
	 * Make sure that the B component of a LAB image is in its range
	 * @param i B component of a LAB image
	 * @return B component value on the appropriate range
	 */
	static public float clipLAB_B(float i) {
		return i < LAB_MIN_VAL_B ? 0 : (i > LAB_MAX_VAL_B ? LAB_MAX_VAL_B : i);
	}

	/**
	 * Make sure that the A and B component of a LAB image is in its range
	 * @param i A or B component of a LAB image
	 * @return A or B component value on the appropriate range
	 */
	static public float clipLAB_AB(float i) {
		return i < LAB_MIN_VAL_AB ? 0 : (i > LAB_MAX_VAL_AB ? LAB_MAX_VAL_AB : i);
	}
		
	static public void XYZ_to_LAB(float x, float y, float z, float[] lab) {
		float xf = x / 95.0429f;
		float yf = y / 100f;
		float zf = z / 108.8900f;

		if (xf > 0.008856f) {
			xf = (float) Math.pow(xf, 1d / 3d);
		} else {
			xf = (7.787f * xf) + (16f / 116f);
		}

		if (yf > 0.008856) {
			yf = (float) Math.pow(yf, 1d / 3d);
		} else {
			yf = (7.787f * yf) + (16f / 116f);
		}

		if (zf > 0.008856) {
			zf = (float) Math.pow(zf, 1d / 3d);
		} else {
			zf = (7.787f * zf) + (16f / 116f);
		}

		lab[0] = (116.0f * yf) - 16.0f;
		lab[1] = 500.0f * (xf - yf);
		lab[2] = 200.0f * (yf - zf);

	}

	static public void intRGB_to_LAB(int rgb, float[] lab) {
		fromRGBtoLAB(
				(rgb >> 16) & 0xff,
				(rgb >> 8) & 0xff,
				rgb & 0xff,
				lab
		);
	}

	static public void RGB_to_LAB3(int r, int g, int b, float[] lab) {
		RGB_to_XYZ(r, g, b, lab);
		XYZ_to_LAB(lab[0], lab[1], lab[2], lab);
	}
	
	static public void YUV_to_LAB(int y, int u, int v, float[] lab) {
		int rgb = YUV_to_intRGB(y, u, v);
		intRGB_to_LAB(rgb, lab);
	}

	/**
	 * Converts XYZ pixel in a RGB pixel
	 * @param x X component of XYZ pixel
	 * @param y Y component of XYZ pixel
	 * @param z Z component of XYZ pixel
	 * @return an integer with the RGB pixel
	 */
	static public int fromXYZtoRGB(float x, float y, float z) {

		float xf = x / 100f;
		float yf = y / 100f;
		float zf = z / 100f;
		
		float r = (xf * 3.2406f) + (yf * -1.5372f) + (zf * -0.4986f);
		float g = (xf * -0.9689f) + (yf * 1.8758f) + (zf * 0.0415f);
		float b = (xf * 0.0557f) + (yf * -0.2040f) + (zf * 1.0570f);

		if (r > 0.0031308f) {
			r = (1.055f * ((float) Math.pow(r, 1.0 / 2.4))) - 0.055f;
		} else {
			r = r * 12.92f;
		}

		if (g > 0.0031308f) {
			g = (1.055f * ((float) Math.pow(g, 1.0 / 2.4))) - 0.055f;
		} else {
			g = g * 12.92f;
		}

		if (b > 0.0031308) {
			b = (1.055f * ((float) Math.pow(b, 1.0 / 2.4))) - 0.055f;
		} else {
			b = b * 12.92f;
		}

		r = (r < 0) ? 0 : r;
		g = (g < 0) ? 0 : g;
		b = (b < 0) ? 0 : b;

		int ri = (int) (r * 255f);
		int gi = (int) (g * 255f);
		int bi = (int) (b * 255f);

		if (ri > 255)
			ri = 255;
		
		if (gi > 255)
			gi = 255;

		if (bi > 255)
			bi = 255;

		int intRGB = (ri << 16) | (gi << 8) | bi;

		return intRGB;

	}
	
	/**
	 * Converts a LAB pixel to a XYZ pixel
	 * @param l L component of LAB pixel
	 * @param a A component of LAB pixel
	 * @param b B component of LAB pixel
	 * @param xyz array that will contain the XYZ pixel
	 */
	static public void fromLABtoXYZ(float l, float a, float b, float[] xyz) {

		float y = (l + 16f) / 116f;
		float y3 = (float) Math.pow(y, 3d);
		float x = (a / 500f) + y;
		float x3 = (float) Math.pow(x, 3d);
		float z = y - (b / 200f);
		float z3 = (float) Math.pow(z, 3d);

		if (y3 > 0.008856f) {
			y = y3;
		} else {
			y = (y - (16f / 116f)) / 7.787f;
		}

		if (x3 > 0.008856f) {
			x = x3;
		} else {
			x = (x - (16f / 116f)) / 7.787f;
		}

		if (z3 > 0.008856f) {
			z = z3;
		} else {
			z = (z - (16f / 116f)) / 7.787f;
		}

		xyz[0] = x * 95.0429f;
		xyz[1] = y * 100f;
		xyz[2] = z * 108.8900f;
	}
	
	/**
	 * Converts a LAB pixel to a RGB pixel
	 * @param l L component of LAB pixel
	 * @param a A component of LAB pixel
	 * @param b B component of LAB pixel
	 * @param xyzBuffer buffer used in order to not create an array every time this method is called
	 * @return an integer that contains the converted RGB pixel
	 */
	static public int fromLABtoRGB(float l, float a, float b, float[] xyzBuffer) {
		// converts to XYZ
		fromLABtoXYZ(l, a, b, xyzBuffer);
		// converts XYZ to RGB
		return fromXYZtoRGB(xyzBuffer[0], xyzBuffer[1], xyzBuffer[2]);

	}

	////////////////////////////////////////////////////////////////////////
	
	/**
	 * Converts a RGB pixel to LAB
	 * @param r R component of the RGB pixel
	 * @param g G component of the RGB pixel
	 * @param b B component of the RGB pixel
	 * @param lab array that will contain the LAB pixel value
	 */
	static public void fromRGBtoLAB(int r, int g, int b, float[] lab) {

		float rf = r / 255f;
		float gf = g / 255f;
		float bf = b / 255f;

		if (rf <= 0.04045f) {
			rf = rf / 12.92f;
		} else {
			rf = (float) Math.pow(((rf + 0.055f) / 1.055f), 2.4);
		}

		if (gf <= 0.04045) {
			gf = gf / 12.92f;
		} else {
			gf = (float) Math.pow(((gf + 0.055f) / 1.055f), 2.4);
		}

		if (bf <= 0.04045) {
			bf = bf / 12.92f;
		} else {
			bf = (float) Math.pow(((bf + 0.055f) / 1.055f), 2.4);
		}

		float xf = (rf * (41.24f / 95.0429f)) + (gf * (35.76f / 95.0429f)) + (bf * (18.05f / 95.0429f));
		float yf = (rf * (0.2126f)) + (gf * (0.7152f)) + (bf * (0.0722f));
		float zf = (rf * (1.93f / 108.8900f)) + (gf * (11.92f / 108.8900f)) + (bf * (95.05f / 108.8900f));

		if (xf > 0.008856f) {
			xf = (float) Math.pow(xf, 1d / 3d);
		} else {
			xf = (7.787f * xf) + (16f / 116f);
		}
		
		if (yf > 0.008856) {
			yf = (float) Math.pow(yf, 1d / 3d);
		} else {
			yf = (7.787f * yf) + (16f / 116f);
		}
		
		if (zf > 0.008856) {
			zf = (float) Math.pow(zf, 1d / 3d);
		} else {
			zf = (7.787f * zf) + (16f / 116f);
		}
		
		lab[0] = (116.0f * yf) - 16.0f;
		lab[1] = 500.0f * (xf - yf);
		lab[2] = 200.0f * (yf - zf);
	}

	/**
	 * Prints LAB's minimum and maximum values
	 */
	public static void showLABMinMax() {

		float maxL = Float.NEGATIVE_INFINITY;
		float maxA = Float.NEGATIVE_INFINITY;
		float maxB = Float.NEGATIVE_INFINITY;
		float minL = Float.POSITIVE_INFINITY;
		float minA = Float.POSITIVE_INFINITY;
		float minB = Float.POSITIVE_INFINITY;
		float[] lab = new float[3];

		for (int r = 0; r < 256; r++) {

			for (int g = 0; g < 256; g++) {

				for (int b = 0; b < 256; b++) {

					fromRGBtoLAB(r, g, b, lab);
					float L = lab[0];
					float A = lab[1];
					float B = lab[2];

					if (L < minL)
						minL = L;

					if (L > maxL)
						maxL = L;

					if (A < minA)
						minA = A;

					if (A > maxA)
						maxA = A;
					
					if (B < minB)
						minB = B;
					
					if (B > maxB)
						maxB = B;
				}
			}
		}

		System.out.println("L> " + minL + " .. " + maxL);
		System.out.println("A> " + minA + " .. " + maxA);
		System.out.println("B> " + minB + " .. " + maxB);
	}
	
}
