package com.seanlubbers.stacker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

// Pokemon does not need to care about/store the stardust value, the CP of the claimed reward, nor the possible IV range
// of the claimed reward. It does need the standard Pokemon attributes as well as the list of possible CPs and IVs for
// the Pokemon at level 15. This class is used for displaying the Pokemon to claim the reward. After claiming, it is then
// saved as a PokemonReward.

@Entity
@Table(name="pokemon")
public class Pokemon {
    private int attack;
    private int defense;
    private int stamina;
    private int pokedex;

    @Id
    private String name;

    @Transient
    private Set<Integer> possibleCP = new TreeSet<>();

    @Transient
    private static List<IvValues> ivList = calculateListOfIVs();

    public Pokemon() {

    }

    public Pokemon(int attack, int defense, int stamina, int pokedex, String name) {
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
        this.pokedex = pokedex;
        this.name = name;
        calculatePossibleCPValues();
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getStamina() {
        return stamina;
    }

    public int getPokedex() {
        return pokedex;
    }

    public String getName() {
        return name;
    }

    // Calculate the list of possible CP values for this Pokemon based on level 15 with any of the IV values given.
    private void calculatePossibleCPValues() {
        int CP;
        for(IvValues currentIVs : ivList) {
            CP = calculateCP(currentIVs.getAttackIV(), currentIVs.getDefenseIV(), currentIVs.getStaminaIV());
            possibleCP.add(CP);
        }
    }

    private int calculateCP(int attackIV, int defenseIV, int staminaIV) {
        // Formula to calculate the CP of a Pokemon based on its known IVs and level per gamepress.gg.
        // The arbitrary CP multiplier for level 15 (the level of all research rewards) is 0.51739395.
        // https://gamepress.gg/pokemongo/cp-multiplier
        return (int) ((attack + attackIV) * Math.pow((defense + defenseIV), 0.5) *
                Math.pow((stamina + staminaIV), 0.5) * Math.pow(0.51739395, 2)) / 10;
    }

    public Set<Integer> getPossibleCPValues() {
        return possibleCP;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != getClass()) {
            return false;
        }
        return name.equals(((Pokemon) obj).getName());
    }

    @Override
    public String toString() {
        return pokedex + ". " +
                name + ": " +
                attack + ", " + defense + ", " + stamina;
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

    public static List<IvValues> getIvList() {
        return ivList;
    }
}
