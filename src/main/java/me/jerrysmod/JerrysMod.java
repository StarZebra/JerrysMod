package me.jerrysmod;

import me.jerrysmod.events.RenderEntityModelEvent;
import me.jerrysmod.events.TickEndEvent;
import me.jerrysmod.features.DungeonMeterRuns;
import me.jerrysmod.features.NotSoSneakyCreeper;
import me.jerrysmod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.collection.parallel.ParIterableLike;

import java.io.File;

@Mod(modid = JerrysMod.MOD_ID, name = "JerrysMod", version = "1.0.0", clientSideOnly = true)
public class JerrysMod {
    
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final File dir = new File(new File(mc.mcDataDir, "config"), "jerry");
    public static final String MOD_ID = "jerryaddons";
    
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        if(!dir.exists()) dir.mkdirs();
        
        
        
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TickEndEvent());
        MinecraftForge.EVENT_BUS.register(new Utils());
        
        MinecraftForge.EVENT_BUS.register(new DungeonMeterRuns());
        MinecraftForge.EVENT_BUS.register(new NotSoSneakyCreeper());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
    
    }
}
