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

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
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
public class DialogProduct extends JDialog implements Observable{
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_save;
    private javax.swing.JCheckBox cb_add_stock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lab_delivery;
    private javax.swing.JLabel lab_qty;
    private javax.swing.JTextArea ta_descrip;
    private javax.swing.JTable tab_stocks;
    private javax.swing.JTextField tf_code;
    private javax.swing.JTextField tf_delivery;
    private javax.swing.JTextField tf_price;
    private javax.swing.JTextField tf_qty;
    // End of variables declaration  
    
    private StockGUI stockGUI;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private String parent_action;

    public DialogProduct() {
    }
    
    public DialogProduct(StockGUI parent, boolean modal, String action) {
        super(parent, modal);
        this.stockGUI = parent;
        this.parent_action = action;
        this.addObserver(parent);
        
        this.initComponents();
        this.initEvents();
        this.initActions();
        this.setLocationRelativeTo(null);
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
	} 
        catch (InstantiationException e) {} 
        catch (ClassNotFoundException e) {}
        catch (UnsupportedLookAndFeelException e) {} 
        catch (IllegalAccessException e) {}
        this.setVisible(true);
        
    }
    
    public void initComponents(){
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_code = new javax.swing.JTextField();
        tf_price = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_descrip = new javax.swing.JTextArea();
        cb_add_stock = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        lab_delivery = new javax.swing.JLabel();
        tf_delivery = new javax.swing.JTextField();
        lab_qty = new javax.swing.JLabel();
        tf_qty = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tab_stocks = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        btn_save = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("OOP - Add/Edit Product");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Detail"));

        jLabel1.setText("Code");
        jLabel2.setText("Price");
        jLabel3.setText("Description");
        ta_descrip.setColumns(20);
        ta_descrip.setRows(5);
        jScrollPane1.setViewportView(ta_descrip);

        cb_add_stock.setText("Add stock");

        lab_delivery.setText("Delivery");
        lab_delivery.setEnabled(false);
        tf_delivery.setEditable(false);

        lab_qty.setText("Quantity");
        lab_qty.setEnabled(false);
        
        tf_qty.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_code, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tf_price, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_add_stock)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lab_qty)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(tf_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lab_delivery)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tf_delivery, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_add_stock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_delivery)
                    .addComponent(tf_delivery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_qty)
                    .addComponent(tf_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab_stocks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Product Code", "Delivery", "Quantity"
            }
        ));
        jScrollPane2.setViewportView(tab_stocks);

        btn_save.setText("Save");
        btn_cancel.setText("Cancel");
        btn_reset.setText("Reset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save)
                    .addComponent(btn_cancel)
                    .addComponent(btn_reset))
                .addContainerGap())
        );
        pack();
    }
    
    public void initEvents(){
        this.btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btn_saveActionPerformed(evt);
            }
        });
        
        this.btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btn_resetActionPerformed(evt);
            }
        });
        
        this.btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	btn_cancelActionPerformed(evt);
            }
        });
        
        this.cb_add_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	cb_add_stockActionPerformed(evt);
            }
        });
    }
    
    public void initActions(){
        if (this.parent_action.equals("edit") || this.parent_action.equals("Add stock")
                || this.parent_action.equals("View"))
        {
            int row_target = 0;            
            if (this.parent_action.equals("edit")){
                int rows = stockGUI.getTab_products().getRowCount();            
                for (int i=0; i<rows; i++)
                {
                    boolean is_checked = (boolean) stockGUI.getTab_products().getValueAt(i, 0);
                    if (is_checked){
                        row_target = i;
                        break;
                    }
                }
            }
            else if (this.parent_action.equals("Add stock") || this.parent_action.equals("View")){
                row_target = stockGUI.getTab_products().getSelectedRow();
                
                if (this.parent_action.equals("Add stock"))
                {
                    this.cb_add_stock.setSelected(true);
                    this.lab_delivery.setEnabled(true);
                    this.tf_delivery.setEnabled(true);

                    String dev_code = "dev"+Instant.now().toEpochMilli();
                    this.tf_delivery.setText(dev_code);

                    this.lab_qty.setEnabled(true);
                    this.tf_qty.setEnabled(true);
                    this.tf_qty.setEditable(true);
                }                
            }            
            
            //if (row_target == 0)
                    
            String prod_code = stockGUI.getTab_products().getValueAt(row_target, 1).toString();
            
            ProductController prod_controller = 
                (ProductController) this.stockGUI.getControllers().get("prod_controller");
            ProductRecords prod_records = (ProductRecords) prod_controller.getModel();
            HashMap<String, Product> products = prod_records.getProducts();
            
            Product prod = products.get(prod_code);
            this.tf_code.setText(prod_code);
            this.tf_price.setText(Float.toString(prod.getPrice()));
            this.ta_descrip.setText(prod.getDescription());
            
            StockController stock_controller = 
                (StockController) this.stockGUI.getControllers().get("stock_controller");
            StockRecords stock_records = (StockRecords)stock_controller.getModel();
            HashMap<String,HashMap<String,Stock>> stocks = stock_records.getStocks();
            
            if (stocks.containsKey(prod.getCode()))
            {
                HashMap<String, Stock> stock_by_deliv = stocks.get(prod.getCode());
            
                DefaultTableModel model = (DefaultTableModel) this.tab_stocks.getModel();

                for (Map.Entry<String, Stock> sub_entry: stock_by_deliv.entrySet())
                {
                    Stock cur_stock = sub_entry.getValue();
                    model.addRow(new Object[] {cur_stock.getProd_code(), 
                        cur_stock.getDelivery(), cur_stock.getQuantity()});
                }
            }
        }
    }
    
    public void btn_saveActionPerformed(java.awt.event.ActionEvent evt){
        String prod_code = this.tf_code.getText();
        float price = Float.parseFloat(this.tf_price.getText());
        String descrip = this.ta_descrip.getText();
        
        Product prod = new Product(prod_code, price, descrip);
        
        String delivery = "NONE";
        int quantity = 0;        
        if (this.cb_add_stock.isSelected()){
            delivery = this.tf_delivery.getText();
            quantity = Integer.parseInt(this.tf_qty.getText());
        }        
        
        Stock stock = new Stock(delivery, prod_code, null, quantity);
        
        ProductController prod_controller = 
                (ProductController) this.stockGUI.getControllers().get("prod_controller");
        ProductRecords prod_records = (ProductRecords) prod_controller.getModel();
        HashMap<String, Product> products = prod_records.getProducts();      
        
        if (products.containsKey(prod.getCode())){
            JOptionPane.showMessageDialog(this, 
                "Product updated",
                "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        }
        products.put(prod.getCode(), prod);
        prod_records.setProducts(products);

        prod_controller.setModel(prod_records);
        prod_controller.save_data(this.stockGUI.PRODUCTS_FILE);
        prod_records.notifyObservers();
        

        StockController stock_controller = 
                (StockController) this.stockGUI.getControllers().get("stock_controller");
        StockRecords stock_records = (StockRecords)stock_controller.getModel();
        HashMap<String,HashMap<String,Stock>> stocks = stock_records.getStocks();
        
        boolean update_dialog = false;
        if (stocks.containsKey(prod.getCode()))
        {
            HashMap<String, Stock> stock_by_deliv = stocks.get(prod.getCode());
            
            if(stock_by_deliv.containsKey(stock.getDelivery())){
                String error_msg = "The stock delivered already exists !";
                JOptionPane.showMessageDialog(this, 
                    error_msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if(!stock_by_deliv.containsKey(stock.getDelivery()) &&
                    stock_by_deliv.containsKey("NONE")){
                stock_by_deliv.remove("NONE");
                stock_by_deliv.put(stock.getDelivery(), stock);
                update_dialog = true;
            }
            else if (!stock_by_deliv.containsKey(stock.getDelivery()) && 
                    !stock.getDelivery().equals("NONE")){
                stock_by_deliv.put(stock.getDelivery(), stock);
                update_dialog = true;
            }
            stocks.put(prod.getCode(), stock_by_deliv);
        }
        else{
            HashMap<String, Stock> stock_by_deliv = new HashMap<String, Stock>();
            stock_by_deliv.put(stock.getDelivery(), stock);
            stocks.put(prod.getCode(), stock_by_deliv);
            update_dialog = true;
        }
        
        if(update_dialog)
        {
            HashMap<String, Stock> stock_by_deliv = stocks.get(prod.getCode());
            
            DefaultTableModel model = (DefaultTableModel) this.tab_stocks.getModel();          
            
            int rows = this.tab_stocks.getRowCount();
            for (int i=rows-1; i>=0; i--)
                model.removeRow(i);
            
            for (Map.Entry<String, Stock> sub_entry: stock_by_deliv.entrySet())
            {
                Stock cur_stock = sub_entry.getValue();
                model.addRow(new Object[] {cur_stock.getProd_code(), 
                    cur_stock.getDelivery(), cur_stock.getQuantity()});
            }   
        }     
        
        // notify parent (stockGUI) of the model updated
        stock_records.setStocks(stocks);
        stock_controller.setModel(stock_records);
        stock_controller.save_data(this.stockGUI.STOCKS_FILE);
        stock_records.notifyObservers();
    }
    
    public void btn_resetActionPerformed(java.awt.event.ActionEvent evt){
        this.tf_code.setText("");
        this.tf_price.setText("");
        this.ta_descrip.setText("");
        this.cb_add_stock.setSelected(false);
        this.tf_delivery.setText("");
        this.tf_qty.setText("");
        
        DefaultTableModel model = (DefaultTableModel) this.tab_stocks.getModel();  
        // remove all items in the table
        int rows = this.tab_stocks.getRowCount();
        for (int i=rows-1; i>=0; i--)
            model.removeRow(i);
    }
    
    public void btn_cancelActionPerformed(java.awt.event.ActionEvent evt){
        this.setVisible(false);
    }
    
    public void cb_add_stockActionPerformed(java.awt.event.ActionEvent evt){
        boolean is_checked = ((JCheckBox)evt.getSource()).isSelected();
        if (is_checked){
            this.lab_delivery.setEnabled(true);
            this.tf_delivery.setEnabled(true);
            
            Instant instant = Instant.now();
            //System.out.println("instant: "+instant.toEpochMilli());
            String dev_code = "dev"+instant.toEpochMilli();
            this.tf_delivery.setText(dev_code);
            
            this.lab_qty.setEnabled(true);
            this.tf_qty.setEnabled(true);
            this.tf_qty.setEditable(true);
        }
        else{
            this.lab_delivery.setEnabled(false);
            this.tf_delivery.setEnabled(false);
            this.tf_delivery.setText("");
            
            this.lab_qty.setEnabled(false);
            this.tf_qty.setEnabled(false);
            this.tf_qty.setEditable(false);
            this.tf_qty.setText("");
        }
    }    

    @Override
    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    @Override
    public void removeObservers() {
        this.observers = new ArrayList<Observer>();
    }

    @Override
    public void notifyObservers() {
        for(Observer obs : this.observers )
            obs.update(this);
    }
}
