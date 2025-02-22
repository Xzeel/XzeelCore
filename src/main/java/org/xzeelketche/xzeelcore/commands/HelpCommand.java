package org.xzeelketche.xzeelcore.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Warna gradient untuk judul
            Component title = Component.text()
                    .append(Component.newline())
                    .append(Component.text("XzeelCore Help", TextColor.fromHexString("#31a9fc")))
                    .append(Component.text(" ✨", TextColor.fromHexString("#FFD700")))
                    .decorate(TextDecoration.BOLD)
                    .build();

            // Pesan bantuan
            Component helpMessage = Component.text()
                    .append(Component.newline())
                    .append(Component.text("Commands:", TextColor.fromHexString("#1E90FF")).decorate(TextDecoration.BOLD))
                    .append(Component.newline())
                    .append(Component.text("▶ /help", TextColor.fromHexString("#FFA500")))
                    .append(Component.text(" - Show this help menu.", TextColor.fromHexString("#FFFFFF")))
                    .append(Component.newline())
                    .append(Component.text("Support: ", TextColor.fromHexString("#1E90FF")).decorate(TextDecoration.BOLD))
                    .append(Component.text("Contact admin for assistance.", TextColor.fromHexString("#FFFFFF")))
                    .append(Component.newline())
                    .build();

            // Kirim pesan ke pemain
            player.sendMessage(title);
            player.sendMessage(helpMessage);
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }
        return true;
    }
}
