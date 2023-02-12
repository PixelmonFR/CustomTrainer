package fr.pixelmonfr.pixelmonfrtrainer.registers;

import com.pixelmonmod.pixelmon.Pixelmon;
import fr.pixelmonfr.pixelmonfrtrainer.PixelmonFRTrainer;
import fr.pixelmonfr.pixelmonfrtrainer.listeners.NPCListeners;
import fr.pixelmonfr.pixelmonfrtrainer.listeners.NPCSubscriber;
import org.spongepowered.api.Sponge;

public class Listeners {
    public Listeners(){
        //Sponge.getEventManager().registerListeners(PixelmonFRTrainer.INSTANCE, new NPCListeners());
        Pixelmon.EVENT_BUS.register(new NPCSubscriber());
    }
}
