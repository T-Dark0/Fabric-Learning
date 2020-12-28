package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.blocks.barrel.BarrelBlock;
import net.fabricmc.example.blocks.barrel.BarrelBlockEntity;
import net.fabricmc.example.items.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "testmod";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		System.out.println("Hello Fabric world!");
		Items.FABRIC_ITEM = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "fabric_item"),
				new FabricItem(new FabricItemSettings().group(ItemGroup.MISC)));

		Identifier barrel_id = new Identifier(MOD_ID, "barrel");
		Blocks.BARREL = Registry.register(Registry.BLOCK, barrel_id, new BarrelBlock(FabricBlockSettings.of(Material.STONE).hardness(1.5f)));
		BlockEntities.BARREL = Registry.register(Registry.BLOCK_ENTITY_TYPE, barrel_id,
				BlockEntityType.Builder.create(BarrelBlockEntity::new, Blocks.BARREL).build(null));
		Items.BARREL = Registry.register(Registry.ITEM, barrel_id, new BlockItem(Blocks.BARREL, new Item.Settings().group(ItemGroup.MISC)));
	}
}
