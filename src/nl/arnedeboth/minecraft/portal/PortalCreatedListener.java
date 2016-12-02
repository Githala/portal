package nl.arnedeboth.minecraft.portal;


import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

public class PortalCreatedListener implements Listener {
  @EventHandler
  public void onBlockPlace(SignChangeEvent e) {
    String[] lines = e.getLines();

    Block block = e.getBlock();

    // If the sign starts with [portal], apply the portal plugin to it.
    if (lines[0].equals("[portal]")) {


      BlockFace face = ((org.bukkit.material.Sign) e.getBlock().getState().getData()).getAttachedFace();
      String portalName = lines[1];


      if (Portals.tryAdd(new Portal(block, portalName)))
      {
        Portals.save();
      } else
      {
        e.setLine(0, "ERROR: Portal pair");
        e.setLine(1, "already exists!");
        e.setLine(2, "Please try");
        e.setLine(3, "another name");
      }
    }
  }

  @EventHandler
  public void onBlockRemove(BlockBreakEvent e) {
    if (Portals.portalAtBlock(e.getBlock()))
    {
      Portal portal = Portals.getPortalFromBlock(e.getBlock());
      Portals.remove(portal);
      Portals.save();
    }
  }
}

