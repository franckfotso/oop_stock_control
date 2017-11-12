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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void read_data(String pathname) {
        File file = new File(pathname);
        System.out.println("ProductController > read_data: "+file.getAbsolutePath());        
        FileReader fr = null;
        BufferedReader br = null;
        HashMap<String, Product> products = new HashMap<String, Product>();
        
        try{
            fr = new FileReader(file);
            br = new BufferedReader(fr);
                    
            String line;
            while((line = br.readLine()) != null)
            {
                //System.out.println("ProductController > line: "+line);                
                String[] data = line.split(",");
//                Product product = new Product(data[0], Float.parseFloat(data[1]), 
//                                        data[2]);
                Product product = new Product(data[0], 0, data[1]);
                products.put(product.getCode(), product);
            }
            
            // read prices
            fr = new FileReader(new File(this.stockGUI.PRICES_FILE));
            br = new BufferedReader(fr);
            
            line = "";
            while((line = br.readLine()) != null)
            {
                String[] data = line.split(",");
                String prod_code = data[0];
                
                if(products.containsKey(prod_code))
                {
                    Product prod = products.get(prod_code);
                    prod.setPrice(Float.parseFloat(data[1]));
                }
                else 
                    System.out.println("ProductController > "
                        + "unable to add price, prod_code: "+prod_code);
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
        File file = new File(pathname);
        System.out.println("ProductController > save_data: "+file.getAbsolutePath());
        FileWriter fw = null;
        BufferedWriter bw = null;
        HashMap<String,Product> products = ((ProductRecords)(this.model)).getProducts();
        //System.out.println("ProductController > len(products): "+products.size());
        
        try{
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            
            for (Map.Entry<String, Product> entry: products.entrySet())
            {
                String code = entry.getKey();
                Product product = entry.getValue();
                
                //String line = code+","+product.getPrice()+","+product.getDescription()+"\n";
                String line = code+","+product.getDescription()+"\n";
                bw.write(line);
            }            
            bw.close();
            fw.close();
            
            // write prices
            fw = new FileWriter(new File(this.stockGUI.PRICES_FILE));
            bw = new BufferedWriter(fw);
            
            for (Map.Entry<String, Product> entry: products.entrySet())
            {
                String code = entry.getKey();
                Product product = entry.getValue();
                
                String line = code+","+product.getPrice()+"\n";
                bw.write(line);
            }            
            bw.close();
            fw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
