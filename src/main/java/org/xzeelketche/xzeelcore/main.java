package org.xzeelketche.xzeelcore;

import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("+---------------------------------+");
        getLogger().info("");
        getLogger().info("XzeelCore plugin started successfully!");
        getLogger().info("");
        getLogger().info("+---------------------------------+");
    }

    @Override
    public void onDisable() {
        getLogger().info("+---------------------------------+");
        getLogger().info("");
        getLogger().info("XzeelCore plugin shut down successfully!");
        getLogger().info("");
        getLogger().info("+---------------------------------+");
    }
}
