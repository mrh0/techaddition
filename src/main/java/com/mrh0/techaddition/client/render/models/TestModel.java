package com.mrh0.techaddition.client.render.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TestModel extends Model {
	public ModelRenderer box = new ModelRenderer(this, 0, 0);

	public TestModel() {
		super(RenderType::getEntitySolid);
		box.addBox(0, 0, 0, 16, 16, 16);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		box.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
