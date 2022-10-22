package me.jerrysmod;

import me.jerrysmod.commands.GlassFinderCommand;
import me.jerrysmod.commands.MiningTickCommand;
import me.jerrysmod.commands.TickMiningCommand;
import me.jerrysmod.events.TickEndEvent;
import me.jerrysmod.features.*;
import me.jerrysmod.utils.KeybindUtils;
import me.jerrysmod.utils.LocationUtils;
import me.jerrysmod.utils.SmoothRotation;
import me.jerrysmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = JerrysMod.MOD_ID, name = "JerrysMod", version = "1.0.0", clientSideOnly = true)
public class JerrysMod {
    
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final File dir = new File(new File(mc.mcDataDir, "config"), "jerry");
    public static final String MOD_ID = "jerrysmod";
    public static final String MOD_NAME = "JerrysMod";
    
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        if(!dir.exists()) dir.mkdirs();
    
        ClientCommandHandler.instance.registerCommand(new MiningTickCommand());
        ClientCommandHandler.instance.registerCommand(new TickMiningCommand());
        ClientCommandHandler.instance.registerCommand(new GlassFinderCommand());
        
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new LocationUtils());
        MinecraftForge.EVENT_BUS.register(new TickEndEvent());
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(new ColoredGlassFinder());
        //MinecraftForge.EVENT_BUS.register(new DungeonMeterRuns());
        MinecraftForge.EVENT_BUS.register(new NotSoSneakyCreeper());
        MinecraftForge.EVENT_BUS.register(new RatFinder());
        MinecraftForge.EVENT_BUS.register(new PowderMarco());
        MinecraftForge.EVENT_BUS.register(new SmoothRotation());
        MinecraftForge.EVENT_BUS.register(new BudgetPetRules());
        //left click block event broken for some reason
        //MinecraftForge.EVENT_BUS.register(new FakeMining());
        //MinecraftForge.EVENT_BUS.register(new LoreChangeEvent());
    
        for(KeyBinding keyBinding : KeybindUtils.keyBindings.values()) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
    
    }
}
