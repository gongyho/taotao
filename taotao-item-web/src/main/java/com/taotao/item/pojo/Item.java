package com.taotao.item.pojo;

import org.springframework.beans.BeanUtils;

import com.taotao.pojo.TbItem;

public class Item extends TbItem {
	/**
	 * copy object
	 * @param item
	 */
	public Item(TbItem item) {
		BeanUtils.copyProperties(item, this);
	}
	
	public String[] getImages() {
		if(super.getImage()!=null && super.getImage().trim().length()>0) {
			return super.getImage().split(",");
		}
		return null;
	}
}
