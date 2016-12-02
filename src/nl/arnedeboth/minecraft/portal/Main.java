package nl.arnedeboth.minecraft.portal;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

  public static Logger _logger = Bukkit.getLogger();

  @Override
  public void onEnable() {
    _logger.info("Loading Portal plugin.");


    getServer().getPluginManager().registerEvents(new PortalCreatedListener(), this);
    getServer().getPluginManager().registerEvents(new TeleportListener(), this);

    Portals.load();
  }

  @Override
  public void onDisable() {
    Portals.save();
  }
}
