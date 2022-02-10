package com.seanlubbers.stacker.pokemondao;

import com.seanlubbers.stacker.model.PokemonReward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackRepository extends JpaRepository<PokemonReward, String> {
}
