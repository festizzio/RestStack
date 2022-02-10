package com.seanlubbers.stacker.service;

import com.seanlubbers.stacker.model.PokemonReward;
import com.seanlubbers.stacker.pokemondao.PokemonRewardRepository;
import com.seanlubbers.stacker.rest.PokemonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonRewardServiceImpl implements PokemonRewardService {
    @Autowired
    private PokemonRewardRepository rewardRepository;

    @Override
    public void catchReward() {
        // method to catch the oldest Pokemon in the stack
    }

    @Override
    public void catchALl() {
        // method to clear the stack (big catching day, caught everything while out)
    }

    @Override
    public void addReward(PokemonReward pokemonReward) {
        rewardRepository.save(pokemonReward);
    }

    @Override
    public List<PokemonReward> getRewards() {
        return rewardRepository.findAll();
    }
}
