package com.seanlubbers.stacker.service;

import com.seanlubbers.stacker.model.Pokemon;
import com.seanlubbers.stacker.pokemondao.PokemonRepository;
import com.seanlubbers.stacker.pokemondao.PokemonSqlite;
import com.seanlubbers.stacker.rest.PokemonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PokemonServiceImpl implements PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    @Override
    public Pokemon getPokemon(String name) {
        if(pokemonRepository.findById(name).isEmpty()) {
            throw new PokemonNotFoundException("Pokemon " + name + " was not found in the list.");
        }
        return pokemonRepository.getById(name);
    }

    @Override
    public void savePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }

    @Override
    public void deletePokemon(String name) {

    }

    @Override
    public void saveAllPokemon() {
        PokemonSqlite.getInstance().open();
        Map<String, Pokemon> pokemonMap = PokemonSqlite.getInstance().getPokemonMap();
        for(Pokemon pokemon : pokemonMap.values()) {
            savePokemon(pokemon);
        }
    }
}
