package net.fabricmc.example.blocks.barrel;

import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BarrelBlock extends BlockWithEntity {

    public BarrelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos blockPos, PlayerEntity player) {
        if (world.isClient) return;

        Inventory blockEntity = (Inventory) world.getBlockEntity(blockPos);
        assert blockEntity != null;

        int slot = -1;
        for(int i = 0; i < 10; i++) {
            if(! blockEntity.getStack(i).isEmpty()) {
                slot = i;
                break;
            }
        }
        if(slot == -1) return;

        player.inventory.offerOrDrop(world, blockEntity.getStack(slot));
        blockEntity.removeStack(slot);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient) return ActionResult.SUCCESS;

        if (player.getStackInHand(hand).isEmpty() && player.isSneaking()) {
            openGui(state, world, pos, player);
        } else {
            insertItem(world, pos, player, hand);
        }

        return ActionResult.SUCCESS;
    }

    private void insertItem(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (player.getStackInHand(hand).isEmpty()) {
            return;
        }
        int slot = 0;
        while(! Objects.requireNonNull(blockEntity).getStack(slot).isEmpty()) {
            slot++;
        }
        blockEntity.setStack(slot, player.getStackInHand(hand).copy());
        for(int i = 0; i < 9; i++) {
            System.out.println("Slot " + i + " holds " + blockEntity.getStack(i));
        }
    }

    private void openGui(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
        if (screenHandlerFactory != null) {
            player.openHandledScreen(screenHandlerFactory);
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockView world) {
        return new BarrelBlockEntity();
    }
}
