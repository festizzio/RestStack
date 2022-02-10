package com.seanlubbers.stacker.pokemondao;

import com.seanlubbers.stacker.model.Pokemon;
import com.seanlubbers.stacker.model.PokemonReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {
}
