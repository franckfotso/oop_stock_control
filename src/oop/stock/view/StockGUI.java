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
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import oop.stock.controller.AbstractController;
import oop.stock.controller.ProductController;
import oop.stock.controller.StockController;
import oop.stock.event.AbstractHandleEvent;
import oop.stock.event.MainHE;
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
    
    public static String PRODUCTS_FILE = "src/oop/stock/data/prod-data.txt";
    public static String STOCKS_FILE = "src/oop/stock/data/stock-data.txt";
    public static String PRICES_FILE = "src/oop/stock/data/prices.txt";
    
    private HashMap<String,AbstractController> controllers;   
    
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_reset;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator sep_H;
    private javax.swing.JSeparator sep_V;
    private javax.swing.JTable tab_products;
    private javax.swing.JTextField tf_search;
    
    private String[] comboData = {"Select", "View", "Add stock", "Buy"};
    private AbstractHandleEvent mainHE;
    private DefaultTableModel init_tab_model;
    private JDialog dialog;

    public StockGUI(HashMap<String, AbstractController> controllers) {
        this.controllers = controllers;        
        
        this.setTitle("OOP - Stock Control");
        this.setMinimumSize(new Dimension(90,64)); // NRel.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // configure GUI
        this.initComponents();		
	
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
        this.initEvents();        
	this.setVisible(true);
    }

    public JDialog getDialog() {
        return dialog;
    }

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }

    public HashMap<String, AbstractController> getControllers() {
        return controllers;
    }

    public void setControllers(HashMap<String, AbstractController> controllers) {
        this.controllers = controllers;
    }

    public JButton getBtn_delete() {
        return btn_delete;
    }

    public void setBtn_delete(JButton btn_delete) {
        this.btn_delete = btn_delete;
    }

    public JButton getBtn_edit() {
        return btn_edit;
    }

    public void setBtn_edit(JButton btn_edit) {
        this.btn_edit = btn_edit;
    }

    public JButton getBtn_search() {
        return btn_search;
    }

    public void setBtn_search(JButton btn_search) {
        this.btn_search = btn_search;
    }

    public JButton getBtn_reset() {
        return btn_reset;
    }

    public void setBtn_reset(JButton btn_reset) {
        this.btn_reset = btn_reset;
    }

    public JTable getTab_products() {
        return tab_products;
    }

    public void setTab_products(JTable tab_products) {
        this.tab_products = tab_products;
    }

    public JButton getBtn_add() {
        return btn_add;
    }

    public void setBtn_add(JButton btn_add) {
        this.btn_add = btn_add;
    }

    public JTextField getTf_search() {
        return tf_search;
    }

    public void setTf_search(JTextField tf_search) {
        this.tf_search = tf_search;
    }

    public String[] getComboData() {
        return comboData;
    }

    public void setComboData(String[] comboData) {
        this.comboData = comboData;
    }
        
    public void initControllers(){
        
        // init product controller
        ProductController prod_controller = new ProductController(null, this);
        prod_controller.read_data(PRODUCTS_FILE);
        
        // init stock controller 
        StockController stock_controller = new StockController(null, this);
        stock_controller.read_data(STOCKS_FILE);
        
        this.controllers.put("prod_controller", prod_controller);
        this.controllers.put("stock_controller", stock_controller);
    }
    
    public void initComponents(){
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        tf_search = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        
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
        
        btn_reset.setText("Reset");
        btn_reset.setToolTipText("");
        btn_reset.setEnabled(false);

        sep_V.setOrientation(javax.swing.SwingConstants.VERTICAL);
        
        init_tab_model = new javax.swing.table.DefaultTableModel(
            new Object [][] { },
            new String [] {
                "#", "Code", "Price", "Description", "Stock", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };

        tab_products.setModel(init_tab_model);
        
        // customize tab size
        TableColumn col;
        for (int i =0; i < tab_products.getColumnCount(); i++)
        {
            col = tab_products.getColumnModel().getColumn(i);            
            switch(i)
            {
                case 0: // box
                    col.setPreferredWidth(5);
                    break;
                case 1: // code
                    col.setPreferredWidth(70);
                    break;
                case 2: // price
                    col.setPreferredWidth(25);
                    break;
                case 3: // description
                    col.setPreferredWidth(170);
                    break;
                case 4: // stock
                    col.setPreferredWidth(25);
                    break;
                default:
                    break;
            }
        }
                        
        tab_products.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tab_products);

        btn_search.setText("Search");
        btn_search.setToolTipText("Search a product");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(sep_V, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_search, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_search)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_reset))
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
                        .addComponent(btn_reset)
                        .addComponent(btn_search))
                    .addComponent(sep_V, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep_H, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");
        pack();
    }
        
    public void initEvents(){
        this.mainHE = new MainHE(this);
    }

    @Override
    public void update(Observable observ) {
                
        if (observ.getClass().isInstance(new ProductRecords()))
        {
            System.out.println("StockGUI >> update changes from ProductRecords");            
            ProductRecords prod_records = (ProductRecords) observ;
            HashMap<String, Product> products = prod_records.getProducts();
            
            DefaultTableModel model = (DefaultTableModel) this.tab_products.getModel();
            int rows = this.tab_products.getRowCount();
            //System.out.println("StockGUI >> rows: "+rows); 
            for (int i=rows-1; i>=0; i--)
                model.removeRow(i);
            
            for (Map.Entry<String, Product> entry: products.entrySet())
            {
                String code = entry.getKey();
                Product product = entry.getValue();
                model.addRow(new Object[] {new Boolean(false), product.getCode(), product.getPrice(),
                                           product.getDescription(), 0, comboData[0]});
            }            
            
            tab_products.getColumn("Actions").setCellEditor(new DefaultCellEditor(new JComboBox(comboData)));
            tab_products.setDefaultRenderer(JComponent.class, new TableComponent());
            
        }
        else if (observ.getClass().isInstance(new StockRecords()))
        {
            System.out.println("StockGUI >> update changes from StockRecords");
            StockRecords stock_records = (StockRecords) observ;
            HashMap<String, HashMap<String, Stock>> stocks = 
                    stock_records.getStocks();
            
            int rows = this.tab_products.getRowCount();
            DefaultTableModel model = (DefaultTableModel) this.tab_products.getModel();
            for (Map.Entry<String, HashMap<String, Stock>> entry: stocks.entrySet())
            {
                int prod_qty = 0;
                String prod_code = entry.getKey();
                HashMap<String, Stock> stock_by_deliv = entry.getValue();
                
                for (Map.Entry<String, Stock> sub_entry: stock_by_deliv.entrySet())
                {
                    String delivery = sub_entry.getKey();
                    Stock stock = sub_entry.getValue();
                    prod_qty += stock.getQuantity();
                }
                
                int target_row = -1;
                for (int i=0; i < rows; i++)
                {
                    if (this.tab_products.getValueAt(i, 1).equals(prod_code))
                    {
                        target_row = i;
                        break;
                    }                        
                }
                
                if (target_row != -1)
                {
                    this.tab_products.setValueAt(prod_qty, target_row, 4);
                }
                else System.out.println("StockGUI >> missing prod_code from stock");                
            }
        }
        else{
            System.out.println("StockGUI >> unknown model");
        }        
    }
    
}
