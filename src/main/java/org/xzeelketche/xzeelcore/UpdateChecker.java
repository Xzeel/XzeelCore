package org.xzeelketche.xzeelcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final String githubRepo;

    public UpdateChecker(JavaPlugin plugin, String githubRepo) {
        this.plugin = plugin;
        this.githubRepo = githubRepo;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(
                        "https://api.github.com/repos/" + githubRepo + "/releases/latest"
                ).openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
                connection.setConnectTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JSONObject response = (JSONObject) new JSONParser().parse(reader);

                String latestVersion = ((String) response.get("tag_name")).replace("v", "");
                String currentVersion = plugin.getDescription().getVersion().replace("v", "");

                if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                    plugin.getLogger().warning("═════════════════════════════════");
                    plugin.getLogger().warning("Latest version " + latestVersion + " available!");
                    plugin.getLogger().warning("Download at: https://github.com/" + githubRepo + "/releases");
                    plugin.getLogger().warning("═════════════════════════════════");
                } else {
                    plugin.getLogger().info("XzeelCore plugin is now using the latest version!");
                }

            } catch (Exception e) {
                plugin.getLogger().warning("Failed to check for updates: " + e.getMessage());
            }
        });
    }

    public boolean needsUpdate() {
        return !getLatestVersion().equalsIgnoreCase(plugin.getDescription().getVersion());
    }

    public String getLatestVersion() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(
                    "https://api.github.com/repos/" + githubRepo + "/releases/latest"
            ).openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JSONObject response = (JSONObject) new JSONParser().parse(reader);

            return (String) response.get("tag_name");
        } catch (Exception e) {
            return plugin.getDescription().getVersion();
        }
    }
}
