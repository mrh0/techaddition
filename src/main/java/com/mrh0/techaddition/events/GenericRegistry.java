package com.mrh0.techaddition.events;

import java.util.ArrayList;
import com.mrh0.techaddition.events.opts.RegOptions;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class GenericRegistry<T extends IForgeRegistryEntry<T>, O extends RegOptions<O>> {
	protected ArrayList<T> objs;
	
	public GenericRegistry() {
		objs = new ArrayList<T>();
	}
	
	public T register(T obj, O opts) {
		if(opts.isEnabled)
			objs.add(obj);
		return obj;
	}
	
	public void initAll(IForgeRegistry<T> reg) {
		for(T obj : objs) {
			init(reg, obj);
		}
	}
	
	protected abstract void init(IForgeRegistry<T> reg, T obj);
}
