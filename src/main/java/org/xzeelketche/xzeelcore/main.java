package org.xzeelketche.xzeelcore;

import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin XzeelCore berhasil dinyalakan!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin XzeelCore berhasil dimatikan!");
    }
}
