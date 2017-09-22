package net.insomniakitten.mvillage.common.util;

/*
 *  Copyright 2017 InsomniaKitten
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class BoundingBoxHelper {

    // FIXME
    private static AxisAlignedBB computeAABBForFacing(
            AxisAlignedBB aabb, EnumFacing currentFacing, EnumFacing newFacing) {
        double minX = aabb.minX, minY = aabb.minY, minZ = aabb.minZ;
        double maxX = aabb.maxX, maxY = aabb.maxY, maxZ = aabb.maxZ;
        float oldAngle = currentFacing.getHorizontalAngle();
        float newAngle = newFacing.getHorizontalAngle();
        // Compute angle difference
        float angle = oldAngle > newAngle ? oldAngle - newAngle : newAngle - oldAngle;
        // VERTICAL
        // x90  new AxisAlignedBB(1 - maxX, minZ,     1 - maxY, 1 - minX, 1,        1 - minY)
        // x270 new AxisAlignedBB(minX,     1 - maxZ, minY,     maxX,     1 - minZ, maxY);
        // HORIZONTAL
        // y0   new AxisAlignedBB(minX,     minY,     minZ,     maxX,     maxY,     maxZ);
        // y90  new AxisAlignedBB(1 - maxZ, minY,     1 - maxX, 1 - minZ, maxY,     1 - minX);
        // y180 new AxisAlignedBB(1 - maxX, minY,     1 - maxZ, 1 - minX, maxY,     1 - minZ);
        // y270 new AxisAlignedBB(minZ,     minY,     minX,     maxZ,     maxY,     maxX);
        return aabb;
    }

}
