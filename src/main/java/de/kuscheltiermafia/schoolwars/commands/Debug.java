/*
 * ███╗   ███╗ █████╗ ██████╗ ███████╗    ██████╗ ██╗   ██╗
 * ████╗ ████║██╔══██╗██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝
 * ██╔████╔██║███████║██║  ██║█████╗      ██████╔╝ ╚████╔╝
 * ██║╚██╔╝██║██╔══██║██║  ██║██╔══╝      ██╔══██╗  ╚██╔╝
 * ██║ ╚═╝ ██║██║  ██║██████╔╝███████╗    ██████╔╝   ██║
 * ╚═╝     ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝    ╚═════╝    ╚═╝
 *
 * ██╗  ██╗██╗   ██╗███████╗ ██████╗██╗  ██╗███████╗██╗  ████████╗██╗███████╗██████╗ ███╗   ███╗ █████╗ ███████╗██╗ █████╗
 * ██║ ██╔╝██║   ██║██╔════╝██╔════╝██║  ██║██╔════╝██║  ╚══██╔══╝██║██╔════╝██╔══██╗████╗ ████║██╔══██╗██╔════╝██║██╔══██╗
 * █████╔╝ ██║   ██║███████╗██║     ███████║█████╗  ██║     ██║   ██║█████╗  ██████╔╝██╔████╔██║███████║█████╗  ██║███████║
 * ██╔═██╗ ██║   ██║╚════██║██║     ██╔══██║██╔══╝  ██║     ██║   ██║██╔══╝  ██╔══██╗██║╚██╔╝██║██╔══██║██╔══╝  ██║██╔══██║
 * ██║  ██╗╚██████╔╝███████║╚██████╗██║  ██║███████╗███████╗██║   ██║███████╗██║  ██║██║ ╚═╝ ██║██║  ██║██║     ██║██║  ██║
 * ╚═╝  ╚═╝ ╚═════╝ ╚══════╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝   ╚═╝╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝
 *
 * This is a plugin from Morgon and CrAzyA22 - Unless explicitly stated otherwise you are not permitted to use any of the given code!
 *
 */

package de.kuscheltiermafia.schoolwars.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import de.kuscheltiermafia.schoolwars.SchoolWars;
import de.kuscheltiermafia.schoolwars.config.ProbabilityConfig;
import de.kuscheltiermafia.schoolwars.events.Minasisierung;
import de.kuscheltiermafia.schoolwars.events.SchulbuchLevels;
import de.kuscheltiermafia.schoolwars.lehrer.*;
import de.kuscheltiermafia.schoolwars.mechanics.Intro;
import de.kuscheltiermafia.schoolwars.mechanics.PlayerStun;
import de.kuscheltiermafia.schoolwars.mechanics.RevivePlayer;
import de.kuscheltiermafia.schoolwars.win_conditions.AtombombeBossfight;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.List;

import static de.kuscheltiermafia.schoolwars.lehrer.Lehrer.lehrerEntityList;
import static de.kuscheltiermafia.schoolwars.lehrer.Lehrer.lehrerList;
import static de.kuscheltiermafia.schoolwars.PlayerMirror.playerMirror;
import static de.kuscheltiermafia.schoolwars.lehrer.LehrerQuests.questLehrerList;

/**
 * Debug command handler providing administrative and testing utilities.
 * <p>
 * This command provides various subcommands for:
 * <ul>
 *   <li>Testing game mechanics (autism, stun, bossfight)</li>
 *   <li>Managing player state (revive, fly, verweise)</li>
 *   <li>Viewing and modifying teacher data (rep, lehrer, stundenplan)</li>
 *   <li>Toggling debug features (join messages, start command)</li>
 *   <li>Getting location information</li>
 * </ul>
 * </p>
 * <p>
 * All subcommands require the "schoolwars.debug.*" permission unless otherwise specified.
 * </p>
 */
@CommandAlias("debug")
@Description("Debug commands for SchoolWars")
public class Debug extends BaseCommand {

