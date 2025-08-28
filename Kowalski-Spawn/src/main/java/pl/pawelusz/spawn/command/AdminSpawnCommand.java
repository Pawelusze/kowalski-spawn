package pl.pawelusz.spawn.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.pawelusz.spawn.SpawnLocation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class AdminSpawnCommand implements TabExecutor {

    private final JavaPlugin plugin;
    private final File file;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AdminSpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getDataFolder().mkdirs();
        this.file = new File(plugin.getDataFolder(), "location.json");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (commandSender instanceof Player player) {
            if (!(player.hasPermission("spawn.admin"))) {
                player.sendRichMessage("<red>Nie posiadasz odpowiedniej permisji! :(");
                return true;
            }

            // /adminspawn <setspawn>
            if (strings[0].equals("setspawn")) {
                File tmp = new File(file.getParentFile(), file.getName() + ".tmp");

                    Location playerLocation = player.getLocation();
                    SpawnLocation spawnLocation = new SpawnLocation(playerLocation);

                try (Writer writer = new OutputStreamWriter(new FileOutputStream(tmp), StandardCharsets.UTF_8)) {
                    gson.toJson(spawnLocation, writer);

                    Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.ATOMIC_MOVE);
                    player.sendRichMessage("<green>Ustawiono nową lokalizacje spawnu");
                } catch (Exception e) {
                    try { Files.deleteIfExists(tmp.toPath()); } catch (Exception ignore) {
                        plugin.getLogger().log(Level.SEVERE, "Nie można zapisać do '" + file.getAbsolutePath() + "'", e);
                        player.sendRichMessage("<red>Nie udało się zapisać spawnu Sprawdź logi.");
                    }

                }
            } else {
                player.sendRichMessage("<red>Komenda zostala uzyta niepoprawnie! :(");
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
         if (strings.length == 0) {
             Arrays.asList("setspawn");
         }
        return new ArrayList<>();
    }
}
