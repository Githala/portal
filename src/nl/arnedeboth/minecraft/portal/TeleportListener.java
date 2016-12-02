package nl.arnedeboth.minecraft.portal;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeleportListener implements Listener {

  @EventHandler
  public void onRightClick(PlayerInteractEvent e)
  {
    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
    {
      Block block = e.getClickedBlock();

      if (Portals.portalAtBlock(block))
      {
        Portals.getPortalFromBlock(block).teleport(e.getPlayer());
      }
    }
  }
}
