package it.unipi.dsmt.jakartaee.lab04.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unipi.dsmt.jakartaee.lab04.dto.BeerDTO;
import it.unipi.dsmt.jakartaee.lab04.entity.Beer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BeersDAO {

    private static List<Beer> beerList = new ArrayList<Beer>();

    public static void init() {
        try {
            String beersJSONFile = "data/beers.json";
            ClassLoader classLoader = BeersDAO.class.getClassLoader();
            URL resource = classLoader.getResource(beersJSONFile);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(resource);

            JsonNode searchResultNode = jsonNode.get("search_results");
            Iterator<JsonNode> iterator = searchResultNode.iterator();
            while (iterator.hasNext()) {
                JsonNode beerJSON = iterator.next();
                Beer beer = new Beer();
                beer.setId(UUID.randomUUID().toString());
                beer.setName(beerJSON.get("title").asText());
                beer.setAsin(beerJSON.get("asin").asText());
                beer.setLink(beerJSON.get("link").asText());
                beer.setImage(beerJSON.get("image").asText());
                beer.setRating(beerJSON.get("rating").asDouble());
                beer.setRatingsTotal(beerJSON.get("ratings_total").asDouble());
                beerList.add(beer);
            }
            System.out.println("Loaded " + beerList.size() + " beers.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void create(BeerDTO beerDTO){
        Beer beer = new Beer();
        beer.setId(UUID.randomUUID().toString());
        beer.setName(beerDTO.getName());
        beer.setImage(beerDTO.getImageUrl());
        beer.setRating(beerDTO.getRating());
        beerList.add(beer);
    }
    public static List<BeerDTO> search(String keyword){
        return beerList.stream()
                .filter(beer -> beer.getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(beer -> new BeerDTO(beer.getId(), beer.getName(), beer.getLink(), beer.getImage(), beer.getRating()))
                .collect(Collectors.toList());
    }

}