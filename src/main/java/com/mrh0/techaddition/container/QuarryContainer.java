package com.mrh0.techaddition.container;

import com.mrh0.techaddition.Index;
import com.mrh0.techaddition.container.base.BaseContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class QuarryContainer  extends BaseContainer {
	
	public final BlockPos pos;
	
	protected QuarryContainer(int id, BlockPos pos) {
		super(Index.QUARRY_CONTAINER, id);
		this.pos = pos;
	}
	
	public static QuarryContainer create(int windowId, PlayerInventory playerInventory, BlockPos pos) {
		return new QuarryContainer(windowId, pos);
	}

	public static QuarryContainer create(int windowId, PlayerInventory playerInventory, PacketBuffer buf) {
		return new QuarryContainer(windowId, buf.readBlockPos());
	}
}
