package cn.fusionfish.libs.command;

import cn.fusionfish.libs.plugin.FusionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();
    private final Plugin plugin;
    private final CommandMap commandMap;

    public CommandManager() {
        this.plugin = FusionPlugin.getInstance();
        commandMap = getCommandMap();
    }

    public void registerCommand(SimpleCommand command) {

        commandMap.register(plugin.getName(), command);
        commands.put(command.getLabel(), command);

    }

    public void unregisterCommands() {

        try {

            @SuppressWarnings("unchecked")
            Map<String, Command> knownCommands = (Map<String, Command>) commandMap.getClass().getMethod("getKnownCommands").invoke(commandMap);

            knownCommands.values().removeIf(commands.values()::contains);
            commands.values().forEach(c -> c.unregister(commandMap));
            commands.clear();

        } catch(Exception e){

            Bukkit.getLogger().severe("卸载命令异常, 请重启服务器!");

        }

    }

    private static CommandMap getCommandMap() {

        try {

            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            return (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        } catch (NoSuchFieldException | IllegalAccessException e) {

            e.printStackTrace();

        }

        return null;
    }

}

