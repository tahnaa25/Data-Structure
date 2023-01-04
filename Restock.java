package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test restock
        Warehouse warehouse = new Warehouse();
        int restock = StdIn.readInt();
        int count = 0;

        while(StdIn.hasNextChar() && count < restock){
            
            if (StdIn.readString().contains("add")){ 
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                warehouse.addProduct(id, name, stock, day, demand);
                count++;
            } 
            else {
                warehouse.restockProduct(StdIn.readInt(), StdIn.readInt());
                count++;
            }
        }
        StdOut.println(warehouse);

    }
}
