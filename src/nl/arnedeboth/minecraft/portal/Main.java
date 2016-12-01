package nl.arnedeboth.minecraft.portal;

import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Main extends JavaPlugin {

  public static HashMap<Block, Portal> portals;

  @Override
  public void onEnable() {
    portals = new HashMap<>();
    getServer().getPluginManager().registerEvents(new PortalCreatedListener(), this);
    getServer().getPluginManager().registerEvents(new TeleportListener(), this);
  }

  @Override
  public void onDisable() {

  }

  public static void save()
  {
    try {
      FileWriter fw = new FileWriter("./portals.txt", false);
      BufferedWriter bw = new BufferedWriter(fw);

      portals.values().forEach(p -> {
        String txtData = p.asTextData();
        try {
          bw.write(txtData);
          bw.newLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });



    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void load()
  {
    try {
      FileReader fr = new FileReader("./portals.txt");
      BufferedReader br = new BufferedReader(fr);

      br.lines().forEach(line -> {
        Portal portal = Portal.fromTextData(line);
        portals.put(portal.getSign(), portal);
      });

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
