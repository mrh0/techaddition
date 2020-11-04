package com.mrh0.techaddition.blocks;

import com.mrh0.techaddition.blocks.base.BaseBlock;
import com.mrh0.techaddition.state.QuarryState;
import com.mrh0.techaddition.tileentity.QuarryTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class QuarryCore extends BaseBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<QuarryState> STATE = EnumProperty.<QuarryState>create("state", QuarryState.class);
	
	public QuarryCore() {
		super("quarry_core", Properties.from(Blocks.IRON_BLOCK));
		
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(STATE, QuarryState.Invalid));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, STATE);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new QuarryTileEntity();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext c) {
		return getDefaultState().with(FACING, c.getPlacementHorizontalFacing().getOpposite()).with(STATE, QuarryState.Invalid);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if(worldIn.isRemote)
			return ActionResultType.SUCCESS;
		TileEntity te = worldIn.getTileEntity(pos);
		if(te == null)
			return ActionResultType.FAIL;
		if(!(te instanceof QuarryTileEntity))
			return ActionResultType.FAIL;
		QuarryTileEntity qte = (QuarryTileEntity)te;
		qte.updateFrames();
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
