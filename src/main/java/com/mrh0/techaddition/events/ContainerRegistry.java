package com.mrh0.techaddition.events;

import com.mrh0.techaddition.events.opts.ContainerOptions;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.IForgeRegistry;

public class ContainerRegistry extends GenericRegistry<ContainerType<?>, ContainerOptions>{
	
	public static ContainerRegistry instance;
	
	public ContainerRegistry() {
		super();
		instance = this;
	}

	protected void init(IForgeRegistry<ContainerType<?>> reg, ContainerType<?> obj) {
		reg.register(obj);
	}
}
