package fr.pixelmonfr.pixelmonfrtrainer.listeners;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.OpenScreen;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.entities.npcs.registry.NPCRegistryTrainers;
import com.pixelmonmod.pixelmon.enums.EnumGuiScreen;
import com.pixelmonmod.pixelmon.storage.ReforgedStorageManager;
import com.pixelmonmod.pixelmon.storage.adapters.ReforgedFileAdapter;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;

import org.spongepowered.api.event.entity.SpawnEntityEvent;


public class NPCListeners {

    @Listener
    public void replaceNPCbyNewCustomNPC(SpawnEntityEvent e){
        for(Entity entity : e.getEntities()){
            if(!(entity instanceof NPCTrainer)) continue;
            if(((NPCTrainer)entity).getBaseTrainer().id== NPCRegistryTrainers.getByName("steve").id) continue;
            entity.remove();
            //System.out.println("npc remove");
            //TrainersManager.INSTANCE.createCustomNPC(((NPCTrainer)entity).world, ((NPCTrainer)entity).posX,((NPCTrainer)entity).posY,((NPCTrainer)entity).posZ,  entity.getLocation().getBiome().getName().toLowerCase());



        }
    }

}
