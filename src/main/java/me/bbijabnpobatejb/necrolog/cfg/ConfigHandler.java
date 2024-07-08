package me.bbijabnpobatejb.necrolog.cfg;

import me.bbijabnpobatejb.necrolog.object.DeathInfo;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.bbijabnpobatejb.necrolog.Necrolog.*;
import static me.bbijabnpobatejb.necrolog.enums.Message.RELOAD_CFG;

public class ConfigHandler {
    public ConfigHandler(JavaPlugin plugin) {

        this.plugin = plugin;
        File folder = plugin.getDataFolder();
        this.dataFile = new File(folder, "data.yml");

    }

    public final JavaPlugin plugin;
    private final File dataFile;
    private final String deathList = "deathList";


    public void saveData() {

        List<Map<String, Object>> serializedList = new ArrayList<>();

        Map<String, Object> data = new HashMap<>();
        data.put("LIST_DEATH_INFO", LIST_DEATH_INFO);
        serializedList.add(data);

        YamlConfiguration yml = new YamlConfiguration();

        for (DeathInfo deathInfo : LIST_DEATH_INFO) {
            serializedList.add(deathInfo.serialize());
        }
        yml.set(deathList, serializedList);

        saveFileData();
    }


    public void saveFileData() {
        YamlConfiguration cfg = new YamlConfiguration();

        List<Map<String, Object>> serializedList = new ArrayList<>();
        for (DeathInfo deathInfo : LIST_DEATH_INFO) {
            serializedList.add(deathInfo.serialize());
        }
        cfg.set(deathList, serializedList);

        try {
            cfg.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data.yml");
        }
    }


    public void loadData() {
        List<DeathInfo> list = getDeathMessage();
        LIST_DEATH_INFO.addAll(list);
        saveFileData();
    }

    public void loadAll() {
        boolean b = false;

        reloadCfg();

        if (!dataFile.exists()) {
            b = true;
            saveData();
        }
        if (b) return;

        loadData();
    }

    public void saveAll() {
        this.saveData();
    }


    public List<DeathInfo> getDeathMessage() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(dataFile);
        List<Map<String, Object>> serializedList = (List<Map<String, Object>>) yml.get(deathList);

        List<DeathInfo> list = new ArrayList<>();

        if (serializedList == null) return new ArrayList<>();
        for (Map<String, Object> serializedData : serializedList) {
            list.add(DeathInfo.deserialize(serializedData));
        }
        return list;
    }

    public List<String> reloadCfg() {
        getPlugin().reloadConfig();
        getPlugin().saveDefaultConfig();
        List<String> list = new ArrayList<>();

        list.add(RELOAD_CFG.getMessage());
        list.add("  §7language §a" + getPlugin().language().toString());
        list.add("  §7banMessage §a" + getPlugin().banMessage());
        list.add("  §7banAfterDeath §a" + getPlugin().banAfterDeath());
        list.add("  §7messageAfterDeath §a" + getPlugin().messageAfterDeath());
        list.add("  §7soundAfterDeath §a" + getPlugin().soundAfterDeath());
        list.add("  §7spectatorAfterDeath §a" + getPlugin().spectatorAfterDeath());
        list.add("  §7logMessage §a" + getPlugin().logMessage().replace("§","&"));

        return list;
    }
}
