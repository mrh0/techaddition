package com.mrh0.techaddition;

import com.mrh0.techaddition.blocks.QuarryCore;
import com.mrh0.techaddition.blocks.QuarryFrame;
import com.mrh0.techaddition.container.QuarryContainer;
import com.mrh0.techaddition.events.ContainerRegistry;
import com.mrh0.techaddition.events.TileEntityRegistry;
import com.mrh0.techaddition.events.opts.ContainerOptions;
import com.mrh0.techaddition.events.opts.TileEntityOptions;
import com.mrh0.techaddition.tileentity.QuarryTileEntity;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class Index {
	
	//Block
	public static Block QUARRY_CORE;
	public static Block QUARRY_FRAME;
	
	//Tile Entity
	public static TileEntityType<QuarryTileEntity> QUARRY_TILE_ENTITY_TYPE;
	
	//Container
	public static ContainerType<QuarryContainer> QUARRY_CONTAINER;
	
	
	public static void blocks() {
		QUARRY_CORE = new QuarryCore();
		QUARRY_FRAME = new QuarryFrame();
	}
	
	public static void items() {
		
	}
	
	public static void tileentities() {
		QUARRY_TILE_ENTITY_TYPE = 
				TileEntityRegistry.instance.<QuarryTileEntity>register(QuarryTileEntity::new, new TileEntityOptions("quarry", QUARRY_CORE));
	}
	
	@SuppressWarnings("unchecked")
	public static void containers() {
		QUARRY_CONTAINER = (ContainerType<QuarryContainer>) 
				ContainerRegistry.instance.register(IForgeContainerType.create(QuarryContainer::create).setRegistryName("quarry_container"), new ContainerOptions());
	}
}
