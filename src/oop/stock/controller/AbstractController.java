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

import oop.stock.model.AbstractModel;

/**
 *
 * @author romuald.fotso
 */
public abstract class AbstractController {
    
    protected AbstractModel model;

    public AbstractController(AbstractModel model) {
        this.model = model;
    }  

    public AbstractModel getModel() {
        return model;
    }

    public void setModel(AbstractModel model) {
        this.model = model;
    }
       
    abstract public int calculate();
    
    abstract public void read_data(String pathname) throws Exception;
    
    abstract public void save_data(String pathname);
}
