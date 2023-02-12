package fr.pixelmonfr.pixelmonfrtrainer.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomAttacksPokemon {
    public String attack1, attack2, attack3, attack4;
    @JsonCreator
    public CustomAttacksPokemon(@JsonProperty("attacks1") String attack1,@JsonProperty("attack2") String attack2,@JsonProperty("attack3") String attack3, @JsonProperty("attack4") String attack4){
        this.attack1 = attack1;
        this.attack2 = attack2;
        this.attack3 = attack3;
        this.attack4 = attack4;
    }
}
