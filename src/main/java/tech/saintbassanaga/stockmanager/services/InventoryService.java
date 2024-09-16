package tech.saintbassanaga.stockmanager.services;

/*
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */

import tech.saintbassanaga.stockmanager.dtos.CreateProductDto;
import tech.saintbassanaga.stockmanager.models.Inventory;
import tech.saintbassanaga.stockmanager.models.Product;

import java.io.FileNotFoundException;
import java.util.List;

public interface InventoryService {
    Inventory createInventory(List<Product> products) throws FileNotFoundException;
    Inventory createFullInventory() throws FileNotFoundException;
}

