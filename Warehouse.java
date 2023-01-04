package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */

    private void addToEnd(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD

        int getId = id % 10;                                            //since we are adding at the end of the sector and sector has array size of 10
        Product newProduct = new Product(id, name, stock, day, demand); //new Product object
        sectors[getId].add(newProduct);                                 //add the id of the new item at the end of the sector
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */

    private void fixHeap(int id) {
        // IMPLEMENT THIS METHOD

        int getId = id % 10;                  //the id of the product that was just added in the previous method
        Sector fixheap = sectors[getId];

        if (fixheap.getSize() != 1) {         //check to see if the sector has at least 1 item in it
            fixheap.swim(fixheap.getSize());  //in the sector java file the swim method runs- when a node is bigger than the parent node, parent node gets swapped with the child node
        }
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */

    private void evictIfNeeded(int id) {
       // IMPLEMENT THIS METHOD

        int getId = id % 10;                   //delete the last one from the sector
        Sector evict = sectors[getId];

        if (evict.getSize() == 5){             //delete if the size is 5
            evict.swap(1, 5);   //swap the first and the fifth index as the size is within 5
            evict.deleteLast();                //delete and decrement the size
            evict.sink(1);              // sink when a node is smaller than its child node, swap to make sure the bigger node goes to the parent
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        // IMPLEMENT THIS METHOD

        int getId = id % 10;                            //get the id first based on the sector array size
        Sector stock = sectors[getId];                  //get the size of the current sector id

        for (int i = 1; i <= stock.getSize(); i++){     //traverse through the items starting from the stock size to see which items need to be restocked
            if (stock.get(i).getId() == id){            //in the loop, if a current id matches with the id of the method for restock
                stock.get(i).updateStock(amount);       //update the stock
                return;
            }
        }

    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        // IMPLEMENT THIS METHOD

        int getId = id % 10;                                //get the id first based on the sector array size
        Sector deleteprod = sectors[getId]; 

        for (int i = 1; i <= deleteprod.getSize(); i++){    //traverse till the size of the sector

            if (deleteprod.get(i).getId() == id){           //if the id matches
                deleteprod.swap(i, deleteprod.getSize());   //swap first
                deleteprod.deleteLast();                    //then delete the last
                deleteprod.sink(i);                         //sink after

                if (i <= deleteprod.getSize()){             //swim if the size is greater than i
                    deleteprod.swim(i);
                    return;
                }
            }
        }

    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        // IMPLEMENT THIS METHOD

        int getId = id % 10;                                    //get the id first based on the sector array size
        Sector purchase = sectors[getId]; 

        for (int i = 1; i <= purchase.getSize(); i++){          //traverse 
            
            if (purchase.get(i).getId() == id){                 //check if the sector id match
                if (purchase.get(i).getStock() >= amount){      //if the stock is less or equal to the amount that is supposed to be added
                    purchase.get(i).setLastPurchaseDay(day);    //set the last purchase dat first
                    purchase.get(i).updateStock(-amount);       //the stock goes down 
                    purchase.get(i).updateDemand(+amount);      //demand goes up
                    purchase.sink(i);                           //after that sink
                }
            }
        }
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD

        int getId = id % 10;                        //get the id first based on the sector array size
        Sector betterprod = sectors[getId];
        int betterprodsize = betterprod.getSize();  //store the sector arr size
        int count = 1;                              //keep track of the count as it iterates through the sector                      

        if (betterprodsize < 5){
            addProduct(getId, name, stock, day, demand);
        }
        else{                                       //greater than 5 or equal to 5
            while (sectors[getId].getSize() == 5 && count <= 10){  
                id++;
                count++;

                if (sectors[getId].getSize() < 5){   //if sector is not empty means has less then 5 things
                    addProduct(id, name, stock, day, demand);
                    break;                           //break so it does not go through the rest
                }
                getId = id % 10;                     //update to wrap around
            }
            addProduct(id, name, stock, day, demand); 
        }        
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
