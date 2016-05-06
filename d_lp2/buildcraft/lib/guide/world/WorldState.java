package buildcraft.lib.guide.world;

import java.util.List;

import javax.vecmath.Vector3f;

import com.google.common.collect.ImmutableList;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;

import buildcraft.core.blueprints.Blueprint;
import buildcraft.core.blueprints.BlueprintBase;
import buildcraft.core.blueprints.Template;
import buildcraft.core.lib.utils.Utils;
import buildcraft.core.lib.world.FakeWorld;
import buildcraft.lib.misc.NBTUtils;

public class WorldState {
    private final Vector3f cameraPos;
    private final double cameraYaw, cameraPitch;
    private final Vec3i size;
    private final FakeWorld world;
    private final List<WorldLabel> labels;

    public WorldState(WorldInfo info) {
        byte[] schematic = info.getSchematic();
        BlueprintBase blueprint = BlueprintBase.loadBluePrint(NBTUtils.load(schematic));
        this.size = blueprint.size;
        if (blueprint instanceof Blueprint) {
            this.world = new FakeWorld((Blueprint) blueprint);
        } else {
            this.world = new FakeWorld((Template) blueprint, Blocks.BRICK_BLOCK.getDefaultState());
        }
        this.labels = ImmutableList.copyOf(info.labels);
        this.cameraPos = Utils.convertFloat(info.cameraPos);

        double xDiff = cameraPos.x - info.cameraFacing.xCoord;
        double yDiff = cameraPos.y - info.cameraFacing.yCoord;
        double zDiff = cameraPos.z - info.cameraFacing.zCoord;

        double opposite = Math.abs(yDiff);
        double adjacent = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

        cameraPitch = Math.atan2(adjacent, opposite);
        cameraYaw = Math.atan2(zDiff, xDiff) - Math.PI / 2;

    }
}
