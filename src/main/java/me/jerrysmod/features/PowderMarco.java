package me.jerrysmod.features;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.events.ReceivePacketEvent;
import me.jerrysmod.utils.KeybindUtils;
import me.jerrysmod.utils.RenderUtils;
import me.jerrysmod.utils.SmoothRotation;
import me.jerrysmod.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class PowderMarco {
	
	public PowderMarco() {
		KeybindUtils.register("Powder Marco", Keyboard.KEY_H);
	}
	
	public static boolean toggled = false;
	
	private static Vec3 closestChest = null;
	
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event){
		if(KeybindUtils.get("Powder Marco").isPressed()){
			toggled = !toggled;
			String string = toggled ? "§aPowder Marco activated." : "§cPowder Marco deactivated.";
			Utils.sendMessageWithPrefix(string);
		}
	}
	
	@SubscribeEvent
	public void receivePacket(ReceivePacketEvent event) {
		if (!toggled) return;
		if (event.packet instanceof S2APacketParticles) {
			S2APacketParticles packet = (S2APacketParticles) event.packet;
			if (packet.getParticleType().equals(EnumParticleTypes.CRIT)) {
				Vec3 particlePos = new Vec3(packet.getXCoordinate(), packet.getYCoordinate(), packet.getZCoordinate());
				if (closestChest != null) {
					double dist = closestChest.distanceTo(particlePos);
					if (dist < 1) {
						SmoothRotation.smoothLook(SmoothRotation.vec3ToRotation(particlePos), 5, () -> {});
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent event) {
		if (!toggled) return;
		closestChest = closestChest();
		if (closestChest != null) {
			RenderUtils.outlineBlock(new BlockPos(closestChest.xCoord, closestChest.yCoord, closestChest.zCoord), new Color(255, 128, 0), event.partialTicks);
		}
	}
	
	private static Vec3 closestChest() {
		int r = 6;
		BlockPos playerPos = JerrysMod.mc.thePlayer.getPosition();
		playerPos.add(0, 1, 0);
		Vec3 playerVec = JerrysMod.mc.thePlayer.getPositionVector();
		Vec3i vec3i = new Vec3i(r, r, r);
		ArrayList<Vec3> chests = new ArrayList<Vec3>();
		if (playerPos != null) {
			for (BlockPos blockPos : BlockPos.getAllInBox(playerPos.add(vec3i), playerPos.subtract(vec3i))) {
				IBlockState blockState = JerrysMod.mc.theWorld.getBlockState(blockPos);
				if (blockState.getBlock() == Blocks.chest) {
					chests.add(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5));
				}
			}
		}
		double smallest = 9999;
		Vec3 closest = null;
		for (Vec3 chest : chests) {
			double dist = chest.distanceTo(playerVec);
			if (dist < smallest) {
				smallest = dist;
				closest = chest;
			}
		}
		return closest;
	}
	
	@SubscribeEvent
	public void onWorldChange(WorldEvent.Load event) {
		toggled = false;
	}
}
