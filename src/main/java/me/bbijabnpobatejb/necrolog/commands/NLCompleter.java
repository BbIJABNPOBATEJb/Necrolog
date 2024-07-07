package me.bbijabnpobatejb.necrolog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NLCompleter implements TabCompleter {
    public final CommandList commandList;

    public NLCompleter() {
        this.commandList = new CommandList();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        List<String> list = commandList.getList(sender);

        if (args.length != 1) return new ArrayList<>(1);

        String input = args[0].toLowerCase();
        List<String> completions = list.stream()
                .filter(value -> value.toLowerCase().startsWith(input))
                .collect(Collectors.toList());
        if (!completions.isEmpty()) {
            Collections.sort(completions);
        }
        return completions;


    }
}
