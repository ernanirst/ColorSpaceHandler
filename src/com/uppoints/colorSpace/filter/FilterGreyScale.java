package com.uppoints.colorSpace.filter;

import com.uppoints.colorSpace.ImageAbstract;

public class FilterGreyScale extends FilterAbstract {

	public FilterGreyScale(ImageAbstract<?> image) {
		super(image);
	}

	@Override
	public void applyFilter() {
		ImageAbstract<?> image = getImage() ;
		
		int w = getWidth() ;
		int h = getHeight() ;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				
				int rgb = image.getRGB(x, y) ;
				
				int r = (rgb >> 16) & 0x000000ff ;
				int g = (rgb >> 8) & 0x000000ff ;
				int b = rgb & 0x000000ff ;
				
				int gs = (r+g+b)/3 ;
				
				image.setRGB(x, y, gs,gs,gs);
			}
		}
		
		notifyFilterApplied();
	}

}
