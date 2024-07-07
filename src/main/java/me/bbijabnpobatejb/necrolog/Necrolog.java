package me.bbijabnpobatejb.necrolog;

import me.bbijabnpobatejb.necrolog.commands.NLCompleter;
import me.bbijabnpobatejb.necrolog.commands.NLExecutor;
import me.bbijabnpobatejb.necrolog.enums.Message;
import me.bbijabnpobatejb.necrolog.events.PluginEventHandler;
import me.bbijabnpobatejb.necrolog.object.PluginHelper;

import static me.bbijabnpobatejb.necrolog.enums.Message.PLUGIN_DISABLE;
import static me.bbijabnpobatejb.necrolog.enums.Message.PLUGIN_START;


public final class Necrolog extends PluginHelper {

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        registerCommand(SIMPLE_PLUGIN_NAME, new NLExecutor());
        registerTabCompleter(SIMPLE_PLUGIN_NAME, new NLCompleter());

        registerEvent(new PluginEventHandler());

        configHandler.loadAll();

        sendConsole(PLUGIN_START.getMessage());

    }


    @Override
    public void onDisable() {
        configHandler.saveAll();
        sendConsole(PLUGIN_DISABLE.getMessage());
    }

}
