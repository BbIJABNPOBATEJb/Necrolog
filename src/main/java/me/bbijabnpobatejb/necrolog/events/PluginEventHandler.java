package me.bbijabnpobatejb.necrolog.events;

import me.bbijabnpobatejb.necrolog.object.DeathInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.bbijabnpobatejb.necrolog.Necrolog.*;
import static me.bbijabnpobatejb.necrolog.commands.NLExecutor.*;
import static me.bbijabnpobatejb.necrolog.enums.Message.ERROR_WRITE;

public class PluginEventHandler implements Listener {


    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        DeathInfo deathInfo = DeathInfo.create(event);
        if (deathInfo == null) {
            sendConsole(ERROR_WRITE + player.getName());
            return;
        }

        LIST_DEATH_INFO.add(deathInfo);
        getPlugin().configHandler.saveData();

        if (getPlugin().messageAfterDeath()) {
            sayDeathMessage(event);
        }
        if (getPlugin().soundAfterDeath()) {
            playSoundForPlayers(Sound.ENTITY_LIGHTNING_BOLT_THUNDER);
        }
        if (getPlugin().spectatorAfterDeath()) {
            player.setGameMode(GameMode.SPECTATOR);
        }
        if (getPlugin().banAfterDeath()) {
            runTaskLater(() -> player.banPlayer(getPlugin().banMessage()), 1);
        }
    }


    public void sayDeathMessage(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Component c = event.deathMessage();
        if (c != null) {
            c = c.color(TextColor.color(redColor));
            int x = (int) player.getX();
            int y = (int) player.getY();
            int z = (int) player.getZ();
            String world = player.getWorld().getName();
            c = c.append(colorText("\nx: " + x + " y: " + y + " z: " + z + " world: " + world, redColor2));
            event.deathMessage(c);
        }

    }

    public static Component colorText(String s, int color) {
        return Component.text(s).color(TextColor.color(color));
    }


}
