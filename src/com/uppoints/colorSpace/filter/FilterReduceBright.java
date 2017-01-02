package com.uppoints.colorSpace.filter;

import com.uppoints.colorSpace.ImageAbstract;
import com.uppoints.colorSpace.ImageHelper;

public class FilterReduceBright extends FilterAbstract {

	final private float reduction ;
	
	public FilterReduceBright(ImageAbstract<?> image) {
		this(image, 0.30) ;
	}
	
	public FilterReduceBright(ImageAbstract<?> image, double reduction) {
		super(image);
		this.reduction = (float) reduction ;
	}
	
	public float getReduction() {
		return reduction;
	}

	@Override
	public void applyFilter() {
		ImageAbstract<?> image = getImage() ;
		
		int w = getWidth() ;
		int h = getHeight() ;
		
		int[] retYUV = new int[3] ;
		
		int[] retRGB = new int[3] ;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				
				int rgb = image.getRGB(x, y) ;
				
				int r = (rgb >> 16) & 0x000000ff ;
				int g = (rgb >> 8) & 0x000000ff ;
				int b = rgb & 0x000000ff ;
				
				ImageHelper.fromRGBtoYUV(r, g, b, retYUV);
				
				int Y = retYUV[0] ;
				
				int yScaled = (int) ((Y * (1-reduction)) + (255f*(reduction/2))) ;
				
				ImageHelper.fromYUVtoRGB(yScaled, retYUV[1], retYUV[2], retRGB);
				
				image.setRGB(x, y, retRGB);
			}
		}
		
		notifyFilterApplied();
	}

}
