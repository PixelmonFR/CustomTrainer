package fr.pixelmonfr.pixelmonfrtrainer.commands;

import fr.pixelmonfr.pixelmonfrtrainer.managers.TrainersManager;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PixelmonFRTrainerCommand implements CommandExecutor {
    public static String commandName = "customtrainer";

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!args.<String>getOne("args").isPresent()) {
            sendArgumentList(src,1);
            return CommandResult.success();
        };
        List<String> argsList = Arrays.stream(args.<String>getOne("args").get().split(" ")).collect(Collectors.toList());

        if(Objects.equals(argsList.get(0), "reload")){
            TrainersManager.INSTANCE.reload();
            src.sendMessage(Text.builder("Vous avez reload la config custom trainer !").color(TextColors.GREEN).build());
        }
        else if(Objects.equals(argsList.get(0), "spawn")){
            if(argsList.size() == 2) {
                if(TrainersManager.INSTANCE.customTrainers.containsKey(argsList.get(1)+".json")){
                    TrainersManager.INSTANCE.createTrainer(TrainersManager.INSTANCE.customTrainers.get(argsList.get(1)+".json"), ((EntityPlayerMP)src).world, ((EntityPlayerMP)src).posX,((EntityPlayerMP)src).posY,((EntityPlayerMP)src).posZ);
                    src.sendMessage(Text.builder("Vous avez fais spawn un trainer custom: "+ argsList.get(1)).color(TextColors.GREEN).build());
                }
                else sendErrorTrainerIsNull(src);
            }
            else sendErrorSpawn(src);
        }
        else if(Objects.equals(argsList.get(0), "list")){
            if(TrainersManager.INSTANCE.customTrainers.keySet().size() == 0 ) src.sendMessage(Text.builder("Aucun trainer trouvé :").color(TextColors.GOLD).style(TextStyles.BOLD).style(TextStyles.UNDERLINE).build());
            else{
                src.sendMessage(Text.builder("Liste des trainers trouvés :").color(TextColors.GOLD).style(TextStyles.BOLD).style(TextStyles.UNDERLINE).build());
                src.sendMessage(Text.builder("").build());
                for(String trainerName: TrainersManager.INSTANCE.customTrainers.keySet())
                    src.sendMessage(Text.builder(" - ").color(TextColors.WHITE).append(Text.builder(trainerName.replace(".json","")).color(TextColors.GREEN).build()).build());
            }
        }
        else sendArgumentList(src,0);

        return CommandResult.success();
    }

    private void sendArgumentList(CommandSource src,int errorID){
        if(errorID == 0)src.sendMessage(Text.builder("Vous devez entrer un argument !").color(TextColors.RED).build());
        src.sendMessage(Text.builder("/" + commandName+" spawn <trainerName>").color(TextColors.RED).build());
        src.sendMessage(Text.builder("/" + commandName+" list").color(TextColors.RED).build());
        src.sendMessage(Text.builder("/" + commandName+" reload").color(TextColors.RED).build());
    }

    private void sendErrorSpawn(CommandSource src){
        src.sendMessage(Text.builder("Vous devez ecrire votre commande comme ceci !").color(TextColors.RED).build());
        src.sendMessage(Text.builder("/" + commandName+" spawn <trainerName>").color(TextColors.RED).build());
    }
    private void sendErrorTrainerIsNull(CommandSource src){
        src.sendMessage(Text.builder("Le trainer n'existe pas !").color(TextColors.RED).build());
    }
}
