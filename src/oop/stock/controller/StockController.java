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
import java.io.IOException;
import java.util.HashMap;
import oop.stock.model.AbstractModel;
import oop.stock.model.Product;
import oop.stock.model.ProductRecords;
import oop.stock.model.Stock;
import oop.stock.model.StockRecords;
import oop.stock.view.StockGUI;

/**
 *
 * @author romuald.fotso
 */
public class StockController extends AbstractController{
    
    private StockGUI stockGUI;
    
    public StockController(AbstractModel model) {
        super(model);
    }
    
    public StockController(AbstractModel model, StockGUI stockGUI) {
        super(model);
        this.stockGUI = stockGUI;
    }

    @Override
    public int calculate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read_data(String pathname){
        File file = new File(pathname);
        System.out.println("StockController > read_data: "+file.getAbsolutePath());
        FileReader fr = null;
        HashMap<String, HashMap<String, Stock>> stocks = 
                new HashMap<String, HashMap<String, Stock>>();
        
        try{
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
                    
            String line;
            while((line = br.readLine()) != null)
            {
                //System.out.println("ProductController > line: "+line);                
                String[] data = line.split(",");
                String delivery = data[0];
                String prod_code = data[1];
                int quanty = Integer.parseInt(data[2]);
                
                if (stocks.containsKey(prod_code))
                {
                    HashMap<String, Stock> stock_by_deliv = stocks.get(prod_code);
                    
                    if (stock_by_deliv.containsKey(delivery))
                    {
                        throw new Exception("[ERROR] duplicate stock found");
                    }
                    else{
                        Stock stock = new Stock(delivery, prod_code, null, quanty);
                        stock_by_deliv.put(delivery, stock);
                    }
                    stocks.put(prod_code, stock_by_deliv);                    
                }
                else
                {
                    HashMap<String, Stock> stock_by_deliv = 
                            new HashMap<String, Stock>();
                    
                    Stock stock = new Stock(delivery, prod_code, null, quanty);
                        stock_by_deliv.put(delivery, stock);
                        
                    stocks.put(prod_code, stock_by_deliv);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        StockRecords stock_records = new StockRecords(stocks);
        stock_records.addObserver(stockGUI); // link Model -> View        
        
        this.model = stock_records;
        System.out.println("StockController > new model set: stock_records");
        stock_records.notifyObservers(); // update stockGUI
    
    }

    @Override
    public void save_data(String pathname) {
        
    }
    
}
