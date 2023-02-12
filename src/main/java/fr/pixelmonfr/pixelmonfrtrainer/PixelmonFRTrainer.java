package fr.pixelmonfr.pixelmonfrtrainer;

import fr.pixelmonfr.pixelmonfrtrainer.registers.Commands;
import fr.pixelmonfr.pixelmonfrtrainer.registers.Listeners;
import fr.pixelmonfr.pixelmonfrtrainer.registers.Managers;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "pixelmonfrtrainer",
        name = "PixelmonFR trainer",
        description = "pixelmonfr trainer for PixelmonFR",
        authors = {"zendraft"}
)
public class PixelmonFRTrainer {
    public static PixelmonFRTrainer INSTANCE;

    private static Managers managers;
    private static Commands commands;
    private static Listeners listeners;

    @Listener
    public void onServerInit(GameInitializationEvent event) {
        INSTANCE = this;
        managers = new Managers();
       //managers.getConfigManager().setupConfig(configFile, configLoader);


    }
    @Listener
    public void onServerStart(org.spongepowered.api.event.game.state.GameStartedServerEvent event){
        listeners = new Listeners();
        commands = new Commands();
    }

   // public CommentedConfigurationNode getConfig() {return managers.getConfigManager().getConfig();}
    public Managers getManagers(){return managers;}


}