    /** List of players receiving join/leave debug messages. */
    public static ArrayList<Player> joinMsg = new ArrayList<>();

    /** Flag controlling whether the /start command is functional. */
    public static Boolean startWorks = true;

    /** Standard prefix for debug messages. */
    public static String debugPrefix = ChatColor.DARK_RED + "[!] " + ChatColor.GRAY;

    /**
     * Applies the "Minasisierung" (autism) effect to the sender.
     *
     * @param sender the command sender (must be a player)
     */
    @Subcommand("autism")
    @CommandPermission("schoolwars.debug.autism")
    @CommandCompletion("autism")
    @Description("Gives the player autism")
    public static void onAutismCommand(CommandSender sender){
        Minasisierung.onMinasisierung((Player) sender);
        sender.sendMessage(debugPrefix + "You now have autism!");
    }

    /**
     * Revives a player (self or specified target).
     *
     * @param sender the command sender (must be a player)
     * @param playerName optional target player name; if null, revives self
     */
    @Subcommand("revive")
    @CommandPermission("schoolwars.debug.revive")
    @Description("Revives a player")
    public static void onReviveCommand(CommandSender sender, @Name("player") @Optional String playerName){
        if (playerName == null || playerName.isEmpty()) {
            Player target = (Player) sender;
            RevivePlayer.revivePlayer((Player) sender, target);
        }else if (playerName != null && !playerName.isEmpty()) {
            Player target = Bukkit.getPlayer(playerName);
            RevivePlayer.revivePlayer((Player) sender, target);
        }else {
            sender.sendMessage(debugPrefix + "Please use /debug revive [<player>]");
        }
    }

