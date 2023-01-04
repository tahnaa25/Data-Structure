package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

	// Use this file to test purchaseProduct
        Warehouse warehouse = new Warehouse();
        int purchase = StdIn.readInt();
        int count = 0;
        while(StdIn.hasNextChar() && count < purchase){

            if (StdIn.readString().equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                warehouse.addProduct(id, name, stock, day, demand);
            } 
            else{
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                warehouse.purchaseProduct(id, day, amount);
            }
            count++;
        }
        StdOut.println(warehouse);
    }
}
