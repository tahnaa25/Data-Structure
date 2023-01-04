package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test deleteProduct
        Warehouse warehouse = new Warehouse();
        int delete = StdIn.readInt();
        int count = 0;

        while(StdIn.hasNextChar() && delete > count){

            if (StdIn.readString().equals("add")){ 
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                warehouse.addProduct(id, name, stock, day, demand);
                count++;
            } 
            else {
                warehouse.deleteProduct(StdIn.readInt());
                count++;
            }
        }
        StdOut.println(warehouse);
    }
}
