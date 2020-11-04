package com.mrh0.techaddition.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrh0.techaddition.blocks.QuarryCore;
import com.mrh0.techaddition.client.render.models.QuarryBeam;
import com.mrh0.techaddition.client.render.models.QuarryCart;
import com.mrh0.techaddition.client.render.models.TestModel;
import com.mrh0.techaddition.tileentity.QuarryTileEntity;
import com.mrh0.techaddition.util.Util;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class QuarryRenderer extends TileEntityRenderer<QuarryTileEntity> {
	private Model model;
	private Model cart;
	private Model beamx;
	private Model beamz;
	private static final ResourceLocation CART_MODEL_TEXTURE = new ResourceLocation("minecraft:textures/block/stone.png");
	private static final ResourceLocation BEAM_MODEL_TEXTURE = new ResourceLocation("minecraft:textures/block/anvil.png");

	public QuarryRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		model = new TestModel();
		cart = new QuarryCart();
		beamx = new QuarryBeam(Axis.X);
		beamz = new QuarryBeam(Axis.Z);
	}

	
	@Override
	public void render(QuarryTileEntity te, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if(!te.ready())
			return;

		IVertexBuilder renderBufferCart = bufferIn.getBuffer(model.getRenderType(CART_MODEL_TEXTURE));
		
		
		/*stack.push();
		stack.translate(te.rightNear.getX(), te.rightNear.up().getY(), te.rightNear.getZ());
		model.render(stack, renderBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		
		stack.push();
		stack.translate(te.rightFar.getX(), te.rightFar.up().getY(), te.rightFar.getZ());
		model.render(stack, renderBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		
		stack.push();
		stack.translate(te.leftNear.getX(), te.leftNear.up().getY(), te.leftNear.getZ());
		model.render(stack, renderBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		
		stack.push();
		stack.translate(te.leftFar.getX(), te.leftFar.up().getY(), te.leftFar.getZ());
		model.render(stack, renderBuffer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();*/
		
		if(te.current == null)
			return;
		BlockPos p = te.current;
		
		Direction d = te.getWorld().getBlockState(te.getPos()).get(QuarryCore.FACING);
		Axis a = d.getAxis();
		
		stack.push();
		stack.translate(p.getX(), p.up().getY(), p.getZ());
		model.render(stack, renderBufferCart, 240, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		
		stack.push();
		BlockPos np1 = te.getMainAxisPos1(d);
		stack.translate(np1.getX(), np1.up().getY(), np1.getZ());
		cart.render(stack, renderBufferCart, 240, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		
		stack.push();
		BlockPos np2 = te.getMainAxisPos2(d);
		stack.translate(np2.getX(), np2.up().getY(), np2.getZ());
		cart.render(stack, renderBufferCart, 240, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		
		IVertexBuilder renderBufferBeam = bufferIn.getBuffer(model.getRenderType(BEAM_MODEL_TEXTURE));
		
		stack.push();
		//te.getBeamPos();
		//Vector3f bs = new Vector3f(1,1,1);//te.getBeamScale();
		Vector3f bp = Util.f(np1);//Util.f(np1.offset(d.getOpposite(), a == Axis.X ? Util.dif(np1.getX(), np2.getX()) : Util.dif(np1.getZ(), np2.getZ()) ));
		stack.translate(bp.getX(), bp.getY()+1, bp.getZ());
		//stack.scale(bs.getX(), bs.getY(), bs.getZ());
		int l = a == Axis.X ? Util.dif(np1.getX(), np2.getX()) : Util.dif(np1.getZ(), np2.getZ());
		for(int i = 0; i < l-1; i++) {
			BlockPos tp = BlockPos.ZERO.offset(d.getOpposite());
			stack.translate(tp.getX(), tp.getY(), tp.getZ());
			(a == Axis.X ? beamx : beamz).render(stack, renderBufferBeam, 240, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		stack.pop();
	}
	
	@Override
	public boolean isGlobalRenderer(QuarryTileEntity te) {
		return true;
	}
}
