package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
	    
        // Use this file to test addProduct
        Warehouse warehouse = new Warehouse();
        int addprod = StdIn.readInt();
        int count = 0;

        while(StdIn.hasNextChar() &&  count < addprod){
            int day =StdIn.readInt();
            int id =StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            warehouse.addProduct(id, name, stock, day, demand);
            count ++;
        }
        StdOut.println(warehouse);    
    } 
}


