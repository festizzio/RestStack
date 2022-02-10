package com.seanlubbers.stacker.service;

import com.seanlubbers.stacker.model.PokemonReward;

import java.util.List;

public interface PokemonRewardService {
    void catchReward();
    void catchALl();
    void addReward(PokemonReward pokemonReward);
    List<PokemonReward> getRewards();
}
