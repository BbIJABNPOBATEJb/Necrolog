package me.bbijabnpobatejb.necrolog.object;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeathInfo implements ConfigurationSerializable {

    public String playerUUID;
    public String playerName;
    public long deathTime;
    public DeathMessage deathMessage;

    public DeathInfo() {
    }

    public DeathInfo( String playerName,String playerUUID, long deathTime, DeathMessage deathMessage) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.deathTime = deathTime;
        this.deathMessage = deathMessage;
    }

    @Nullable
    public static DeathInfo create(PlayerDeathEvent event) {
        DeathInfo deathInfo = new DeathInfo();
        Player player = event.getPlayer();
        deathInfo.playerUUID = player.getUniqueId().toString();
        deathInfo.playerName = player.getName();
        deathInfo.deathTime = System.currentTimeMillis();
        DeathMessage message = DeathMessage.createDeathMessage(event);
        if (message == null) return null;
        deathInfo.deathMessage = message;
        return deathInfo;
    }

    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("playerUUID", this.playerUUID);
        data.put("playerName", this.playerName);
        data.put("deathTime", this.deathTime);
        data.put("deathMessage", this.deathMessage.serialize());

        return data;
    }

    public static DeathInfo deserialize(Map<String, Object> args) {
        return new DeathInfo(
                (String) args.get("playerName"),
                (String) args.get("playerUUID"),
                (Long) args.get("deathTime"),
                DeathMessage.deserialize((Map<String, Object>) args.get("deathMessage"))
        );
    }
    public String getDate(long l) {
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    int color1 = 0xF59D77;
    public Component getText(){
        String date = getDate(deathTime);
        Component message = Component.text(date + " - ").color(NamedTextColor.GRAY);

        HoverEvent<Component> hoverEvent = HoverEvent.showText(
                Component.text("Nickname " + playerName + "\n").append(
                Component.text("UUID " + playerUUID))
        );
        Component fullMessage = deathMessage.getFullMessage().hoverEvent(hoverEvent);
        message = message.append(fullMessage.color(TextColor.color(color1)));
        return message;
    }
}
