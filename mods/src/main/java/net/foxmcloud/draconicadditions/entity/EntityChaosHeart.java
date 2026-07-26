package net.foxmcloud.draconicadditions.entity;

import java.util.List;

import com.brandon3055.draconicevolution.entity.EntityDragonHeart;

import net.foxmcloud.draconicadditions.DAFeatures;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityChaosHeart extends EntityDragonHeart {

    public EntityChaosHeart(World world) {
        super(world);
        this.renderStack = new ItemStack(DAFeatures.chaosHeart);
    }

    public EntityChaosHeart(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.renderStack = new ItemStack(DAFeatures.chaosHeart);
    }

    @Override
    public void onUpdate() {
	if (!world.isRemote) {
		int age = getAge();
		if (age == 801) {
		List<EntityDragonHeart> dragonHearts = world.getEntitiesWithinAABB(EntityDragonHeart.class, AxisAlignedBB.getBoundingBox(this.posX - 1, this.posY - 1, this.posZ - 1, this.posX + 1, this.posY + 1, this.posZ + 1));
		if (dragonHearts != null && dragonHearts.size() > 0) {
			for(int i = 0; i < dragonHearts.size(); i++) {
				if (!(dragonHearts.get(i) instanceof EntityChaosHeart))
					dragonHearts.get(i).setDead();
			}
		}
	}
            if (age == 1279) {
                drop();
                setAge(1281);
            }
	}
        super.onUpdate();
    }

    private void drop() {
        EntityPlayer player = world.getClosestPlayerToEntity(this, 512);

        if (player != null) {
            world.spawnParticle("portal", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(DAFeatures.chaosHeart)));
        }
        else {
            world.spawnEntityInWorld(new EntityItem(world, posX, posY, posZ, new ItemStack(DAFeatures.chaosHeart)));
        }

        setDead();
    }
}
