package com.seanlubbers.stacker.model;

import com.seanlubbers.stacker.rest.InvalidCpException;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="pokemon_rewards")
public class PokemonReward {

    // == field variables ==

    // == base variables ==
    private int baseAttack;
    private int baseDefense;
    private int baseStamina;
    private int pokedexNumber;
    private int CP;
    private String pokemonName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pokemonId;

    @Transient
    private String ivValuesPerCp;

    @Transient
    private List<Integer> possibleCPValues;
    @Transient
    private int stardustValue;
    @Transient
    private Map<Integer, List<IvValues>> mapOfIvValues;
    @Transient
    private static List<IvValues> ivList = calculateListOfIVs();

    // == constructors ==

    // So JPA will call the empty, default constructor when it loads the entities from the table.
    // Putting anything in here will throw an error, so if you need to do any post-processing on
    // the loaded entity, you need to use the @PostLoad annotation. @PostConstruct doesn't work
    // because JPA does not work with the Spring container/beans.
    public PokemonReward() {

    }

    public PokemonReward(Pokemon pokemon, int CP) {
        this(pokemon.getPokedex(), pokemon.getName(), pokemon.getAttack(), pokemon.getDefense(), pokemon.getStamina(), CP);
    }

    public PokemonReward(int pokedexNumber, String pokemonName, int baseAttack, int baseDefense, int baseStamina) {
        this.pokedexNumber = pokedexNumber;
        this.pokemonName = pokemonName;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseStamina = baseStamina;
    }

    public PokemonReward(int pokedexNumber, String pokemonName, int baseAttack, int baseDefense, int baseStamina, int CP) {

        this.pokedexNumber = pokedexNumber;
        this.pokemonName = pokemonName;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseStamina = baseStamina;
        possibleCPValues = new ArrayList<>();
        mapOfIvValues = new HashMap<>();
        calculatePossibleCPValues();
        possibleCPValues.sort(Comparator.naturalOrder());

        // == initialize list of evolved Pokemon - these are worth extra stardust ==
        final List<String> stage1EvoName = Arrays.asList("Graveler", "Rhydon", "Poliwhirl", "Monferno",
                "Combusken", "Porygon2", "Raichu", "Skiploom", "Loudred", "Umbreon", "Azumarill");
        final List<String> stage2EvoName = Arrays.asList("Venusaur", "Charizard");
        if(stage2EvoName.contains(pokemonName)) {
            stardustValue = 500;
        } else if(stage1EvoName.contains(pokemonName)) {
            stardustValue = 300;
        } else {
            stardustValue = 100;
        }

        if(CP > 0) {
            this.CP = CP;
        } else {
            this.CP = calculateCP(15, 15, 15);
        }

        calculateIvPercentagePerCP();
    }

    @PostLoad
    public void init() {
        possibleCPValues = new ArrayList<>();
        mapOfIvValues = new HashMap<>();
        calculatePossibleCPValues();
        possibleCPValues.sort(Comparator.naturalOrder());
        calculateIvPercentagePerCP();
    }

    // == private methods ==

    // Calculate the list of possible CP values for this Pokemon based on level 15 with any of the IV values given.
    private void calculatePossibleCPValues() {
        int CP;
        List<IvValues> listOfIvValues;
        for(IvValues currentIVs : ivList) {

            CP = calculateCP(currentIVs.getAttackIV(), currentIVs.getDefenseIV(), currentIVs.getStaminaIV());
            listOfIvValues = new ArrayList<>();

            if(!mapOfIvValues.isEmpty()) {
                if(mapOfIvValues.containsKey(CP)) {
                    listOfIvValues = mapOfIvValues.get(CP);
                }
            }

            listOfIvValues.add(currentIVs);

            listOfIvValues.sort(Comparator.comparingDouble(IvValues::getIvPercentage));
            mapOfIvValues.put(CP, listOfIvValues);
        }
        fillPossibleCPValueList();
    }

    private int calculateCP(int attackIV, int defenseIV, int staminaIV) {
        // Formula to calculate the CP of a Pokemon based on its known IVs and level per gamepress.gg.
        // The arbitrary CP multiplier for level 15 (the level of all research rewards) is 0.51739395.
        // https://gamepress.gg/pokemongo/cp-multiplier
        return (int) ((baseAttack + attackIV) * Math.pow((baseDefense + defenseIV), 0.5) *
                Math.pow((baseStamina + staminaIV), 0.5) * Math.pow(0.51739395, 2)) / 10;
    }

    private void fillPossibleCPValueList(){
        possibleCPValues.addAll(mapOfIvValues.keySet());
        possibleCPValues.sort(Comparator.naturalOrder());
    }

    // For each CP, there are several different possibilities for IV percentages. This sets the range.
    public void calculateIvPercentagePerCP() {
        StringBuilder sb = new StringBuilder();
        List<IvValues> valuesPerCp;

        if(!mapOfIvValues.containsKey(CP)){
            throw new InvalidCpException("Invalid CP of " + CP + " for Pokemon " + pokemonName);
        } else {
            valuesPerCp = mapOfIvValues.get(CP);
            sb.append(valuesPerCp.get(0).getIvPercentage());
            if(!(valuesPerCp.get(0).getIvPercentage() == valuesPerCp.get(valuesPerCp.size() - 1).getIvPercentage())) {
                sb.append("% - ");
                sb.append(valuesPerCp.get(valuesPerCp.size() - 1).getIvPercentage());
            }
            sb.append("%");
            this.ivValuesPerCp = sb.toString();
        }
    }

    // == public methods ==

    public String getPokemonName() {
        return this.pokemonName;
    }

    public int getStardustValue() {
        return stardustValue;
    }

    public int getCP() {
        return CP;
    }

    public List<Integer> getPossibleCPValues() {
        return possibleCPValues;
    }

    public int getPokedexNumber() {
        return this.pokedexNumber;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseStamina() {
        return baseStamina;
    }

    public String getIvValuesPerCp() {
        return this.ivValuesPerCp;
    }

    public boolean wasNotFound() {
        return pokemonName == null;
    }

    // == Overridden methods ==

    @Override
    public String toString() {
        return this.pokemonName + ": CP " + this.CP;
    }

    // == static methods ==

    // IV floor for research tasks is 10/10/10, and these values don't change between Pokemon.
    // No reason to call it every time you instantiate a new Pokemon object hence it being static.
    private static List<IvValues> calculateListOfIVs() {
        List<IvValues> ivValues = new ArrayList<>();
        for(int attackIV = 10; attackIV <= 15; attackIV++) {
            for (int defenseIV = 10; defenseIV <= 15; defenseIV++) {
                for (int staminaIV = 10; staminaIV <= 15; staminaIV++) {
                    ivValues.add(new IvValues(attackIV, defenseIV, staminaIV));
                }
            }
        }
        return ivValues;
    }
}
