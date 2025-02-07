// används för att representera en sko i programmet

class ShoeInformation {


    int id;  //Unikt ID för varje sko för att sedan kunna identifiera den i databasen.
    String brand;
    String colour;
    int size;
    int price;

    @Override
    public String toString() {
        return id + ". " + brand + " - " + colour + " - Storlek: " + size + " - Pris: " + price + " kr";
    }
}