package com.mrh0.techaddition.blocks.base;

import com.mrh0.techaddition.events.BlockRegistry;
import com.mrh0.techaddition.events.opts.BlockOptions;

import net.minecraft.block.Block;

public class BaseBlock extends Block{

	public BaseBlock(String name, Properties block) {
		super(block);
		this.setRegistryName(name);
		BlockRegistry.instance.register(this, new BlockOptions());
	}
	
	public BaseBlock(String name, Properties block, BlockOptions opts) {
		super(block);
		this.setRegistryName(name);
		BlockRegistry.instance.register(this, opts);
	}
}