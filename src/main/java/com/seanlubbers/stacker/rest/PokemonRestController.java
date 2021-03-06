package com.seanlubbers.stacker.rest;

import com.seanlubbers.stacker.model.Pokemon;
import com.seanlubbers.stacker.model.PokemonReward;
import com.seanlubbers.stacker.pokemondao.PokemonSqlite;
import com.seanlubbers.stacker.service.PokemonRewardService;
import com.seanlubbers.stacker.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PokemonRestController {

    private PokemonService pokemonService;
    private PokemonRewardService pokemonRewardService;

    @Autowired
    public PokemonRestController(PokemonService pokemonService, PokemonRewardService pokemonRewardService) {
        this.pokemonService = pokemonService;
        this.pokemonRewardService = pokemonRewardService;
    }

    @PostConstruct
    public void loadPokemon() {

    }

    // add code for the "/pokemon" endpoint

    @GetMapping("/pokemon_rewards")
    public Collection<PokemonReward> getAllRewards() {
        return pokemonRewardService.getRewards();
    }

    @GetMapping("/pokemon/{name}")
    public Pokemon getPokemon(@PathVariable String name) {
        Pokemon thePokemon = pokemonService.getPokemon(name);
        if(thePokemon == null) {
            throw new PokemonNotFoundException("Pokemon " + name + " not found.");
        }
        return thePokemon;
    }
//
//    @PostMapping("/pokemon/{pokemonName}")
//    public String addPokemon(@PathVariable String pokemonName) {
//        PokemonSqlite.getInstance().open();
//        Pokemon pokemon = PokemonSqlite.getInstance().getPokemonMap().get(pokemonName);
//        System.out.println(pokemon);
//        pokemonService.savePokemon(pokemon);
//        return "Successfully added " + pokemonName + " to the MySQL database from SQLite.";
//    }

    @PostMapping("/pokemon/stack/{pokemonName}/{CP}")
    public PokemonReward addStackPokemon(@PathVariable String pokemonName, @PathVariable int CP) {
        PokemonReward pokemonReward = new PokemonReward(pokemonService.getPokemon(pokemonName), CP);
        pokemonRewardService.addReward(pokemonReward);
        return pokemonReward;
    }

    @GetMapping("/pokemon/stack")
    public List<PokemonReward> getStack() {
        return pokemonRewardService.getRewards();
    }

    // Method to check IVs of any research Pokemon (i.e. level 20 Pokemon with IV floor of 10/10/10
    @GetMapping("/pokemon/IVs/{name}/{CP}")
    public PokemonReward getIVs(@PathVariable String name, @PathVariable int CP) {
        PokemonReward pokemonReward = new PokemonReward(pokemonService.getPokemon(name), CP);
        return pokemonReward;
    }

}
