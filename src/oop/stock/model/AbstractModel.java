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
package oop.stock.model;

import java.util.ArrayList;
import oop.stock.observer.Observable;
import oop.stock.observer.Observer;

/**
 *
 * @author romuald.fotso
 */
public abstract class AbstractModel implements Observable{
    
    private  ArrayList<Observer> l_observers = new ArrayList<Observer>();
    
    @Override
    public void addObserver(Observer obs)
    {
        this.l_observers.add(obs);
    }
	
    @Override
    public void removeObservers()
    {
        this.l_observers = new ArrayList<Observer>();
    }

    @Override
    public void notifyObservers()
    {
        for(Observer obs : this.l_observers)
            obs.update(this);
    }
}
