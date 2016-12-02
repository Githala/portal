package nl.arnedeboth.minecraft.portal;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Portals {

  public static Logger _logger = Bukkit.getLogger();

  private static HashMap<Block, Portal> portals = new HashMap<>();

  public static boolean tryAdd(Portal portal)
  {
    List<Portal> portalsWithName = portals.entrySet().stream().filter(map -> map.getValue().getPortalName().equals(portal.getPortalName())).map(p -> p.getValue()).collect(Collectors.toList());

    if (portalsWithName.size() < 2) {

      if (portalsWithName.size() == 1)
      {
        Portal existingPortal = portalsWithName.get(0);
        existingPortal.setPair(portal);
        portal.setPair(existingPortal);
      }

      portals.put(portal.getSign(), portal);

      return true;
    } else {
      return false;
    }
  }

  public static boolean portalAtBlock(Block block)
  {
    return portals.containsKey(block);
  }

  public static Portal getPortalFromBlock(Block block)
  {
    return portals.get(block);
  }

  public static void remove(Portal portal)
  {
    portal.getPair().setPair(null);
    portal.setPair(null);
    portals.remove(portal.getSign());

  }

  public static void save()
  {
    _logger.info("Saving portals to file.");

    try {
      FileWriter fw = new FileWriter("./portals.txt", false);
      BufferedWriter bw = new BufferedWriter(fw);

      portals.values().forEach(p -> {
        String txtData = p.asTextData();
        try {
          bw.write(txtData);
          bw.newLine();
        } catch (IOException e) {
          _logger.severe(e.getMessage());
        }
      });

      bw.flush();
      bw.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void load()
  {
    _logger.info("Loading portals from file.");
    try {
      FileReader fr = new FileReader("./portals.txt");
      BufferedReader br = new BufferedReader(fr);

      br.lines().forEach(line -> {
        Portal portal = Portal.fromTextData(line);
        tryAdd(portal);
      });

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
