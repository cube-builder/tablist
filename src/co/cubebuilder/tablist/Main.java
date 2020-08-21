package co.cubebuilder.tablist;

import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;


public class Main  extends JavaPlugin implements Listener  {
	
	private boolean titlechanged = false;

    @Override
    public void onEnable() {
        //this.getServer().getPluginManager().registerEvents(this, this);
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        new BukkitRunnable() {

            @Override
            public void run() {
                try {
                    Field a = packet.getClass().getDeclaredField("a");
                    a.setAccessible(true);
                    Field b = packet.getClass().getDeclaredField("b");
                    b.setAccessible(true);

                    Object header1 = new ChatComponentText("\n§42builders§32tools\n \n §6 This is the BEST ANarchy Server. RULES Don't insult the owner or else im gonna dox you and leak your ip \n ");
                    Object header2 = new ChatComponentText("\n§42builders§32tools\n \n §6  \n  ");

                    Object footer = new ChatComponentText("\n§3Players Online: §f" + Bukkit.getServer().getOnlinePlayers().size());
                    if (titlechanged) {
                        a.set(packet, header2);
                        titlechanged = false;

                    } else {
                        a.set(packet, header1);
                        titlechanged = true;
                    }
                    b.set(packet, footer);

                    if (Bukkit.getOnlinePlayers().size() == 0) return;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    }

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

}
