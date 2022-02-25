package com.seanlubbers.stacker.model;

import com.seanlubbers.stacker.utils.StardustValues;

import javax.persistence.*;

// PokemonReward needs to store the CP, the possible IVs for the given CP & Pokemon, the stardust value, and the basic Pokemon attributes such as
// name, attack, defense, stamina, and pokedex number for serialization/deserialization. It does not need to calculate the possible IV combos, nor
// does it need to calculate the possible CP values of the Pokemon at level 15. This is for storing the actual claimed reward only and not the
// potential reward. We will save the Pokemon class for that.

@Entity
@Table(name="pokemon_rewards")
public class PokemonReward {

    // == field variables ==

    // == base variables ==
    private int CP;
    private String pokemonName;
    private String ivValuesPerCP;
    @Transient private int stardustValue;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pokemonId;

    // == constructors ==

    // So JPA will call the empty, default constructor when it loads the entities from the table.
    // Putting anything in here will throw an error, so if you need to do any post-processing on
    // the loaded entity, you need to use the @PostLoad annotation. @PostConstruct doesn't work
    // because JPA does not work with the Spring container/beans.
    public PokemonReward() {

    }

    public PokemonReward(Pokemon pokemon, int CP) {

        this.pokemonName = pokemon.getName();
        this.CP = CP;

        if(StardustValues.worth500.contains(pokemonName)) {
            stardustValue = 500;
        } else if(StardustValues.worth300.contains(pokemonName)) {
            stardustValue = 300;
        } else {
            stardustValue = 100;
        }

        this.ivValuesPerCP = pokemon.getIvValuesPerCp(CP);
    }

    @PostLoad
    public void init() {

    }

    // == private methods ==

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

    public boolean wasNotFound() {
        return pokemonName == null;
    }

    // == Overridden methods ==

    @Override
    public String toString() {
        return this.pokemonName + ": CP " + this.CP;
    }
}
