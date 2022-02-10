package com.seanlubbers.stacker.pokemondao;

import com.seanlubbers.stacker.model.Pokemon;
import com.seanlubbers.stacker.service.PokemonService;
import com.seanlubbers.stacker.utils.SQLiteQueries;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PokemonSqlite {
    private Connection conn;
    private Map<String, Pokemon> pokemonMap = new HashMap<>();
    private static PokemonSqlite instance = new PokemonSqlite();

    public static PokemonSqlite getInstance() {
        if(instance == null) {
            instance = new PokemonSqlite();
        }
        return instance;
    }

    public void open() {
        try {
            conn = DriverManager.getConnection(SQLiteQueries.CONNECTION_STRING);
            loadPokemon();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPokemon() {
        try(PreparedStatement statement = conn.prepareStatement(SQLiteQueries.QUERY_ALL);
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                String name = resultSet.getString(SQLiteQueries.COLUMN_POKEMON_NAME);
                int pokedex = resultSet.getInt(SQLiteQueries.COLUMN_POKEDEX_NUMBER);
                int attack = resultSet.getInt(SQLiteQueries.COLUMN_BASE_ATTACK);
                int defense = resultSet.getInt(SQLiteQueries.COLUMN_BASE_DEFENSE);
                int stamina = resultSet.getInt(SQLiteQueries.COLUMN_BASE_STAMINA);
                pokemonMap.put(name, new Pokemon(attack, defense, stamina, pokedex, name));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Pokemon> getPokemonMap() {
        return pokemonMap;
    }
}
