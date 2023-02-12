package fr.pixelmonfr.pixelmonfrtrainer.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    public int spawnRange;
    public int tentativePercentSpawn;
    public int spawnTentativeInterval;
    public int maxNpcsPerPlayer;
    public int npcDispawnTime;

    @JsonCreator
    public Config(@JsonProperty("spawnRange") int spawnRange ,@JsonProperty("tentativePercentSpawn") int tentativePercentSpawn, @JsonProperty("spawnTentativeInterval") int spawnTentativeInterval, @JsonProperty("maxNpcsPerPlayer") int maxNpcsPerPlayer, @JsonProperty("npcDispawnTime") int npcDispawnTime){
        this.spawnRange = spawnRange;
        this.tentativePercentSpawn = tentativePercentSpawn;
        this.spawnTentativeInterval = spawnTentativeInterval;
        this.maxNpcsPerPlayer = maxNpcsPerPlayer;
        this.npcDispawnTime = npcDispawnTime;
    }
}
