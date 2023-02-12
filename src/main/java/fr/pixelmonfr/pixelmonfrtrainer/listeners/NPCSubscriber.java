package fr.pixelmonfr.pixelmonfrtrainer.listeners;

import com.pixelmonmod.pixelmon.api.events.npc.NPCEvent;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import fr.pixelmonfr.pixelmonfrtrainer.managers.TrainersManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;


public class NPCSubscriber {
    @SubscribeEvent
    public void onBattleEnd(NPCEvent.EndBattle event){
        if (!(event.npc instanceof NPCTrainer)) return;
        if(!TrainersManager.INSTANCE.npcPlayerSpawned.containsKey(event.npc.getUniqueID()))return;
        TrainersManager.INSTANCE.removeNPCPlayer(event.npc.getUniqueID(), Sponge.getServer().getPlayer(TrainersManager.INSTANCE.npcPlayerSpawned.get(event.npc.getUniqueID())).get());
    }
}
