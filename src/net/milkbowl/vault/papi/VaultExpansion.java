package net.milkbowl.vault.papi;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * PlaceholderAPI expansion for VaultLite.
 * 
 * <p>This class provides economy placeholders through PlaceholderAPI,
 * allowing other plugins to access economy information in a standardized way.</p>
 * 
 * <p>Available placeholders:</p>
 * <ul>
 *   <li>{@code %vault_balance%} - Player balance</li>
 *   <li>{@code %vault_balance_formatted%} - Formatted player balance</li>
 * </ul>
 * 
 * @author VaultLite Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class VaultExpansion extends PlaceholderExpansion {

	/**
	 * Returns the expansion identifier.
	 * 
	 * @return the identifier "vault"
	 */
	@Override
	public String getIdentifier() {
		return "vault";
	}

	/**
	 * Returns the expansion author.
	 * 
	 * @return the author "Vault"
	 */
	@Override
	public String getAuthor() {
		return "Vault";
	}

	/**
	 * Returns the expansion version.
	 * 
	 * @return the version "1.0.0"
	 */
	@Override
	public String getVersion() {
		return "1.0.0";
	}

	/**
	 * Indicates if the expansion should persist after reloads.
	 * 
	 * @return always {@code true} to persist
	 */
	@Override
	public boolean persist() {
		return true;
	}

	/**
	 * Gets the economy provider registered on the server.
	 * 
	 * @return the economy provider or null if not found
	 */
	private Economy economy() {
		if (Bukkit.getServicesManager() == null) {
			return null;
		}
		
		RegisteredServiceProvider<Economy> economyServiceProvider = Bukkit.getServicesManager()
				.getRegistration(Economy.class);

		if (economyServiceProvider == null) {
			return null;
		}
		
		return economyServiceProvider.getProvider();
	}

	/**
	 * Processes a placeholder request.
	 * 
	 * <p>Supported placeholders:</p>
	 * <ul>
	 *   <li>{@code balance} - Returns the player's balance</li>
	 *   <li>{@code balance_formatted} - Returns the player's formatted balance</li>
	 * </ul>
	 * 
	 * @param player the player (can be null)
	 * @param params the placeholder parameters
	 * @return the placeholder value or empty string if not supported
	 */
	@Override
	public String onRequest(OfflinePlayer player, String params) {
		Economy economyProvider = economy();
		if (economyProvider == null) {
			return "";
		}
		
		return switch (params.toLowerCase()) {
			case "balance" -> player == null ? "0" : Double.toString(economyProvider.getBalance(player));
			
			case "balance_formatted" -> player == null ? 
				economyProvider.format(0) : 
				economyProvider.format(economyProvider.getBalance(player));
			default -> "";
		};
	}
}
