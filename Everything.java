package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test all methods
        Warehouse warehouse = new Warehouse();
        int every = StdIn.readInt();
        int count = 0;

        while(StdIn.hasNextChar() && count < every){
            String read = StdIn.readString();
            
            if (read.equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                warehouse.addProduct(id, name, stock, day, demand);
            } 

            else if (read.equals("purchase")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                warehouse.purchaseProduct(id, day, amount);
            }

            else if (read.equals("restock")){
                warehouse.restockProduct(StdIn.readInt(), StdIn.readInt());
            }
            else {
                warehouse.deleteProduct(StdIn.readInt());
            }
            count++;
        }
        StdOut.println(warehouse);
    }
}
