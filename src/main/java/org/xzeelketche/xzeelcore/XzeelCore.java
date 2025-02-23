package org.xzeelketche.xzeelcore;

import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.xzeelketche.xzeelcore.commands.HelpCommand;

public final class XzeelCore extends JavaPlugin {

    @Override
    public void onLoad() {
        // Load or create config
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        UpdateChecker updateChecker = new UpdateChecker(this);
        updateChecker.registerUpdateCommand();
        getServer().getPluginManager().registerEvents(updateChecker, this);

        // Watermark (onEnable)
        Component watermarkMessage = Component.text()
                .append(Component.text("\n" +
                        " ㅤㅤ__  __ __________ _____ _     ____ ___  ____  _____ \n" +
                        " ㅤㅤ\\ \\/ /|__  / ____| ____| |   / ___/ _ \\|  _ \\| ____|\n" +
                        "  ㅤㅤ\\  /   / /|  _| |  _| | |  | |  | | | | |_) |  _|  \n" +
                        "  ㅤㅤ/  \\  / /_| |___| |___| |__| |__| |_| |  _ <| |___ \n" +
                        " ㅤㅤ/_/\\_\\/____|_____|_____|_____\\____\\___/|_| \\_\\_____|\n" +
                        "\n", TextColor.fromHexString("#31a9fc")))
                .build();

        // Pesan onEnable dengan gaya modern
        Component enableMessage = Component.text()
                .append(Component.text("[" + getName() + "] ", TextColor.fromHexString("#31a9fc")).decorate(TextDecoration.BOLD))
                .append(Component.text("Plugin XzeelCore has been ", TextColor.fromHexString("#FFFFFF")))
                .append(Component.text("enabled", TextColor.fromHexString("#31a9fc")).decorate(TextDecoration.BOLD))
                .append(Component.text("! :)", TextColor.fromHexString("#FFD700")))
                .append(Component.text("\n", TextColor.fromHexString("#FFFFFF")))
                .append(Component.text("ㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤVersion: ", TextColor.fromHexString("#FFFFFF")))
                .append(Component.text(getDescription().getVersion(), TextColor.fromHexString("#31a9fc")))
                .append(Component.text("\n", TextColor.fromHexString("#FFFFFF")))
                .build();

        // Kirim pesan ke console
        getServer().getConsoleSender().sendMessage(watermarkMessage);
        getServer().getConsoleSender().sendMessage(enableMessage);

        // Register commands
        this.getCommand("help").setExecutor(new HelpCommand());

        saveResource("config.yml", true);
    }

    @Override
    public void onDisable() {
        // Watermark (onDisable)
        Component watermarkMessage = Component.text()
                .append(Component.text("\n" +
                        " ㅤㅤ__  __ __________ _____ _     ____ ___  ____  _____ \n" +
                        " ㅤㅤ\\ \\/ /|__  / ____| ____| |   / ___/ _ \\|  _ \\| ____|\n" +
                        "  ㅤㅤ\\  /   / /|  _| |  _| | |  | |  | | | | |_) |  _|  \n" +
                        "  ㅤㅤ/  \\  / /_| |___| |___| |__| |__| |_| |  _ <| |___ \n" +
                        " ㅤㅤ/_/\\_\\/____|_____|_____|_____\\____\\___/|_| \\_\\_____|\n" +
                        "\n", TextColor.fromHexString("#31a9fc")))
                .build();

        // Create a formatted component to notify console that the plugin is now disabled
        Component disableMessage = Component.text()
                .append(Component.text("[" + getName() + "] ", TextColor.fromHexString("#FF6347")).decorate(TextDecoration.BOLD))
                .append(Component.text("Plugin XzeelCore has been ", TextColor.fromHexString("#FFFFFF")))
                .append(Component.text("disabled", TextColor.fromHexString("#FF6347")).decorate(TextDecoration.BOLD))
                .append(Component.text("! :(", TextColor.fromHexString("#FF0000")))
                .append(Component.text("\n", TextColor.fromHexString("#FFFFFF")))
                .append(Component.text("ㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤㅤVersion: ", TextColor.fromHexString("#FFFFFF")))
                .append(Component.text(getDescription().getVersion(), TextColor.fromHexString("#FF6347")))
                .append(Component.text("\n", TextColor.fromHexString("#FFFFFF")))
                .build();

        // Send the message to the console
        getServer().getConsoleSender().sendMessage(watermarkMessage);
        getServer().getConsoleSender().sendMessage(disableMessage);
    }
}
