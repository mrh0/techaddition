package com.mrh0.techaddition.state;

import net.minecraft.util.IStringSerializable;

public enum QuarryState implements IStringSerializable {
	Invalid("invalid"),
	Active("active"),
	Inactive("inactive");

	private String name;
	
	private QuarryState(String name) {
		this.name = name;
	}

	@Override
	public String getString() {
		return this.name;
	}
}