    /**
     * Gets and displays the location of the block the player is looking at.
     * Provides a clickable message to copy coordinates to clipboard.
     *
     * @param sender the command sender (must be a player)
     */
    @Subcommand("getloc")
    @CommandCompletion("getloc")
    @Description("Sends the location of the block you are looking at to chat")
    public static void onGetLocCommand(CommandSender sender){
        Player p = (Player) sender;

        TextComponent location = new TextComponent(debugPrefix + "Click to copy block location! " + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ());
        location.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "" + p.getTargetBlockExact(4).getLocation().getX() + ", " + p.getTargetBlockExact(4).getLocation().getY() + ", " + p.getTargetBlockExact(4).getLocation().getZ()));
        p.spigot().sendMessage(location);
    }

    /**
     * Gets a player's reputation with a specific teacher.
     *
     * @param sender the command sender
     * @param lehrerName the name of the teacher to check
     * @param playerName optional target player; if null, checks sender's reputation
     */
    @Subcommand("rep")
    @CommandPermission("schoolwars.debug.rep")
    @Description("Gets the reputation of a player for a specific lehrer")
    public static void onRepCommand(CommandSender sender, @Name("lehrer") String lehrerName, @Name("player") @Optional String playerName) {

        Player p = (Player) sender;

        if (playerName == null || playerName.isEmpty()) {
            try {
                p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(p.getName()).getReputation(Lehrer.getLehrerByName(lehrerName)));
            } catch (Exception faultyInput) {
                p.sendMessage(debugPrefix + "Lehrer not found!");
            }
        } else if (playerName != null && !playerName.isEmpty()) {
            try {
                Player target = Bukkit.getPlayer(playerName);
                p.sendMessage(debugPrefix + "Reputation: " + ChatColor.RED + playerMirror.get(target.getName()).getReputation(Lehrer.getLehrerByName(lehrerName)));
            } catch (Exception faultyInput) {
                p.sendMessage(debugPrefix + "Lehrer not found!");
            }
        }
    }

    /**
     * Subcommand group for teacher-related debug operations.
     * Provides spawn, kill, and position update commands for teachers.
     */
    @Subcommand("lehrer")
    @CommandPermission("schoolwars.debug.lehrer")
    @Description("Debug commands for lehrer")
    public class onLehrerCommand extends BaseCommand {

        /**
         * Spawns a teacher at the player's location.
         *
         * @param sender the command sender
         * @param lehrerName the name of the teacher to spawn
         */
        @Subcommand("spawn")
        @Description("Spawns a lehrer at your location")
        public static void onLehrerSpawnCommand(CommandSender sender, @Name("lehrer") String lehrerName) {
            Player p = (Player) sender;
            for(Lehrer lehrer : Lehrer.values()) {
                Villager testLehrer = Lehrer.summonLehrer(p.getLocation(), lehrer);
                if(testLehrer.getCustomName().contains(lehrerName)) {
                    Bukkit.broadcastMessage("Found lehrer");
                    p.sendMessage(debugPrefix + "Spawned " + lehrer.name() + "!");
                }else{
                    testLehrer.remove();
                }
            }
        }

        /**
         * Removes a teacher or all teachers from the world.
         *
         * @param sender the command sender
         * @param lehrerName the name of the teacher to remove, or "all" to remove all
         */
        @Subcommand("kill")
        @Description("Kills a lehrer by name or all lehrers")
        public static void onLehrerKillCommand(CommandSender sender, @Name("lehrer") String lehrerName) {
            Player p = (Player) sender;
            for (Villager lehrer : lehrerEntityList) {
                try {
                    if (lehrer.getCustomName().contains(lehrerName)) {
                        lehrer.remove();
                    } else if (lehrerName.equals("all")) {
                        lehrer.remove();
                        lehrerEntityList.clear();
                        lehrerList.clear();
                    }
                } catch (Exception ignored) {
                }
            }
        }

        /**
         * Forces an immediate update of all teacher positions.
         */
        @Subcommand("position")
        @Description("Updates the position of all lehrers")
        public static void onLehrerPositionCommand() {
            Lehrer.updateLehrerPosition(true);
        }

        @Subcommand("get-quests")
        @Description("Returns lehrer quest list")
        public static void onLehrerGetQuestsCommand(CommandSender sender) {
            Player p = (Player) sender;
            for (Villager lehrer : questLehrerList) {
                p.sendMessage(debugPrefix + lehrer.getName() + " is in the list!");
            }
        }
    }

    /**
     * Subcommand group for toggling debug features.
     */
    @Subcommand("toggle")
    @CommandPermission("schoolwars.debug.toggle")
    @Description("Toggles debug features")
    public class onToggleCommand extends BaseCommand {

        /**
         * Toggles whether the player receives join/leave debug messages.
         *
         * @param sender the command sender
         */
        @Subcommand("join")
        @Description("Toggles join messages for the player")
        public static void onToggleJoinCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (joinMsg.contains(p)) {
                p.sendMessage(debugPrefix + "You will no longer recieve join messages!");
                joinMsg.remove(p);
            } else {
                p.sendMessage(debugPrefix + "You will now recieve join messages!");
                joinMsg.add(p);
            }
        }

        /**
         * Toggles whether the /start command is functional.
         *
         * @param sender the command sender
         */
        @Subcommand("start")
        @Description("Toggles /start command functionality")
        public static void onToggleStartCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (startWorks) {
                p.sendMessage(debugPrefix + "/start will no longer work!");
                startWorks = false;
            } else {
                p.sendMessage(debugPrefix + "/start will now work again!");
                startWorks = true;
            }
        }
    }

    /**
     * Stuns a player, preventing movement for a duration.
     *
     * @param sender the command sender
     * @param playerName the name of the player to stun
     * @param duration the stun duration in seconds
     * @param inGround whether to sink the player into the ground
     */
    @Subcommand("stun")
    @CommandPermission("schoolwars.debug.stun")
    @Description("Stuns a player for a certain duration")
    public static void onStunCommand(CommandSender sender, @Name("player") String playerName, @Name("duration") String duration, @Name("in-ground") String inGround) {
        Player p = (Player) sender;
        List<String> validInGround = List.of("true", "1", "yes", "y", "false", "0", "no", "n");
        List<String> trueValues = List.of("true", "1", "yes", "y");
        boolean inGroundBool;
        if (!validInGround.contains(inGround.toLowerCase())) {
            p.sendMessage(debugPrefix + "Please use a valid value for in-ground: true/false, 1/0, yes/no, y/n");
            return;
        }
        inGroundBool = trueValues.contains(inGround.toLowerCase());
        PlayerStun.stunPlayer(Bukkit.getPlayer(playerName), Integer.parseInt(duration), inGroundBool);
        p.sendMessage(debugPrefix + "You stunned " + playerName + " for " + duration + " seconds.");
        p.sendMessage(debugPrefix + "inGround: " + inGround);
    }

    /**
     * Subcommand group for managing player disciplinary warnings (Verweise).
     */
    @Subcommand("verweise")
    @CommandPermission("schoolwars.debug.verweise")
    @Description("Debug commands for Verweise")
    public class  onVerweiseCommand extends BaseCommand {

        /**
         * Adds warnings to a player.
         *
         * @param sender the command sender
         * @param amount the number of warnings to add (can be negative)
         * @param playerName optional target player; if null, applies to sender
         */
        @Subcommand("add")
        @Description("Adds a certain amount of Verweise to a player. Use negative numbers to remove Verweise")
        public static void onVerweiseAddCommand(CommandSender sender, @Name("amount") String amount, @Name("player") @Optional String playerName) {
            Player p = (Player) sender;
            if (playerName == null || playerName.isEmpty()) {
                playerMirror.get(p.getName()).addVerweis(Integer.parseInt(amount));
                p.sendMessage(debugPrefix + "Added Verweis: " + amount);
            } else if (playerName != null && !playerName.isEmpty()) {
                playerMirror.get(playerName).addVerweis(Integer.parseInt(amount));
                p.sendMessage(debugPrefix + "Added Verweis: " + amount + " to " + playerName);
            }else {
                p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
                p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <amount> <player>!");
                p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
            }
        }

        /**
         * Gets the current warning count for a player.
         *
         * @param sender the command sender
         * @param playerName optional target player; if null, shows sender's count
         */
        @Subcommand("get")
        @Description("Gets the current amount of Verweise of a player")
        public static void onVerweiseGetCommand(CommandSender sender, @Name("player") @Optional String playerName) {
            Player p = (Player) sender;
            if (playerName == null || playerName.isEmpty()) {
                p.sendMessage(debugPrefix + "Your current Verweise: " + playerMirror.get(p.getName()).getVerweise());
            } else if (playerName != null && !playerName.isEmpty()) {
                p.sendMessage(debugPrefix + playerName + "'s current Verweise: " + playerMirror.get(playerName).getVerweise());
            }else {
                p.sendMessage(debugPrefix + "Please use /debug verweise <get>");
                p.sendMessage(debugPrefix + "or /debug verweise <get> <player>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <amount>");
                p.sendMessage(debugPrefix + "or /debug verweise <add> <player> <amount>!");
                p.sendMessage(debugPrefix + "You can use negative numbers to remove Verweise");
            }
        }
    }

    /**
     * Subcommand group for class schedule (Stundenplan) management.
     */
    @Subcommand("stundenplan")
    @CommandPermission("schoolwars.debug.stundenplan")
    @Description("Debug commands for Stundenplan")
    public class onStundenplanCommand extends BaseCommand {

        /**
         * Displays the current class schedule.
         *
         * @param sender the command sender
         */
        @Subcommand("get")
        @Description("Gets the current Stundenplan")
        public static void onStundenplanGetCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                p.sendMessage(debugPrefix + "Current Stundenplan: ");
                for (Lehrer lehrer : Stundenplan.studenplan.keySet()) {
                    p.sendMessage(debugPrefix + lehrer.name() + " -> " + Stundenplan.studenplan.get(lehrer).name());
                }
            } else {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }

        /**
         * Forces an update of the class schedule.
         *
         * @param sender the command sender
         */
        @Subcommand("update")
        @Description("Updates the Stundenplan")
        public static void onStundenplanUpdateCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                Stundenplan.updateStundenplan(true);
                p.sendMessage(debugPrefix + "Sucessfully updated Stundenplan!");
            } else {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }

        /**
         * Opens the schedule viewer GUI.
         *
         * @param sender the command sender
         */
        @Subcommand("view")
        @Description("Views the Stundenplan GUI")
        public static void onStundenplanViewCommand(CommandSender sender) {
            Player p = (Player) sender;
            if (SchoolWars.gameStarted) {
                p.openInventory(SekretariatStundenplan.createStundenplan(0));
                p.sendMessage(debugPrefix + "Opened Stundenplan!");
            } else {
                p.sendMessage(debugPrefix + "Game has not started yet!");
            }
        }
    }

    /**
     * Subcommand group for boss fight control.
     */
    @Subcommand("bossfight")
    @CommandPermission("schoolwars.debug.bossfight")
    @Description("Debug commands for Bossfight")
    public class onBossfightCommand extends BaseCommand {

        /**
         * Starts the atomic bomb boss fight.
         *
         * @param sender the command sender
         */
        @Subcommand("start")
        @Description("Starts the Atombombe Bossfight")
        public static void onBossfightStartCommand(CommandSender sender) {
            AtombombeBossfight.startBossfight();
            sender.sendMessage(debugPrefix + "Started bossfight!");
        }
    }

    /**
     * Starts the intro cutscene for the sender.
     *
     * @param sender the command sender
     */
    @Subcommand("intro")
    @CommandPermission("schoolwars.debug.intro")
    @Description("Starts the intro sequence for a player")
    public static void onIntroCommand(CommandSender sender, @Optional String target) {
        if (target != null && !target.isEmpty()) {
            Player targetPlayer = Bukkit.getPlayer(target);
            Intro.introScene(targetPlayer);
            sender.sendMessage(Debug.debugPrefix + "Started intro sequence for " + target + "!");
            return;
        }
        Intro.introScene((Player) sender);
        sender.sendMessage(Debug.debugPrefix + "Started intro sequence!");
    }

    /**
     * Toggles flight mode for a player.
     *
     * @param sender the command sender
     * @param player optional target player; if null, toggles for sender
     */
    @Subcommand("fly")
    @CommandPermission("schoolwars.debug.fly")
    @Description("Toggles flight for a player")
    public static void onFlyCommand(CommandSender sender, @Name("player") @Optional String player) {

        if (player == null || player.isEmpty()) {
            Player target = (Player) sender;
            target.setAllowFlight(!target.getAllowFlight());
        }else {
            Player target = Bukkit.getPlayer(player);
            target.setAllowFlight(!target.getAllowFlight());
        }
    }

    /**
     * Shows the location of the next schoolbook spawn.
     *
     * @param sender the command sender
     */
    @Subcommand("schulbuch")
    @CommandPermission("schoolwars.debug.schulbuch")
    @Description("Gets the current schulbuch leveling location")
    public static void onSchulbuchCommand(CommandSender sender) {
            sender.sendMessage(debugPrefix + "Next book location: " + SchulbuchLevels.currentBookshelfLoc.getX() + ", " + SchulbuchLevels.currentBookshelfLoc.getY() + ", " + SchulbuchLevels.currentBookshelfLoc.getZ());
    }

    /**
     * Reloads the probability configuration from disk.
     *
     * @param sender the command sender
     */
    @Subcommand("rl-probs")
    @CommandPermission("schoolwars.debug.reload-probs")
    @Description("Reloads the probability configuration")
    public static void onReloadProbabilitiesCommand(CommandSender sender) {
        ProbabilityConfig.reload();
        sender.sendMessage(debugPrefix + "Reloaded probabilities!");
    }

}