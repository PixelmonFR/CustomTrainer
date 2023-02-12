package fr.pixelmonfr.pixelmonfrtrainer.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomPokemonStat {
    public int hp, atk, def, spAtk, spDef, speed;
    @JsonCreator
    public CustomPokemonStat(@JsonProperty("hp") int hp,@JsonProperty("atk") int atk,@JsonProperty("def") int def,@JsonProperty("spAtk") int spAtk,@JsonProperty("spDef") int spDef,@JsonProperty("speed") int speed) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }
}
