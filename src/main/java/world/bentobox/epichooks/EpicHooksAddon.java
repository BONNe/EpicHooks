package world.bentobox.epichooks;

import org.bukkit.Material;

import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.flags.Flag;
import world.bentobox.bentobox.managers.RanksManager;
import world.bentobox.epichooks.events.HopperAccessListener;


/**
 * This class inits EpicHooks addon.
 */
public final class EpicHooksAddon extends Addon
{
    /**
     * Executes code when loading the addon. This is called before {@link #onEnable()}.
     * This <b>must</b> be used to setup configuration, worlds and commands.
     */
    public void onLoad()
    {
    }


    /**
     * Executes code when enabling the addon. This is called after {@link #onLoad()}.
     * <br/> Note that commands and worlds registration <b>must</b> be done in {@link
     * #onLoad()}, if need be. Failure to do so <b>will</b> result in issues such as
     * tab-completion not working for commands.
     */
    public void onEnable()
    {
        // Check if it is enabled - it might be loaded, but not enabled.
        if (this.getPlugin() == null || !this.getPlugin().isEnabled())
        {
            this.logError("BentoBox is not available or disabled!");
            this.setState(State.DISABLED);
            return;
        }

        this.getPlugin().getAddonsManager().getGameModeAddons().forEach(
            EpicHooksAddon.EPIC_HOPPER_ISLAND_PROTECTION::addGameModeAddon);

        // This would be a correct way how to do access check.
        //this.registerListener(new HopperAccessListener(this));
        // But because no public maven repo for EpicHoppers with version 4.3.7 and newer exists,
        // then I am forced to hack into all handlers.
        new HopperAccessListener(this);

        // Register Flags
        this.registerFlag(EpicHooksAddon.EPIC_HOPPER_ISLAND_PROTECTION);
    }


    /**
     * Executes code when disabling the addon.
     */
    public void onDisable()
    {
    }


    // ---------------------------------------------------------------------
    // Section: Constants
    // ---------------------------------------------------------------------


    /**
     * This flag allows to define which users can access to epic hoppers. F.e. it can be
     * set that only Island owner can access to them. By default it is set to Member
     * rank.
     */
    public static final Flag EPIC_HOPPER_ISLAND_PROTECTION =
        new Flag.Builder("EPIC_HOPPER_ISLAND_PROTECTION", Material.HOPPER).defaultRank(
            RanksManager.MEMBER_RANK).build();
}
