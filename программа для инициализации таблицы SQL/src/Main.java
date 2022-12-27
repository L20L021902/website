import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int idint=22;
        int serialnumberint=3406;
        String idinupt = "1";
        String serialnuminput = "3388";
        String nameinput = "Pizza";
        String typeinput = "food";
        String primecostinput = "700";
        String priceinput = "780";
        String productscol = "1888";

        //for (int i=0;i<188;i++)
        SQLproviding.insertProducts(idinupt, serialnuminput,nameinput,typeinput,primecostinput,priceinput,productscol);
        ResultSet products=SQLproviding.getProducts();

        while (products.next())
        {
            for (int i=1;i<7;i++)
            System.out.println(products.getString(i));
            
        }


    }
    }
