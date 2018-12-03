package fr.cesi.pimpmyfridge.controller;

import java.util.Stack;

public class ArrayWorker extends Stack<Double> {
	
	private static final long serialVersionUID = 1825012013828872822L;
	
	private int maxSize;

    public ArrayWorker(int size) {
        super();
        this.maxSize = size;
    }

    
    
    @Override
    public Double push(Double object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }

	public double getLast() {
		
		return 0;
	}

	public double getAverage() {
		
		return 0;
	}
    
}
