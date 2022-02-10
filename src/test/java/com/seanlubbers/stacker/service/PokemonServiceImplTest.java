package com.seanlubbers.stacker.service;

import com.seanlubbers.stacker.model.Pokemon;
import com.seanlubbers.stacker.pokemondao.PokemonRepository;
import com.seanlubbers.stacker.pokemondao.PokemonSqlite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PokemonServiceImplTest {

    @Test
    public void getAllPokemonTest() {
        PokemonSqlite.getInstance().open();
        Map<String, Pokemon> pokemonMap = PokemonSqlite.getInstance().getPokemonMap();
        Pokemon sandshrew = new Pokemon(126,120,137,27,"sandshrew");
        Pokemon seadra = new Pokemon(187, 156, 146, 117, "seadra");
        assertTrue(pokemonMap.containsKey(sandshrew.getName()));
        assertTrue(pokemonMap.containsValue(sandshrew));
        assertTrue(pokemonMap.containsKey(seadra.getName()));
        assertTrue(pokemonMap.containsValue(seadra));
//        for(Pokemon pokemon : pokemonMap.values()) {
//            System.out.println(pokemon);
//        }
    }

}