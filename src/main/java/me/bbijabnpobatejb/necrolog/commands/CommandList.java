package me.bbijabnpobatejb.necrolog.commands;

import me.bbijabnpobatejb.necrolog.enums.Message;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandList {

    public final List<String> admin = Arrays.asList(
            "reloadCfg",
            "clearAll"
    );
    public final List<String> player = Arrays.asList(
    );

    public List<String> getList(CommandSender sender) {
        List<String> list = new ArrayList<>(player);
        if (sender.isOp()) {
            list.addAll(new ArrayList<>(admin));
        }
        return list;
    }

    public String getUsage(CommandSender sender) {
        List<String> list = getList(sender);
        String s = "<";
        for (String string : list) {
            s += string;
            if (list.indexOf(string) != list.size() - 1) {
                s += " | ";
            }
        }
        s += ">";
        return s;
    }

    public void sendUsage(CommandSender sender) {
        String usage = getUsage(sender);
        sender.sendMessage(Message.USAGE + usage);
    }
}
