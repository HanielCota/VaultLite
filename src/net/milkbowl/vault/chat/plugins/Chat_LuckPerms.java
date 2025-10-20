package net.milkbowl.vault.chat.plugins;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * LuckPerms Chat Provider implementation.
 * 
 * <p>This class provides complete integration with the LuckPerms chat system
 * through the Vault API, allowing existing plugins to access prefixes and suffixes
 * without modifications.</p>
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Support for contextual prefixes and suffixes</li>
 *   <li>Integration with LuckPerms metadata</li>
 *   <li>World support</li>
 *   <li>Read-only implementation for mutations</li>
 *   <li>Full compatibility with Vault Chat API</li>
 * </ul>
 * 
 * @author VaultLite Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public final class Chat_LuckPerms extends Chat {

	/**
	 * LuckPerms API instance for chat operations.
	 */
    private final LuckPerms luckPerms;

    /**
     * Constructs a new instance of the Chat Provider for LuckPerms.
     * 
     * @param plugin the plugin instance
     * @param perms the associated permission provider
     * @param luckPerms the LuckPerms API instance
     */
    public Chat_LuckPerms(Plugin plugin, Permission perms, LuckPerms luckPerms) {
        super(perms);
        this.luckPerms = luckPerms;
    }

    /**
     * Returns the chat provider name.
     * 
     * @return the provider name
     */
    @Override
    public String getName() {
        return "LuckPerms-Chat";
    }

    /**
     * Checks if the provider is enabled and working.
     * 
     * @return {@code true} if LuckPerms is available, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return this.luckPerms != null && Bukkit.getServer() != null;
    }

    /**
     * Gets the LuckPerms user for an offline player.
     * 
     * @param player the offline player
     * @return the LuckPerms user or null if not found
     */
    private User userOf(OfflinePlayer player) {
        if (player == null || luckPerms == null) {
            return null;
        }
        
        Player online = player.getPlayer();
        if (online == null) {
            return null;
        }
        
        return luckPerms.getPlayerAdapter(Player.class)
                .getUser(online);
    }

    /**
     * Returns a player's prefix.
     * 
     * @param world the world (not used in LuckPerms)
     * @param playerName the player name
     * @return the player's prefix or empty string if not found
     */
    @Override
    public String getPlayerPrefix(String world, String playerName) {
        if (playerName == null) {
            return "";
        }
        
        Player online = Bukkit.getPlayerExact(playerName);
        if (online == null) {
            return "";
        }
        
        User luckPermsUser = luckPerms.getPlayerAdapter(Player.class)
                .getUser(online);
        String playerPrefix = luckPermsUser.getCachedData()
                .getMetaData(QueryOptions.defaultContextualOptions())
                .getPrefix();
        return playerPrefix == null ? "" : playerPrefix;
    }

    /**
     * Returns a player's suffix.
     * 
     * @param world the world (not used in LuckPerms)
     * @param playerName the player name
     * @return the player's suffix or empty string if not found
     */
    @Override
    public String getPlayerSuffix(String world, String playerName) {
        if (playerName == null) {
            return "";
        }
        
        Player online = Bukkit.getPlayerExact(playerName);
        if (online == null) {
            return "";
        }
        
        User luckPermsUser = luckPerms.getPlayerAdapter(Player.class)
                .getUser(online);
        String playerSuffix = luckPermsUser.getCachedData()
                .getMetaData(QueryOptions.defaultContextualOptions())
                .getSuffix();
        return playerSuffix == null ? "" : playerSuffix;
    }

    /**
     * Returns an offline player's prefix.
     * 
     * @param player the offline player
     * @return the player's prefix or empty string if not found
     */
    public String getPlayerPrefix(OfflinePlayer player) {
        User user = userOf(player);
        if (user == null) {
            return "";
        }
        
        String playerPrefix = user.getCachedData()
                .getMetaData(QueryOptions.defaultContextualOptions())
                .getPrefix();
        return playerPrefix == null ? "" : playerPrefix;
    }

    /**
     * Returns an offline player's suffix.
     * 
     * @param player the offline player
     * @return the player's suffix or empty string if not found
     */
    public String getPlayerSuffix(OfflinePlayer player) {
        User user = userOf(player);
        if (user == null) {
            return "";
        }
        
        String playerSuffix = user.getCachedData()
                .getMetaData(QueryOptions.defaultContextualOptions())
                .getSuffix();
        return playerSuffix == null ? "" : playerSuffix;
    }

    /**
     * Sets a player's prefix (not implemented - read-only).
     * 
     * @param world the world
     * @param player the player name
     * @param prefix the prefix
     */
    @Override
    public void setPlayerPrefix(String world, String player, String prefix) {
        // No-op: Read-only implementation
    }

    /**
     * Sets a player's suffix (not implemented - read-only).
     * 
     * @param world the world
     * @param player the player name
     * @param suffix the suffix
     */
    @Override
    public void setPlayerSuffix(String world, String player, String suffix) {
        // No-op: Read-only implementation
    }

    /**
     * Returns a group's prefix (not implemented).
     * 
     * @param world the world
     * @param group the group name
     * @return always empty string since not implemented
     */
    @Override
    public String getGroupPrefix(String world, String group) {
        return "";
    }

    /**
     * Returns a group's suffix (not implemented).
     * 
     * @param world the world
     * @param group the group name
     * @return always empty string since not implemented
     */
    @Override
    public String getGroupSuffix(String world, String group) {
        return "";
    }

    /**
     * Sets a group's prefix (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param prefix the prefix
     */
    @Override
    public void setGroupPrefix(String world, String group, String prefix) {
        // No-op: Read-only implementation
    }

    /**
     * Sets a group's suffix (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param suffix the suffix
     */
    @Override
    public void setGroupSuffix(String world, String group, String suffix) {
        // No-op: Read-only implementation
    }

    /**
     * Returns string information of a group (not implemented).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public String getGroupInfoString(String world, String group, String node, String defaultValue) {
        return defaultValue;
    }

    /**
     * Sets string information of a group (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setGroupInfoString(String world, String group, String node, String value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns integer information of a group (not implemented).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public int getGroupInfoInteger(String world, String group, String node, int defaultValue) {
        return defaultValue;
    }

    /**
     * Sets integer information of a group (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setGroupInfoInteger(String world, String group, String node, int value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns double information of a group (not implemented).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public double getGroupInfoDouble(String world, String group, String node, double defaultValue) {
        return defaultValue;
    }

    /**
     * Sets double information of a group (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setGroupInfoDouble(String world, String group, String node, double value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns boolean information of a group (not implemented).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public boolean getGroupInfoBoolean(String world, String group, String node, boolean defaultValue) {
        return defaultValue;
    }

    /**
     * Sets boolean information of a group (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setGroupInfoBoolean(String world, String group, String node, boolean value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns string information of a player (not implemented).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public String getPlayerInfoString(String world, String playerName, String node, String defaultValue) {
        return defaultValue;
    }

    /**
     * Sets string information of a player (not implemented - read-only).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setPlayerInfoString(String world, String playerName, String node, String value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns integer information of a player (not implemented).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public int getPlayerInfoInteger(String world, String playerName, String node, int defaultValue) {
        return defaultValue;
    }

    /**
     * Sets integer information of a player (not implemented - read-only).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setPlayerInfoInteger(String world, String playerName, String node, int value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns double information of a player (not implemented).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public double getPlayerInfoDouble(String world, String playerName, String node, double defaultValue) {
        return defaultValue;
    }

    /**
     * Sets double information of a player (not implemented - read-only).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setPlayerInfoDouble(String world, String playerName, String node, double value) {
        // No-op: Read-only implementation
    }

    /**
     * Returns boolean information of a player (not implemented).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param defaultValue the default value
     * @return always the default value since not implemented
     */
    @Override
    public boolean getPlayerInfoBoolean(String world, String playerName, String node, boolean defaultValue) {
        return defaultValue;
    }

    /**
     * Sets boolean information of a player (not implemented - read-only).
     * 
     * @param world the world
     * @param playerName the player name
     * @param node the information node
     * @param value the value
     */
    @Override
    public void setPlayerInfoBoolean(String world, String playerName, String node, boolean value) {
        // No-op: Read-only implementation
    }
}
