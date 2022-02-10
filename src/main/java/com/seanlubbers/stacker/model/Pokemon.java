package com.seanlubbers.stacker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pokemon")
public class Pokemon {
    private int attack;
    private int defense;
    private int stamina;
    private int pokedex;

    @Id
    private String name;

    public Pokemon() {

    }

    public Pokemon(int attack, int defense, int stamina, int pokedex, String name) {
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
        this.pokedex = pokedex;
        this.name = name;
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
}
