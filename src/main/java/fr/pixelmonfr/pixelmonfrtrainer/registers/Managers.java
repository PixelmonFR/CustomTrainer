package fr.pixelmonfr.pixelmonfrtrainer.registers;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.OpenScreen;
import com.pixelmonmod.pixelmon.enums.EnumGuiScreen;
import fr.pixelmonfr.pixelmonfrtrainer.managers.TrainersManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.io.IOException;
import java.util.Random;

public class Managers {
    public static TrainersManager trainersManager;

    public Managers()  {
        try {trainersManager = new TrainersManager();}
        catch (IOException e) {throw new RuntimeException(e);}
        OpenScreen.open(player, EnumGuiScreen.PickPokemon,0);
        Pixelmon.network.sendTo();
    }


}
