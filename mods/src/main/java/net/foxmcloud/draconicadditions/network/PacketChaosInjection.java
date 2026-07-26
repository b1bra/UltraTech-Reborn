package net.foxmcloud.draconicadditions.network;

import com.brandon3055.draconicevolution.api.itemupgrade.UpgradeHelper;
import com.brandon3055.draconicevolution.items.ToolUpgrade;

import io.netty.buffer.ByteBuf;
import net.foxmcloud.draconicadditions.DAFeatures;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import java.util.List;
import net.minecraft.util.ChatComponentTranslation;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketChaosInjection implements IMessage {

	public PacketChaosInjection() {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<PacketChaosInjection, IMessage> {

		public Handler() {}

		@Override
		public IMessage onMessage(PacketChaosInjection message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.getServerWorld().addScheduledTask(() -> {
				ItemStack chest = player.inventory.armorInventory.get(2);
				if (chest.getItem() == DAFeatures.chaoticChest) {
					if (UpgradeHelper.getUpgradeLevel(chest, ToolUpgrade.ATTACK_DAMAGE) > 0) {
						NBTTagCompound chestNBT = chest.getTagCompound();
						if (chestNBT.getBoolean("injecting")) {
							player.addChatMessage(new ChatComponentTranslation("msg.da.chaosInjection.stop"));
							chestNBT.setBoolean("injecting", false);
						}
						else {
							player.addChatMessage(new ChatComponentTranslation("msg.da.chaosInjection.start"));
							chestNBT.setBoolean("injecting", true);
						}
					}
					else {
						player.addChatMessage(new ChatComponentTranslation("msg.da.chaosInjection.noupgrade"));
					}
				}
				else {
					player.addChatMessage(new ChatComponentTranslation("msg.da.chaosInjection.incompatible"));
				}
			});
			return null;
		}
	}
}
