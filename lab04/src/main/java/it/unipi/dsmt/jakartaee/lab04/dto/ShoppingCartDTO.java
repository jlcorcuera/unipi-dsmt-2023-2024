package it.unipi.dsmt.jakartaee.lab04.dto;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {

    private List<BeerDTO> beerDTOList;

    public ShoppingCartDTO(){
        beerDTOList = new ArrayList<>();
    }

    public void addProduct(String productId, String name){
        BeerDTO found = null;
        for(BeerDTO beerDTO: beerDTOList) {
            if (beerDTO.getId().equals(productId)) {
                found = beerDTO;
                break;
            }
        }
        if (found != null) {
            found.setQuantity(found.getQuantity() + 1);
        } else {
            found = new BeerDTO(productId, name, 1);
            beerDTOList.add(found);
        }
    }

    public List<BeerDTO> getBeerDTOList() {
        return beerDTOList;
    }

    public int getTotalNumberProducts() {
        return beerDTOList.stream()
                .map(beerDTO -> beerDTO.getQuantity())
                .reduce(0, Integer::sum);
    }

}
