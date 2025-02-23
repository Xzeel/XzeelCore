package org.xzeelketche.xzeelcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements Listener {

    private final JavaPlugin plugin;
    private final String localVersion;
    private String remoteVersion;
    private boolean updateAvailable = false;
    private static final String VERSION_URL = "https://github.com/Xzeel/XzeelCore/releases";
    private static final long CHECK_INTERVAL = 72000; // Dalam ticks (1 jam)

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
        this.localVersion = plugin.getDescription().getVersion();
        startUpdateTask();
    }

    private void startUpdateTask() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, () -> {
            try {
                checkForUpdates();
                if(updateAvailable) {
                    plugin.getLogger().info("Update tersedia! Versi terbaru: " + remoteVersion);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Gagal memeriksa update: " + e.getMessage());
            }
        }, 0L, CHECK_INTERVAL);
    }

    private void checkForUpdates() throws Exception {
        URL url = new URL(VERSION_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        remoteVersion = reader.readLine().trim();
        reader.close();

        updateAvailable = isNewerVersion(localVersion, remoteVersion);
    }

    private boolean isNewerVersion(String local, String remote) {
        String[] localParts = local.split("\\.");
        String[] remoteParts = remote.split("\\.");

        for(int i = 0; i < Math.min(localParts.length, remoteParts.length); i++) {
            int localNum = Integer.parseInt(localParts[i]);
            int remoteNum = Integer.parseInt(remoteParts[i]);

            if(remoteNum > localNum) return true;
            if(remoteNum < localNum) return false;
        }
        return remoteParts.length > localParts.length;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("xzeelcore.update") && updateAvailable) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.sendMessage(ChatColor.YELLOW + "[XzeelCore] Versi terbaru " + remoteVersion + " tersedia!");
                player.sendMessage(ChatColor.YELLOW + "Download di: https://github.com/Xzeel/XzeelCore/releases");
            }, 100L); // Delay 5 detik
        }
    }

    // Command untuk manual check
    public void registerUpdateCommand() {
        plugin.getCommand("xzeelcoreupdate").setExecutor((sender, command, label, args) -> {
            if(sender.hasPermission("xzeelcore.update")) {
                sender.sendMessage(ChatColor.YELLOW + "Memeriksa update...");
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        checkForUpdates();
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            if(updateAvailable) {
                                sender.sendMessage(ChatColor.YELLOW + "[XzeelCore] Versi terbaru " + remoteVersion + " tersedia!");
                            } else {
                                sender.sendMessage(ChatColor.GREEN + "Plugin sudah menggunakan versi terbaru!");
                            }
                        });
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "Gagal memeriksa update: " + e.getMessage());
                    }
                });
            }
            return true;
        });
    }
}
