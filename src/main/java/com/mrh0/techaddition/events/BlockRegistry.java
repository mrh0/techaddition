package com.mrh0.techaddition.events;

import com.mrh0.techaddition.events.opts.BlockOptions;
import com.mrh0.techaddition.items.base.BaseBlockItem;
import net.minecraft.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry extends GenericRegistry<Block, BlockOptions>{
	
	public static BlockRegistry instance;
	
	public BlockRegistry() {
		super();
		instance = this;
	}
	
	@Override
	public Block register(Block block, BlockOptions opts) {
		Block b = super.register(block, opts);
		if(opts.makeItem)
			ItemRegistry.instance.register(new BaseBlockItem(block, opts.itemOpts), opts.itemOpts);
		return b;
	}

	@Override
	public void init(IForgeRegistry<Block> reg, Block obj) {
		reg.register(obj);
	}
}
