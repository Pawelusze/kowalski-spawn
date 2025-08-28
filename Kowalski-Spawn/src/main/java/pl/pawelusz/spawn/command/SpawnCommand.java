package pl.pawelusz.spawn.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.pawelusz.spawn.SpawnLocation;
import pl.pawelusz.spawn.SpawnPlugin;

import java.io.*;

public class SpawnCommand implements CommandExecutor {

    private final SpawnPlugin spawnPlugin;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SpawnCommand(SpawnPlugin spawnPlugin) {
        this.spawnPlugin = spawnPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (commandSender instanceof Player player) {
            try (FileReader reader = new FileReader(SpawnPlugin.getLocationFile(spawnPlugin))) {
                SpawnLocation deserializedLocation = gson.fromJson(reader, SpawnLocation.class);
                Location finalLocation = deserializedLocation.correctLocation();

                if (finalLocation == null) {
                    player.sendRichMessage("<red>Spawn nie jest jeszcze ustawiony!");
                    return true;
                }

                player.teleport(finalLocation);
                player.sendRichMessage("<green>Pomyslnie przeteleportowano na spawna!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}