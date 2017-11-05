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
package oop.stock.view;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import oop.stock.controller.AbstractController;
import oop.stock.controller.ProductController;
import oop.stock.controller.StockController;
import oop.stock.model.Product;
import oop.stock.model.ProductRecords;
import oop.stock.model.Stock;
import oop.stock.model.StockRecords;
import oop.stock.observer.Observable;
import oop.stock.observer.Observer;

/**
 *
 * @author romuald.fotso
 */
public class StockGUI extends JFrame implements Observer{
    
    private HashMap<String,AbstractController> controllers;
    private String products_file = "oop/stock/data/prod-data.txt";
    private String stocks_file = "oop/stock/data/stock-data.txt";
    private String prices_file = "oop/stock/data/prices.txt";
    
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_search;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator sep_H;
    private javax.swing.JSeparator sep_V;
    private javax.swing.JTable tab_products;
    private javax.swing.JTextField tf_search;

    public StockGUI(HashMap<String, AbstractController> controllers) {
        this.controllers = controllers;        
        
        this.setTitle("OOP - Stock Control");
        this.setMinimumSize(new Dimension(90,64)); // NRel.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // configure GUI
        this.initComponents();		
	this.initEvents();
        // Use Look & Feel System
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    SwingUtilities.updateComponentTreeUI(this);
	} 
        catch (InstantiationException e) {} 
        catch (ClassNotFoundException e) {}
        catch (UnsupportedLookAndFeelException e) {} 
        catch (IllegalAccessException e) {}
        
        this.initControllers();
        this.pack();
	this.setVisible(true);
    }   
    
    public void initControllers(){
        // init product controller
        ProductController prod_controller = new ProductController(null);
        ProductRecords prod_records = (ProductRecords) prod_controller.read_data(products_file); 
        prod_controller.setModel(prod_records);
        
        // init stock controller 
        StockController stock_controller = new StockController(null);
        StockRecords stock_records = (StockRecords) stock_controller.read_data(stocks_file);
        stock_controller.setModel(stock_records);
        
        this.controllers.put("prod_controller", prod_controller);
        this.controllers.put("prod_controller", stock_controller);
    }
    
    public void initComponents(){
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        tf_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        sep_V = new javax.swing.JSeparator();
        sep_H = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tab_products = new javax.swing.JTable();
        

        btn_add.setText("Add");
        btn_add.setToolTipText("Add a product");

        btn_edit.setText("Edit");
        btn_edit.setToolTipText("Edit a product");
        btn_edit.setEnabled(false);

        btn_delete.setText("Delete");
        btn_delete.setToolTipText("Delete a product");
        btn_delete.setEnabled(false);

        tf_search.setToolTipText("Enter a keyword for search...");
        tf_search.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_search.setText("Search");
        btn_search.setToolTipText("Search a product");

        sep_V.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tab_products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "Shoe14", "150", "Women shoes", "45", null},
                {null, "Pant75", "20", "Men pant", "12", null},
                {null, "Sock12", "45", "winter sock", "32", null},
                {null, "Glasses78", "25", "summer glasses", "15", null}
            },
            new String [] {
                "#", "Code", "Price", "Description", "Stock", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tab_products.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tab_products);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sep_H)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(sep_V, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_add)
                        .addComponent(btn_edit)
                        .addComponent(btn_delete)
                        .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_search))
                    .addComponent(sep_V, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep_H, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");
        //pack();
    }
    
    public void initEvents(){
        
    }

    @Override
    public void update(Observable observ) {
        System.out.println("StockGUI >> update from Observable");
        
    }
    
}
