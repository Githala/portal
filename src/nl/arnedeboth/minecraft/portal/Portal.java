package nl.arnedeboth.minecraft.portal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Portal {
  private final String portalName;
  //private List<Block> blocks = new ArrayList<>();
  private final Block sign;

  private Portal pair;

  public Portal(Block sign, String portalName)
  {
    this.sign = sign;
    this.portalName = portalName;
  }

//  public boolean isBlockInPortal(Block b)
//  {
//    return blocks.contains(b);
//  }
//
//  public List<Block> getBlocks()
//  {
//    return blocks;
//  }

  public Block getSign()
  {
    return sign;
  }

  public String getPortalName()
  {
    return portalName;
  }

  public Portal getPair()
  {
    return pair;
  }

  public void setPair(Portal portal)
  {
    this.pair = portal;
  }

  public void teleport(Player player)
  {
    if (pair != null) {
      Location location = pair.getSign().getLocation();

      player.teleport(location);
    }
  }

  public String asTextData()
  {
    Location loc = sign.getLocation();
    double x = loc.getX();
    double y = loc.getY();
    double z = loc.getZ();

    return portalName + " " + loc.getWorld().getName() + " " + x + " " + y + " " + z;
  }

  public static Portal fromTextData(String data)
  {
    String[] parts = data.split(" ");
    String portalName = parts[0];
    Location loc = new Location(Bukkit.getWorld(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));

    Block block = loc.getBlock();

    return new Portal(block, portalName);
  }


}
