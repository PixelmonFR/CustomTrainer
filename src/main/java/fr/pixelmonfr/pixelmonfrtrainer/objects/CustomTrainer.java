package fr.pixelmonfr.pixelmonfrtrainer.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomTrainer {
    public String name, customTexture;
    public int level;
    public CustomTrainerParty pokemons;
    public String[] biomes;
    public int[] biomesTrainerRarity;
    public String[] activeWorld;
    public String winMessage;
    public String lossMessage;
    public int moneyReward;


    @JsonCreator
    public CustomTrainer(@JsonProperty("name") String name,@JsonProperty("level") int level,@JsonProperty("customTexture") String customTexture, @JsonProperty("biomes") String[] biomes,@JsonProperty("biomesTrainerRarity") int[] biomesTrainerRarity,@JsonProperty("pokemons") CustomTrainerParty pokemons, @JsonProperty("activeWorld") String[] activeWorld,@JsonProperty("winMessage") String winMessage,@JsonProperty("loseMessage") String lossMessage,@JsonProperty("moneyReward") int moneyReward) {
        this.name = name;
        this.level = level;
        this.customTexture = customTexture;
        this.pokemons = pokemons;
        this.biomes = biomes;
        this.biomesTrainerRarity = biomesTrainerRarity;
        this.activeWorld = activeWorld;
        this.winMessage = winMessage;
        this.lossMessage = lossMessage;
        this.moneyReward = moneyReward;
    }
}
