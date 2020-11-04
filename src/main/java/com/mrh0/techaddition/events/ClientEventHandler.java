package com.mrh0.techaddition.events;

import com.mrh0.techaddition.Index;
import com.mrh0.techaddition.TechAddition;
import com.mrh0.techaddition.client.render.QuarryRenderer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TechAddition.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
	
	public static void clientRegistry() {
    	//ScreenManager.registerFactory(Index.BOOKSHELF_CONTAINER, BookshelfGui::new);
		RenderTypeLookup.setRenderLayer(Index.QUARRY_CORE, RenderType.getCutout());
    	ClientRegistry.bindTileEntityRenderer(Index.QUARRY_TILE_ENTITY_TYPE, QuarryRenderer::new);
    }
}
