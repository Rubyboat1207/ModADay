package com.rubyboat.daily.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import static com.rubyboat.daily.Main.MOONSTONE_APPLIED;

public class MoonstoneItem extends Item {
    public MoonstoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos bp = context.getBlockPos();
        BlockState blockState = world.getBlockState(bp);
        BlockState below = world.getBlockState(bp.down());

        BlockState farmland = null;

        if(blockState.getBlock() instanceof FarmlandBlock) {
            farmland = blockState;
        }
        if(below.getBlock() instanceof FarmlandBlock) {
            farmland = below;
        }

        if(farmland == null || farmland.get(MOONSTONE_APPLIED)) {
            return ActionResult.PASS;
        }

        world.setBlockState(farmland == blockState ? bp : bp.down(), farmland.with(MOONSTONE_APPLIED, true));
        return ActionResult.SUCCESS;
    }
}
