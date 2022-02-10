package com.seanlubbers.stacker.service;

import com.seanlubbers.stacker.model.Pokemon;
import com.seanlubbers.stacker.model.PokemonReward;

import java.util.List;

public interface PokemonService {
    List<Pokemon> getAllPokemon();
    Pokemon getPokemon(String name);
    void savePokemon(Pokemon pokemon);
    void deletePokemon(String name);
    void saveAllPokemon();
}
