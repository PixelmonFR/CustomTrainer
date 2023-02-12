package fr.pixelmonfr.pixelmonfrtrainer.registers;

import fr.pixelmonfr.pixelmonfrtrainer.PixelmonFRTrainer;
import fr.pixelmonfr.pixelmonfrtrainer.commands.PixelmonFRTrainerCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commands {
    public Commands(){
        createCommand(
                PixelmonFRTrainerCommand.commandName,
                new PixelmonFRTrainerCommand(),
                new String[]{PixelmonFRTrainerCommand.commandName},
                new CommandElement[]{GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("args")))}
        );
    }

    private void createCommand(String commandName, CommandExecutor commandExecutor, String[] alias, CommandElement[] args){
        registerCommand(commandExecutor, commandName,alias,args);
    }

    private void registerCommand(CommandExecutor commandExecutor, String commandName, String[] alias, CommandElement[] args){
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("permet d'utiliser "+ commandName))
                .permission(commandName+".use")
                .executor(commandExecutor)
                .arguments(args)
                .build();

        Sponge.getCommandManager().register(PixelmonFRTrainer.INSTANCE, commandSpec,alias);
    }

}
