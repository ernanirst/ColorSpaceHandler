package com.uppoints.colorSpace.filter;

import com.uppoints.colorSpace.ImageAbstract;
import com.uppoints.colorSpace.ImageHelper;

public class FilterAutoContrast extends FilterAbstract {

	public FilterAutoContrast(ImageAbstract<?> image) {
		super(image);
	}

	@Override
	public void applyFilter() {
		ImageAbstract<?> image = getImage() ;
		
		int w = getWidth() ;
		int h = getHeight() ;
		
		int[] retYUV = new int[3] ;
		
		int minY = 255 ;
		int maxY = 0 ;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				
				int rgb = image.getRGB(x, y) ;
				
				int r = (rgb >> 16) & 0x000000ff ;
				int g = (rgb >> 8) & 0x000000ff ;
				int b = rgb & 0x000000ff ;
				
				ImageHelper.fromRGBtoYUV(r, g, b, retYUV);
				
				int Y = retYUV[0] ;
				
				if (Y < minY) minY = Y ;
				if (Y > maxY) maxY = Y ;
			}
		}

		float scaleY = maxY-minY ;
		
		int[] retRGB = new int[3] ;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				
				int rgb = image.getRGB(x, y) ;
				
				int r = (rgb >> 16) & 0x000000ff ;
				int g = (rgb >> 8) & 0x000000ff ;
				int b = rgb & 0x000000ff ;
				
				ImageHelper.fromRGBtoYUV(r, g, b, retYUV);
				
				int Y = retYUV[0] ;
				
				int yScaled = (int) (((Y-minY)/scaleY)*255) ;
				
				ImageHelper.fromYUVtoRGB(yScaled, retYUV[1], retYUV[2], retRGB);
				
				image.setRGB(x, y, retRGB);
			}
		}
		
		notifyFilterApplied();
	}

}
