package fr.pixelmonfr.pixelmonfrtrainer.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomTrainerParty {
    public CustomPokemonTrainer pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6;

    @JsonCreator
    public CustomTrainerParty(@JsonProperty("pokemon1") CustomPokemonTrainer pokemon1,@JsonProperty("pokemon2") CustomPokemonTrainer pokemon2,@JsonProperty("pokemon3") CustomPokemonTrainer pokemon3,@JsonProperty("pokemon4") CustomPokemonTrainer pokemon4,@JsonProperty("pokemon5") CustomPokemonTrainer pokemon5,@JsonProperty("pokemon6") CustomPokemonTrainer pokemon6){
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.pokemon3 = pokemon3;
        this.pokemon4 = pokemon4;
        this.pokemon5 = pokemon5;
        this.pokemon6 = pokemon6;
    }
}
