package world.bentobox.epichooks;

import world.bentobox.bentobox.api.addons.Addon;
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

        // Register listener
        this.registerListener(new HopperAccessListener(this));
    }


    /**
     * Executes code when disabling the addon.
     */
    public void onDisable()
    {
    }
}
