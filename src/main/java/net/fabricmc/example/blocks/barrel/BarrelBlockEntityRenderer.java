package net.fabricmc.example.blocks.barrel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BarrelBlockEntityRenderer extends BlockEntityRenderer<BarrelBlockEntity> {
    // A jukebox itemstack
    private static final ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public BarrelBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BarrelBlockEntity barrel, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        System.out.println("LOGSPAM");
        if (barrel.getWorld() == null) {
            return;
        }

        matrices.push();

        double offset = Math.sin((barrel.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
        // Move the item
        matrices.translate(0.5, 1.25 + offset, 0.5);

        // Rotate the item
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((barrel.getWorld().getTime() + tickDelta) * 4));

        int lightAbove = WorldRenderer.getLightmapCoordinates(barrel.getWorld(), barrel.getPos().up());
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND,
                lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

        matrices.pop();
    }
}