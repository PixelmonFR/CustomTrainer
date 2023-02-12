package fr.pixelmonfr.pixelmonfrtrainer.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomPokemonTrainer {
    public String species, name,nature, growth, item;
    public CustomPokemonStat ivs, evs;
    public String ability;
    public CustomAttacksPokemon attacks;
    public int level;
    public boolean shiny;
    public int form;
    public String customTexture;

    @JsonCreator
    public CustomPokemonTrainer(@JsonProperty("species") String species,@JsonProperty("name") String name,@JsonProperty("level") int level,@JsonProperty("ivs") CustomPokemonStat ivs, @JsonProperty("evs") CustomPokemonStat evs, @JsonProperty("attacks") CustomAttacksPokemon attacks,@JsonProperty("nature") String nature, @JsonProperty("ability") String ability, @JsonProperty("growth") String growth,@JsonProperty("item") String item,@JsonProperty("shiny") boolean shiny, @JsonProperty("form") int form, @JsonProperty("customTexture") String customTexture){
        this.species = species;
        this.name = name;
        this.level = level;
        this.ivs = ivs;
        this.evs = evs;
        this.attacks = attacks;
        this.nature = nature;
        this.growth = growth;
        this.item = item;
        this.shiny = shiny;
        this.form = form;
        this.ability = ability;
        this.customTexture = customTexture;
    }
}
