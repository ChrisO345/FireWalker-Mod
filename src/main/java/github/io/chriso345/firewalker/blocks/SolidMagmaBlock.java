package github.io.chriso345.firewalker.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SolidMagmaBlock extends DrySolidMagmaBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final int NEIGHBORS_TO_AGE = 4;
    private static final int NEIGHBORS_TO_MELT = 2;

    public SolidMagmaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public void randomTick(BlockState p_221238_, ServerLevel p_221239_, BlockPos p_221240_, RandomSource p_221241_) {
        this.tick(p_221238_, p_221239_, p_221240_, p_221241_);
    }

    public void tick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource rand) {
        if ((rand.nextInt(3) == 0 || this.fewerNeigboursThan(levelIn, pos, 4)) && levelIn.getMaxLocalRawBrightness(pos) > 11 - state.getValue(AGE) - state.getLightBlock(levelIn, pos) && this.slightlyMelt(state, levelIn, pos)) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (Direction direction : Direction.values()) {
                blockpos$mutableblockpos.setWithOffset(pos, direction);
                BlockState blockstate = levelIn.getBlockState(blockpos$mutableblockpos);
                if (blockstate.is(this) && !this.slightlyMelt(blockstate, levelIn, blockpos$mutableblockpos)) {
                    levelIn.scheduleTick(blockpos$mutableblockpos, this, Mth.nextInt(rand, 20, 40));
                }
            }

        } else {
            levelIn.scheduleTick(pos, this, Mth.nextInt(rand, 20, 40));
        }
    }

    private boolean slightlyMelt(BlockState state, Level levelIn, BlockPos pos) {
        int i = state.getValue(AGE);
        if (i < 3) {
            levelIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(i + 1)), 2);
            return false;
        } else {
            this.melt(state, levelIn, pos);
            return true;
        }
    }

    public void neighborChanged(BlockState state, Level levelIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (blockIn.defaultBlockState().is(this) && this.fewerNeigboursThan(levelIn, pos, 2)) {
            this.melt(state, levelIn, pos);
        }

        super.neighborChanged(state, levelIn, pos, blockIn, fromPos, isMoving);
    }

    private boolean fewerNeigboursThan(BlockGetter worldIn, BlockPos pos, int neighborsRequired) {
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pos, direction);
            if (worldIn.getBlockState(blockpos$mutableblockpos).is(this)) {
                ++i;
                if (i >= neighborsRequired) {
                    return false;
                }
            }
        }

        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}
