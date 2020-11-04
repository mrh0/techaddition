package com.mrh0.techaddition.tileentity;

import java.util.Iterator;
import java.util.stream.Stream;

import com.mrh0.techaddition.Index;
import com.mrh0.techaddition.blocks.QuarryCore;
import com.mrh0.techaddition.container.QuarryContainer;
import com.mrh0.techaddition.util.Util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class QuarryTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

	private final static int MAX_FRAME_RANGE = 16;
	private final static int MAX_FRAME_DISTANCE_RANGE = 32;
	private final static int TIME_TO_MINE = 10;
	
	public BlockPos leftNear = null;
	public BlockPos rightNear = null;
	public BlockPos leftFar = null;
	public BlockPos rightFar = null;
	
	public BlockPos current = pos;
	
	private int ttc = TIME_TO_MINE;
	
	public QuarryTileEntity() {
		super(Index.QUARRY_TILE_ENTITY_TYPE);
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inv, PlayerEntity player) {
		return QuarryContainer.create(windowId, inv, pos);
	}
	
	private Iterator<BlockPos> work;

	@Override
	public void tick() {
		if(work == null || !work.hasNext())
			return;
		if(ttc-- > 0)
			return;
		current = work.next();
		while(current.add(pos).getY() >= 0 && current.add(pos).getY() < 256 && world.isAirBlock(current.add(pos))) {
			if(!work.hasNext())
				return;
			current = work.next();
		}
		ttc = TIME_TO_MINE;
		System.out.println("Delete: " + current);
		world.setBlockState(current.add(pos), Blocks.AIR.getDefaultState());
		updateSync();
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.techaddition.quarry");
	}

	public void updateFrames() {
		
		Direction back = getBlockState().get(QuarryCore.FACING).getOpposite();
		
		BlockPos other = findOtherSide(pos, back);
		if(other == null)
			return;
		
		BlockPos[] l = findEnd(pos.offset(back), other, back.rotateYCCW());
		BlockPos[] r = findEnd(pos.offset(back), other, back.rotateY());
		
		BlockPos l1 = l[0];
		BlockPos l2 = l[1];
		BlockPos r1 = r[0];
		BlockPos r2 = r[1];

		leftNear = l1.subtract(pos).offset(back);
		leftFar = l2.subtract(pos).offset(back.getOpposite());
		rightNear = r1.subtract(pos).offset(back);
		rightFar = r2.subtract(pos).offset(back.getOpposite());
		
		updateSync();
		
		BlockPos[] bounds = getBounds();
		Stream<BlockPos> s = BlockPos.getAllInBox(bounds[1], bounds[0].add(new BlockPos(0,-pos.getY(),0)));
		work = s.iterator();
		
		System.out.println("Update:"+leftNear+":"+leftFar+":"+rightNear+":"+rightFar);
	}
	
	private boolean isValidFrame(BlockPos pos) {
		return world.getBlockState(pos).isIn(Index.QUARRY_FRAME) && world.getBlockState(pos.up()).isIn(Blocks.RAIL);
	}
	
	public boolean ready() {
		return rightNear != null && leftNear != null && rightFar != null && leftFar != null;
	}
	
	private BlockPos[] findEnd(BlockPos start, BlockPos otherstart, Direction dir) {
		int i = 0;
		BlockPos p1 = start;
		BlockPos p2 = otherstart;
		while(i < MAX_FRAME_RANGE && isValidFrame(p1.offset(dir)) && isValidFrame(p2.offset(dir))) {
			p1 = p1.offset(dir);
			p2 = p2.offset(dir);
			i++;
		}
		BlockPos[] r = {p1, p2};
		return r;
	}
	
	private BlockPos findOtherSide(BlockPos start, Direction dir) {
		int i = 0;
		BlockPos p = start.offset(dir, 2);
		while(i < MAX_FRAME_RANGE && !isValidFrame(p)) {
			p = p.offset(dir);
			i++;
		}
		if(i+1 >= MAX_FRAME_DISTANCE_RANGE)
			return null;
		return p;
	}
	
	
	
	
	
	public BlockPos getMainAxisPos1(Direction d) {
		Axis a = d.getAxis();
		if(a == Axis.X) {
			return new BlockPos(leftNear.getX(), 0, current.getZ()).offset(d);
		}
		return new BlockPos(current.getX(), 0, leftNear.getZ()).offset(d);
	}
	
	public BlockPos getMainAxisPos2(Direction d) {
		Axis a = d.getAxis();
		if(a == Axis.X) {
			return new BlockPos(leftFar.getX(), 0, current.getZ()).offset(d.getOpposite());
		}
		return new BlockPos(current.getX(), 0, leftFar.getZ()).offset(d.getOpposite());
	}
	
	public BlockPos getBeamPos(Direction d) {
		Axis a = d.getAxis();
		if(a == Axis.X) {
			return new BlockPos(d == Direction.WEST ? leftNear.getX() : leftFar.getX(), 0, current.getZ()).offset(d);
		}
		return new BlockPos(current.getX(), 0, d == Direction.NORTH ? leftNear.getZ() : leftFar.getZ()).offset(d);
	}
	
	/*public Vector3f getBeamPos() {
		Direction d = world.getBlockState(pos).get(QuarryCore.FACING);
		Axis a = d.getAxis();
		if(a == Axis.X) {
			BlockPos p = new BlockPos(0, 0, current.getZ()).offset(d.getOpposite());
			return new Vector3f(Util.mid(leftFar.getX(), leftNear.getX()) + p.getX(), p.getY(), p.getZ());
		}
		BlockPos p = new BlockPos(current.getX(), 0, 0).offset(d.getOpposite());
		return new Vector3f(p.getX(), p.getY(), Util.mid(leftFar.getZ(), leftNear.getZ()) + p.getZ());
	}*/
	
	public Vector3f getBeamScale() {
		Direction d = world.getBlockState(pos).get(QuarryCore.FACING);
		Axis a = d.getAxis();
		if(a == Axis.X) {
			return new Vector3f(Util.dif(leftNear.getX(), leftFar.getX())*2+2, 1, 1);
		}
		return new Vector3f(1, 1, Util.dif(leftNear.getZ(), leftFar.getZ())*2+2);
	}
	
	public BlockPos[] getBounds() {
		int minX = Util.min(leftNear.getX(), leftNear.getX(), rightNear.getX(), rightFar.getX());
		int minY = 0;//Util.min(leftNear.getY(), leftNear.getY(), rightNear.getY(), rightFar.getY());
		int minZ = Util.min(leftNear.getZ(), leftNear.getZ(), rightNear.getZ(), rightFar.getZ());
		
		int maxX = Util.max(leftNear.getX(), leftNear.getX(), rightNear.getX(), rightFar.getX());
		int maxY = 0;//Util.max(leftNear.getY(), leftNear.getY(), rightNear.getY(), rightFar.getY());
		int maxZ = Util.max(leftNear.getZ(), leftNear.getZ(), rightNear.getZ(), rightFar.getZ());
		
		BlockPos[] r = {new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)};
		return r;
	}
	
	
	public void updateSync() {
		world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 0);
		this.markDirty();
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		leftNear = Util.readPos("leftNear", nbt);
		leftFar = Util.readPos("leftFar", nbt);
		rightNear = Util.readPos("rightNear", nbt);
		rightFar = Util.readPos("rightFar", nbt);
		current = Util.readPos("current", nbt);
		super.read(state, nbt);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		Util.writePos("leftNear", nbt, leftNear == null ? pos : leftNear);
		Util.writePos("leftFar", nbt, leftFar == null ? pos : leftFar);
		Util.writePos("rightNear", nbt, rightNear == null ? pos : rightNear);
		Util.writePos("rightFar", nbt, rightFar == null ? pos : rightFar);
		Util.writePos("current", nbt, current == null ? pos : current);
		return super.write(nbt);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT update = getUpdateTag();
        int data = 0;
        return new SUpdateTileEntityPacket(this.pos, data, update);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT update = pkt.getNbtCompound();
        handleUpdateTag(this.getBlockState(), update);
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbt = new CompoundNBT();
		write(nbt);
        return nbt;
	}
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
		read(state, nbt);
	}
}
