package com.uppoints.colorSpace.filter;

import java.awt.image.BufferedImage;

import com.uppoints.colorSpace.ImageAbstract;

abstract public class FilterAbstract {

	private ImageAbstract<?> image ;

	public FilterAbstract(ImageAbstract<?> image) {
		this.image = image;
	}
	
	public BufferedImage getBufferedImage() {
		ensureFilterApplied();
		return image.getBufferedImage();
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public ImageAbstract<?> getImage() {
		return image;
	}
	
	private boolean filterApplied = false ;
	
	public boolean isFilterApplied() {
		return filterApplied;
	}
	
	protected void notifyFilterApplied() {
		filterApplied = true ;
	}
	
	public void ensureFilterApplied() {
		if (!filterApplied) {
			applyFilter();
			notifyFilterApplied();
		}
	}
	
	abstract public void applyFilter() ;
	
}
