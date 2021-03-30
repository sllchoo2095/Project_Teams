package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


//Stack class is with the 
public class Stack<T> {
    private List<T> dataCollection;
    public Stack() {
        dataCollection = new LinkedList<>();
    }
    public void push(T item) {
        dataCollection.add(dataCollection.size(), item);
    }
   public T pop() {
        if(dataCollection.size() > 0) {
        	
            return dataCollection.remove(dataCollection.size()-1);
        }
        
        else {
            return null;
        }
          
    }
    public int size()
    {
    	    return dataCollection.size();
    }
   public void clear() {
        dataCollection.clear();
    }
	

}
