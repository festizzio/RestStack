package com.seanlubbers.stacker.model;

public class PokemonJacksonTest {

    private String name;
    private int pokedex;
    private int attack;
    private int defense;
    private int stamina;

    public PokemonJacksonTest(String name, int pokedex, int attack, int defense, int stamina) {
        this.name = name;
        this.pokedex = pokedex;
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
    }

    public PokemonJacksonTest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPokedex() {
        return pokedex;
    }

    public void setPokedex(int pokedex) {
        this.pokedex = pokedex;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }


}
