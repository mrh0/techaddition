package com.mrh0.techaddition.items.base;


import com.mrh0.techaddition.events.opts.ItemOptions;
import com.mrh0.techaddition.groups.ModGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class BaseBlockItem extends BlockItem {

	public BaseBlockItem(Block block, ItemOptions opts) {
		super(block,  new Properties().group(ModGroup.MAIN));
		this.setRegistryName(block.getRegistryName());
	}
	
	public BaseBlockItem(Block block, ItemGroup group) {
		super(block,  new Properties().group(group));
		this.setRegistryName(block.getRegistryName());
	}
	
}
