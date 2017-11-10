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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import oop.stock.controller.ProductController;
import oop.stock.controller.StockController;
import oop.stock.view.StockGUI;

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
                }
            }
        );
        
        // edit button
        this.stockGUI.getBtn_edit().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    System.out.println("MainHE > edit action");
                }
            }
        );
        
        // edit button
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
                    System.out.println("MainHE > search action");
                }
            }
        );
        
        // reset button
        this.stockGUI.getBtn_reset().addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    System.out.println("MainHE > reset action");
                }
            }
        );
        
        this.stockGUI.getTab_products().getModel().addTableModelListener(
            new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    boolean is_box_checked = false;
                     
                    if(e.getType() == e.UPDATE){
                        int row_id = stockGUI.getTab_products().getSelectedRow();
                        System.out.println("MainHE > row sel: "+row_id+
                        ", checkbox val: "+stockGUI.getTab_products().getValueAt(row_id, 0));
                        
                        int rows = stockGUI.getTab_products().getColumnCount();                       
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
        );
        
        // selection listener table
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
    
}
