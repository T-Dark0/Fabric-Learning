package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.example.blocks.barrel.BarrelBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Initialising client");
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.BARREL, BarrelBlockEntityRenderer::new);
    }
}
