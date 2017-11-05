/*
 * Copyright (C) 2017 romuald.fotso
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package oop.stock.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import oop.stock.model.AbstractModel;
import oop.stock.model.Product;
import oop.stock.model.ProductRecords;
import oop.stock.view.StockGUI;

/**
 *
 * @author romuald.fotso
 */
public class ProductController extends AbstractController {
    private StockGUI stockGUI;
    
    public ProductController(AbstractModel model) {
        super(model);
    }
    
    public ProductController(AbstractModel model, StockGUI stockGUI) {
        super(model);
        this.stockGUI = stockGUI;
    }
    
    @Override
    public int calculate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read_data(String pathname) {
        File file = new File(pathname);
        System.out.println("ProductController > read_data: "+file.getAbsolutePath());        
        FileReader fr = null;
        HashMap<String, Product> products = new HashMap<String, Product>();
        
        try{
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
                    
            String line;
            while((line = br.readLine()) != null)
            {
                //System.out.println("ProductController > line: "+line);                
                String[] data = line.split(",");
                Product product = new Product(data[0], Float.parseFloat(data[1]), 
                                        data[2]);
                products.put(product.getCode(), product);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ProductRecords prod_records = new ProductRecords(products);
        prod_records.addObserver(stockGUI); // link Model -> View        
        
        this.model = prod_records;
        System.out.println("ProductController > new model set: prod_records");
        prod_records.notifyObservers(); // update stockGUI
    }

    @Override
    public void save_data(String pathname) {
        
    }
    
}
