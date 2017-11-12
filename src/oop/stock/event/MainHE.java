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
package oop.stock.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import oop.stock.controller.ProductController;
import oop.stock.controller.StockController;
import oop.stock.model.Product;
import oop.stock.model.ProductRecords;
import oop.stock.model.Stock;
import oop.stock.model.StockRecords;
import oop.stock.view.DialogProduct;
import oop.stock.view.StockGUI;
import oop.stock.view.TableComponent;

/**
 *
 * @author romuald.fotso
 */
public class MainHE extends AbstractHandleEvent{
    
    private ProductController prod_controller;
    private StockController stock_controller;    

    public MainHE(StockGUI stockGUI) {
        super(stockGUI);
        this.handleMainEvent();
        this.prod_controller = (ProductController) this.stockGUI.getControllers().get("prod_controller");
        this.stock_controller = (StockController) this.stockGUI.getControllers().get("stock_controller");
    }
    
    public void handleMainEvent(){        
        // add button
        this.stockGUI.getBtn_add().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    System.out.println("MainHE > add action");                    
                    stockGUI.setDialog(new DialogProduct(stockGUI, true, "add"));
                }
            }
        );
        
        // edit button
        this.stockGUI.getBtn_edit().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    btn_editActionPerformed(evt);                    
                }
            }
        );
        
        // delete button
        this.stockGUI.getBtn_delete().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    System.out.println("MainHE > delete action");
                }
            }
        );
        
        // search button
        this.stockGUI.getBtn_search().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    btn_searchActionPerformed(evt);
                }
            }
        );
        
        // reset button
        this.stockGUI.getBtn_reset().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    btn_resetActionPerformed(evt);                  
                }
            }
        );
        
        // change listener on table
        this.stockGUI.getTab_products().getModel().addTableModelListener(
            new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    tab_productsTableChanged(e);                    
                }        
            }
        );
        
        // selection listener on table
        this.stockGUI.getTab_products().getSelectionModel()
            .addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row_id = stockGUI.getTab_products().getSelectedRow();

//                    System.out.println("MainHE > row sel: "+row_id+
//                    ", checkbox val: "+stockGUI.getTab_products().getValueAt(row_id, 0));                    
            }
        });
    }
    
    public void btn_resetActionPerformed(ActionEvent evt){
        System.out.println("MainHE > reset action");
        
        this.stockGUI.getTf_search().setText("");
        this.stockGUI.getBtn_reset().setEnabled(false);
        this.stockGUI.getBtn_edit().setEnabled(false);
        this.stockGUI.getBtn_delete().setEnabled(false);
        
        ProductRecords prod_records = (ProductRecords) this.prod_controller.getModel();        
        StockRecords stock_records = (StockRecords) this.stock_controller.getModel();
        
        prod_records.notifyObservers();
        stock_records.notifyObservers();
    }
    
    public void btn_searchActionPerformed(ActionEvent evt){
        System.out.println("MainHE > search action");
        this.stockGUI.getBtn_reset().setEnabled(true);
        
        String keyword = this.stockGUI.getTf_search().getText();
        
        ProductRecords prod_records = (ProductRecords) this.prod_controller.getModel();
        HashMap<String, Product> products = prod_records.getProducts();
        
        StockRecords stock_records = (StockRecords) this.stock_controller.getModel();
        HashMap<String,HashMap<String,Stock>> stocks = stock_records.getStocks();
        
        Product prod = null;
        // search if keyword match with product code
        if(products.containsKey(keyword)) 
            prod = products.get(keyword);
        
        if (prod == null)
        {
            // search if keyword match with partial product code or description
            for (Map.Entry<String, Product> entry: products.entrySet())
            {
                String code = entry.getKey();
                Product product = entry.getValue();

                if (product.getCode().startsWith(keyword)
                    || product.getCode().contains(keyword)
                    ){
                    prod = product;
                    break;
                }
                else if (product.getDescription().startsWith(keyword)
                    || product.getDescription().contains(keyword)
                    ){
                    prod = product;
                    break;
                }
            }
        }        
        
        if (prod == null)
        {
            String error_msg = "Sorry, no product matches with keyword";
            JOptionPane.showMessageDialog(null, 
                error_msg, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else{
            DefaultTableModel model = (DefaultTableModel) this.stockGUI.getTab_products().getModel();
            int rows = this.stockGUI.getTab_products().getRowCount();
            //System.out.println("StockGUI >> rows: "+rows); 
            for (int i=rows-1; i>=0; i--)
                model.removeRow(i);
            
            int prod_qty = 0;
            HashMap<String, Stock> stock_by_deliv = stocks.get(prod.getCode());

            for (Map.Entry<String, Stock> sub_entry: stock_by_deliv.entrySet())
            {
                String delivery = sub_entry.getKey();
                Stock stock = sub_entry.getValue();
                prod_qty += stock.getQuantity();
            }
            
            model.addRow(new Object[] {new Boolean(false), prod.getCode(), prod.getPrice(),
                                           prod.getDescription(), prod_qty, this.stockGUI.getComboData()[0]});
            
            this.stockGUI.getTab_products().getColumn("Actions")
                    .setCellEditor(new DefaultCellEditor(new JComboBox(this.stockGUI.getComboData())));
            this.stockGUI.getTab_products().setDefaultRenderer(JComponent.class, new TableComponent());
        }
    }
    
    public void btn_editActionPerformed(ActionEvent evt){
        System.out.println("MainHE > edit action");
                    
        int rows = stockGUI.getTab_products().getRowCount();
        int rows_checked = 0;
        for (int i=0; i<rows; i++)
        {
            boolean is_checked = (boolean) stockGUI.getTab_products().getValueAt(i, 0);
            if (is_checked)rows_checked++;
        }

        if (rows_checked == 1)
            stockGUI.setDialog(new DialogProduct(stockGUI, true, "edit"));
        else {
            String error_msg = "Please, select only one item for editing";
            JOptionPane.showMessageDialog(null, 
                error_msg, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void tab_productsTableChanged(TableModelEvent e) {
        System.out.println("MainHE > tableChanged ");
        boolean is_box_checked = false;
        int row_id = stockGUI.getTab_products().getSelectedRow();

        if(e.getType() == e.UPDATE && row_id != -1){

            System.out.println("MainHE > row sel: "+row_id);
            System.out.println("MainHE > checkbox val: "+stockGUI.getTab_products().getValueAt(row_id, 0));

            int rows = stockGUI.getTab_products().getRowCount();                       
            for (int i=0; i<rows; i++)
            {
                boolean is_checked = (boolean) stockGUI.getTab_products().getValueAt(i, 0);
                if (is_checked) is_box_checked = true;

            }

            String action = (String) stockGUI.getTab_products().getValueAt(row_id, 5);
            System.out.println("MainHE > action sel: "+action);

            if(action == "View")
            {
                prod_controller.getModel().notifyObservers();
                stock_controller.getModel().notifyObservers();
            }
            else if(action == "Add stock"){

            }
            else if(action == "Buy"){

            }
        }

        System.out.println("MainHE > is_box_checked: "+is_box_checked);
        if (is_box_checked){                        
            stockGUI.getBtn_edit().setEnabled(true);
            stockGUI.getBtn_delete().setEnabled(true);
        }
        else{
            stockGUI.getBtn_edit().setEnabled(false);
            stockGUI.getBtn_delete().setEnabled(false);
        }
    }    
}
