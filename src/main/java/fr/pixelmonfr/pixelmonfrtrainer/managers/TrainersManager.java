package fr.pixelmonfr.pixelmonfrtrainer.managers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.entities.npcs.registry.NPCRegistryTrainers;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleAIMode;
import fr.pixelmonfr.pixelmonfrtrainer.PixelmonFRTrainer;
import fr.pixelmonfr.pixelmonfrtrainer.objects.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.biome.BiomeTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TrainersManager {
    public static TrainersManager INSTANCE;
    public HashMap<String, CustomTrainer> customTrainers = new HashMap<>();
    public HashMap<String, List<String>> biomeTrainersSpawn = new HashMap<>();
    public HashMap<String, List<Integer>> biomeTrainerRarity = new HashMap<>();
    public HashMap<String, List<UUID>> playerNPCSpawned = new HashMap<>();
    public HashMap<UUID, String> npcPlayerSpawned = new HashMap<>();

    public Config config;

    Task npcSpawnService;

    public File rootDir = new File("config/PixelmonFRTrainers");
    public File trainersDir = new File("config/PixelmonFRTrainers/Trainers");
    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

    public TrainersManager() throws IOException {
        if(!trainersDir.isDirectory()) createDefault();
        loadNPCsConfig();
        loadTrainers();
        loadNPCSpawnService();
        INSTANCE=this;
    }

    private void loadTrainers(){
        try {
            customTrainers.clear();
            biomeTrainersSpawn.clear();
            biomeTrainerRarity.clear();
            for (String trainer :allTrainers()) {
                customTrainers.put(trainer, getTrainerConfig(trainer));
                for(String biome : customTrainers.get(trainer).biomes){
                    List<String> trainers = new ArrayList<>();
                    if(biomeTrainersSpawn.containsKey(biome.toLowerCase())) trainers = biomeTrainersSpawn.get(biome.toLowerCase());

                    trainers.add(trainer);
                    biomeTrainersSpawn.put(biome.toLowerCase(), trainers);
                }
            }
            calculateRarity();
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }
    private int getRarityList(String biomeId){
        if(biomeTrainerRarity.containsKey(biomeId)){
            System.out.println("return : "+(biomeTrainerRarity.size()-1) );
            return biomeTrainerRarity.get(biomeId).get(biomeTrainerRarity.get(biomeId).size()-1);
        }
        System.out.println("return 0");
        return 0;
    }


    private void calculateRarity(){
        for (String trainer : customTrainers.keySet()){
            int idBiomeTrainer = 0;
            for (int biome : customTrainers.get(trainer).biomesTrainerRarity){
                String biomeName = customTrainers.get(trainer).biomes[idBiomeTrainer].toLowerCase();

                List<Integer> rarityList = new ArrayList<>();

                if(biomeTrainerRarity.containsKey(biomeName)) rarityList = biomeTrainerRarity.get(biomeName);

                rarityList.add(getRarityList(biomeName) + biome);
                biomeTrainerRarity.put(biomeName, rarityList);

                //System.out.println(biomeTrainerRarity.get(customTrainers.get(trainer).biomes[idBiomeTrainer].toLowerCase()).get(idBiomeTrainer));

                idBiomeTrainer++;
            }
        }
    }


/*
    private void calculateRarity(){
        int biomeId = 0;
        for(String biome : biomeTrainersSpawn.keySet()){
            List<String> trainers = biomeTrainersSpawn.get(biome);
            int rarity = 0;
            List<Integer> rarityList = new ArrayList<>();

            for (String trainer : trainers){


                rarity += customTrainers.get(trainer).biomesTrainerRarity[biomeIDTrainer];
                System.out.println(biome+" "+trainer+" "+rarity);
                rarityList.add(rarity);
            }
            biomeTrainerRarity.put(biome, rarityList);
            biomeId++;
        }
    }
*/
    private void createDefault() throws IOException {
        trainersDir.mkdirs();
        File file = new File(trainersDir+"/defaultTrainer.json");
        file.createNewFile();
        writeTrainerFile("defaultTrainer.json",
            new CustomTrainer(
                    "default Name",
                    1,
                    "pixelmonfr/default",
                    new String[]{BiomeTypes.PLAINS.getName(),BiomeTypes.JUNGLE.getName()},
                    new int[]{80,50},
                    new CustomTrainerParty(
                            new CustomPokemonTrainer("pikachu","pikachu",5, new CustomPokemonStat(30,30,30,30,30,30),new CustomPokemonStat(30,30,30,30,30,30),new CustomAttacksPokemon("Tackle","","",""),"ADAMANT","STATIC","Ordinary","",true,0,""),null,null,null,null,null),
                    new String[]{},
                    "Default win message %player% à vaincu %trainer%",
                    "Default loss message %player% à vaicu %trainer%",
                    500
            )
        );

        writeTrainerFile("defaultTrainer2.json",
                new CustomTrainer(
                        "default Name2",
                        1,
                        "pixelmonfr/default2",
                        new String[]{BiomeTypes.PLAINS.getName(),BiomeTypes.JUNGLE.getName()},
                        new int[]{20,50},
                        new CustomTrainerParty(
                                new CustomPokemonTrainer("pikachu","pikachu",5, new CustomPokemonStat(30,30,30,30,30,30),new CustomPokemonStat(30,30,30,30,30,30),new CustomAttacksPokemon("Tackle","","",""),"ADAMANT","STATIC","Ordinary","",true,0,""),null,null,null,null,null
                        ),
                        new String[]{},
                        "Default win message2 %player% à vaincu %trainer%",
                        "Default loss message2 %player% à vaicu %trainer%",
                        500
                )
        );
    }

    private CustomTrainer getTrainerConfig(String customTrainer) throws IOException {
        return mapper.readValue(Paths.get(trainersDir.getPath()+"/"+customTrainer).toFile(), CustomTrainer.class);
    }
    private List<String> allTrainers(){
        File[] listOfFiles = trainersDir.listFiles();
        List<String> trainers = new ArrayList<>();
        for (File trainersFiles : listOfFiles) trainers.add(trainersFiles.getName());
        return trainers;
    }
    private void writeTrainerFile(String trainerPath, CustomTrainer trainer) throws IOException {
        writer.writeValue(new File(trainersDir+"/"+trainerPath), trainer);
    }
    private void writeNPCConfigFile(String configPath, Config config) throws IOException {
        writer.writeValue(new File(rootDir+"/"+configPath), config);
    }
    private Config getNPCsConfig() throws IOException {
        return mapper.readValue(Paths.get(rootDir.getPath()+"/"+"config.json").toFile(), Config.class);
    }

    public void reload(){
        playerNPCSpawned.clear();
        npcSpawnService.cancel();
        loadNPCsConfig();
        loadTrainers();
        loadNPCSpawnService();
    }

    public NPCTrainer createTrainer(CustomTrainer trainer, World world, double x, double y, double z){
        NPCTrainer npc = new NPCTrainer(world);
        npc.setBaseTrainer(NPCRegistryTrainers.getByName("steve"));
        npc.setName(trainer.name);
        npc.setTextureIndex(4);
        npc.setCustomSteveTexture(trainer.customTexture);
        npc.setLevel(trainer.level);


        Random r = new Random();
        float pitch = (float) (360 * Math.asin(2 * r.nextFloat() - 1) / Math.PI);


        //npc.winCommands = new ArrayList<>();

        npc.winMessage = trainer.winMessage.replace("%trainer%",trainer.name);


        //npc.loseCommands = new ArrayList<>();
        npc.loseMessage = trainer.lossMessage.replace("%trainer%",trainer.name);
        int id = 0;
        int pokemonsLevel = 0;
        List<Pokemon> trainerParty = createPartyTrainer(trainer.pokemons);
        for(Pokemon pokemon: trainerParty){
            npc.getPokemonStorage().set(id,pokemon);
            pokemonsLevel= pokemonsLevel+pokemon.getLevel();
            id++;
        }

        npc.winMoney = trainer.moneyReward;
        npc.winMoney = trainer.moneyReward/(pokemonsLevel/trainerParty.size());

        npc.setPositionAndRotation(x,y,z,90,pitch);
        npc.setBattleAIMode(EnumBattleAIMode.Advanced);
        world.spawnEntity(npc);
        return npc;
    }

    private List<Pokemon> createPartyTrainer(CustomTrainerParty customTrainerParty){
        List<Pokemon> pokemons = new ArrayList<>();

        List<CustomPokemonTrainer> customPokemonTrainers = new ArrayList<>();
        if(customTrainerParty.pokemon1 != null) customPokemonTrainers.add(customTrainerParty.pokemon1);
        if(customTrainerParty.pokemon2 != null) customPokemonTrainers.add(customTrainerParty.pokemon2);
        if(customTrainerParty.pokemon3 != null) customPokemonTrainers.add(customTrainerParty.pokemon3);
        if(customTrainerParty.pokemon4 != null) customPokemonTrainers.add(customTrainerParty.pokemon4);
        if(customTrainerParty.pokemon5 != null) customPokemonTrainers.add(customTrainerParty.pokemon5);
        if(customTrainerParty.pokemon6 != null) customPokemonTrainers.add(customTrainerParty.pokemon6);

        for(CustomPokemonTrainer customPokemon: customPokemonTrainers){
            if(customPokemon == null) continue;
            Pokemon pokemon = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(customPokemon.species));

            pokemon.setLevel(customPokemon.level);
            pokemon.setNickname(customPokemon.name);
            pokemon.setNature(EnumNature.natureFromString(customPokemon.nature));
            pokemon.setGrowth(EnumGrowth.growthFromString(customPokemon.growth));
            pokemon.setShiny(customPokemon.shiny);
            pokemon.setForm(customPokemon.form);
            pokemon.setCustomTexture(customPokemon.customTexture);

            if(customPokemon.item != null)
                if(!customPokemon.item.equals("")){
                    Optional<Item> item = Optional.ofNullable(Item.getByNameOrId(customPokemon.item));
                    item.ifPresent(value -> pokemon.setHeldItem(new ItemStack(value)));
                }

            Attack[] attackList = new Attack[4];

            if(AttackBase.getAttackBase(customPokemon.attacks.attack1).isPresent()) attackList[0] = new Attack(customPokemon.attacks.attack1);
            else attackList[0] = null;

            if(AttackBase.getAttackBase(customPokemon.attacks.attack2).isPresent()) attackList[1] = new Attack(customPokemon.attacks.attack2);
            else attackList[1] = null;

            if(AttackBase.getAttackBase(customPokemon.attacks.attack3).isPresent()) attackList[2] = new Attack(customPokemon.attacks.attack3);
            else attackList[2] = null;

            if(AttackBase.getAttackBase(customPokemon.attacks.attack4).isPresent()) attackList[3] = new Attack(customPokemon.attacks.attack4);
            else attackList[3] = null;

            pokemon.setMoveset(new Moveset(new Attack[]{
                    attackList[0],
                    attackList[1],
                    attackList[2],
                    attackList[3]
            }));

            pokemon.setAbility(customPokemon.ability);

            pokemon.getEVs().setStat(StatsType.HP, customPokemon.evs.hp);
            pokemon.getEVs().setStat(StatsType.Attack, customPokemon.evs.atk);
            pokemon.getEVs().setStat(StatsType.Defence, customPokemon.evs.def);
            pokemon.getEVs().setStat(StatsType.SpecialAttack, customPokemon.evs.spAtk);
            pokemon.getEVs().setStat(StatsType.SpecialDefence, customPokemon.evs.spDef);
            pokemon.getEVs().setStat(StatsType.Speed, customPokemon.evs.speed);

            pokemon.getIVs().setStat(StatsType.HP, customPokemon.ivs.hp);
            pokemon.getIVs().setStat(StatsType.Attack, customPokemon.ivs.atk);
            pokemon.getIVs().setStat(StatsType.Defence, customPokemon.ivs.def);
            pokemon.getIVs().setStat(StatsType.SpecialAttack, customPokemon.ivs.spAtk);
            pokemon.getIVs().setStat(StatsType.SpecialDefence, customPokemon.ivs.spDef);
            pokemon.getIVs().setStat(StatsType.Speed, customPokemon.ivs.speed);

            pokemons.add(pokemon);
        }
        return pokemons;
    }
    // 1500  500   7000
    public int getNumberOfMaxRarity(String biome){
        if(biomeTrainerRarity.containsKey(biome)) return biomeTrainerRarity.get(biome).get(biomeTrainerRarity.get(biome).size()-1);
        return -1;
    }

    public String getRandomNPC(String biome,String wordlName){
        int rarityMax = getNumberOfMaxRarity(biome);
        int randomRarity = new Random().nextInt(rarityMax);
        int idChoose = 0;
        int exRarityChoose = 0;
        int id = 0;

        for(int rarity : biomeTrainerRarity.get(biome)){
            if (!Arrays.stream(customTrainers.get(biomeTrainersSpawn.get(biome).get(id)).activeWorld).map(String::toLowerCase).collect(Collectors.toList()).contains(wordlName.toLowerCase())){
                id++;
                continue;
            }
            if(rarity>randomRarity && exRarityChoose<=randomRarity) {
                idChoose = id;
                exRarityChoose = rarity;
            }
            id++;
        }
        return biomeTrainersSpawn.get(biome).get(idChoose);
    }

    public NPCTrainer createCustomNPC(Player p,World world, double posX, double posY, double posZ, String biome){
        if(!biomeTrainersSpawn.containsKey(biome.toLowerCase())) return null;

        CustomTrainer customTrainer = customTrainers.get(getRandomNPC(biome,p.getWorld().getName()));
        if(!Arrays.stream(customTrainer.activeWorld).map(String::toLowerCase).collect(Collectors.toList()).contains(p.getWorld().getName().toLowerCase())) return null;

        NPCTrainer npc = createTrainer(customTrainer,world,posX,posY,posZ);
        List<UUID> trainers = new ArrayList<>();

        if(playerNPCSpawned.containsKey(p.getName())) trainers = playerNPCSpawned.get(p.getName());
        trainers.add(npc.getUniqueID());
        npcPlayerSpawned.put(npc.getUniqueID(),p.getName());
        playerNPCSpawned.put(p.getName(),trainers);

        return npc;
    }

    private void loadNPCsConfig(){
        File file = new File(rootDir+"/config.json");
        try {
            if(!file.exists()) {
                file.createNewFile();
                writeNPCConfigFile("config.json", new Config(100,10,10,6, 600));
            }
            config = getNPCsConfig();
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    private void loadNPCSpawnService(){
        try {
            System.out.println("NPCSpawnService loaded");
            npcSpawnService  = Sponge.getScheduler().createTaskBuilder().execute( () -> {
                for(Player player : Sponge.getServer().getOnlinePlayers()) {

                    if (playerNPCSpawned.containsKey(player.getName()))
                        if (playerNPCSpawned.get(player.getName()).size() > config.maxNpcsPerPlayer) continue;
                    if (new Random().nextInt(100) > config.tentativePercentSpawn) continue;

                    int sizeX = config.spawnRange;
                    int sizeZ = config.spawnRange;

                    double rLocX = player.getPosition().getX() + new Random().nextInt(sizeX) - (sizeX / 2);
                    double rLocZ = player.getPosition().getZ() + new Random().nextInt(sizeZ) - (sizeZ / 2);
                    double rLocY = player.getWorld().getHighestYAt((int) rLocX, (int) rLocZ) + 1;

                    Sponge.getScheduler().createTaskBuilder().execute(() -> {
                        NPCTrainer npc = TrainersManager.INSTANCE.createCustomNPC(player, ((Entity) player).world, rLocX, rLocY, rLocZ, player.getWorld().getBiome((int) rLocX, (int) rLocY, (int) rLocZ).getName().toLowerCase());
                        if(npc==null) return;
                        Sponge.getScheduler().createTaskBuilder().execute(() -> {
                            removeNPCPlayer(npc.getUniqueID(), player);
                        }).delay(config.npcDispawnTime, TimeUnit.SECONDS).submit(PixelmonFRTrainer.INSTANCE);
                    }).submit(PixelmonFRTrainer.INSTANCE);
                }
            }).interval(config.spawnTentativeInterval, TimeUnit.SECONDS).async().submit(PixelmonFRTrainer.INSTANCE);
        }
        catch (Exception e){e.printStackTrace();}
    }

    public void removeNPCPlayer(UUID npc, Player player){
        Sponge.getScheduler().createTaskBuilder().execute(() -> {
            List<UUID> trainers = playerNPCSpawned.get(player.getName());

            Sponge.getScheduler().createTaskBuilder().execute(() -> {
                trainers.remove(npc);
            }).submit(PixelmonFRTrainer.INSTANCE);

            playerNPCSpawned.put(player.getName(),trainers);
            if(npcPlayerSpawned.containsKey(npc)) npcPlayerSpawned.remove(npc);
        }).async().submit(PixelmonFRTrainer.INSTANCE);

        Sponge.getScheduler().createTaskBuilder().execute(() -> {
            if(player.getWorld().getEntity(npc).isPresent()) player.getWorld().getEntity(npc).get().remove();
        }).submit(PixelmonFRTrainer.INSTANCE);
    }
}
