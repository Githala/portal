package nl.arnedeboth.minecraft.portal;


import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class PortalCreatedListener implements Listener {
  @EventHandler
  public void onBlockPlace(SignChangeEvent e) {
    String[] lines = e.getLines();

    Block block = e.getBlock();

    // If the sign starts with [portal], apply the portal plugin to it.
    if (lines[0].equals("[portal]")) {


      BlockFace face = ((org.bukkit.material.Sign) e.getBlock().getState().getData()).getAttachedFace();
      String portalName = lines[1];


      List<Portal> portalsWithName = Main.portals.entrySet().stream().filter(map -> map.getValue().getPortalName().equals(portalName)).map(p -> p.getValue()).collect(Collectors.toList());

      if (portalsWithName.size() < 2) {
        Portal portal = new Portal(block, portalName);

        if (portalsWithName.size() == 1)
        {
          Portal existingPortal = portalsWithName.get(0);
          existingPortal.setPair(portal);
          portal.setPair(existingPortal);
        }

        Main.portals.put(block, portal);

        Main.save();
      } else {
        e.setLine(0, "ERROR: Portal pair");
        e.setLine(1, "already exists!");
        e.setLine(2, "Please try");
        e.setLine(3, "another name");
      }
    }
  }

  public void onBlockRemove(BlockBreakEvent e) {
    if (Main.portals.containsKey(e.getBlock()))
    {
      Portal portal = Main.portals.get(e.getBlock());
      portal.getPair().setPair(null);
      portal.setPair(null);

      Main.portals.remove(e.getBlock());
    }
  }
}

