package me.bbijabnpobatejb.necrolog.object;

import me.bbijabnpobatejb.necrolog.Necrolog;
import me.bbijabnpobatejb.necrolog.cfg.ConfigHandler;
import me.bbijabnpobatejb.necrolog.enums.Language;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class PluginHelper extends JavaPlugin {
    public static final String PLUGIN_NAME = "Necrolog";
    public static final String SIMPLE_PLUGIN_NAME = "necrolog";
    public static final String PERM_USE = "necrolog.use";
    protected static Necrolog plugin;
    public static List<DeathInfo> LIST_DEATH_INFO = new ArrayList<>();
    public final ConfigHandler configHandler = new ConfigHandler(this);
    protected final String afterDeathPath = "afterDeath.";

    public String banMessage() {
        return getConfig().getString(afterDeathPath + "banMessage", "§cYou are dead");
    }

    public Language language() {
        String s = getConfig().getString("language", "en");
        try {
            return Language.valueOf(s);
        } catch (IllegalArgumentException e) {
            sendConsole("§cLanguage not '" + s + "' found ");
            getConfig().set("language", "en");
            saveConfig();
            return Language.en;
        }
    }

    public boolean banAfterDeath() {
        return getConfigState(afterDeathPath + "ban");
    }

    public boolean messageAfterDeath() {
        return getConfigState(afterDeathPath + "message");
    }


    public boolean soundAfterDeath() {
        return getConfigState(afterDeathPath + "sound");
    }

    public boolean spectatorAfterDeath() {
        return getConfigState(afterDeathPath + "spectator");
    }

    public boolean getConfigState(String path) {
        return getConfig().getBoolean(path, false);
    }

    public static Necrolog getPlugin() {
        return plugin;
    }

    protected void registerEvent(@NotNull Listener eventClass) {
        Bukkit.getPluginManager().registerEvents(eventClass, this);
    }


    public static final String PREFIX = "§7[§3" + PLUGIN_NAME + "§7] §7";

    public static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + message);
    }

    public static void sendChat(String message) {
        Bukkit.getServer().sendMessage(Component.text(PREFIX + message));
    }

    public static void playSoundForPlayers(Sound sound) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, SoundCategory.MASTER, 1f, 1f);
        }
    }

    public void registerCommand(String commandName, org.bukkit.command.CommandExecutor executor) {
        if (getCommand(commandName) != null) {
            Objects.requireNonNull(getCommand(commandName)).setExecutor(executor);
        } else {
            getLogger().warning("Command '" + commandName + "' is not registered!");
        }
    }

    protected void registerTabCompleter(String commandName, TabCompleter completer) {
        if (getCommand(commandName) != null) {
            Objects.requireNonNull(getCommand(commandName)).setTabCompleter(completer);
        } else {
            getLogger().warning("Command completer '" + commandName + "' is not registered!");
        }
    }

    public static void runTaskLater(Runnable runnable, int tick) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, tick);
    }

    public static void runTaskTimer(Consumer<BukkitTask> runnable, int tick) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, 0, tick);
    }
}
