package nsimhie.priceperusage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by nsimhie on 2015-10-06.
 */
public class Usage
{
    private String name;
    private double price;
    private int uses;

    Usage(String name, double price, int uses)
    {
        this.name = name;
        this.price = price;
        this.uses = uses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getUses() {
        return uses;
    }

    public double getPPU(){
        return Double.parseDouble(String.format("%.2f",price/((double) uses)));
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\nPrice: " + getPrice() + "\nUses: " + getUses() + "\nPPU: " + getPPU();
    }

    public JSONObject toJson()
    {

        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("name", getName());
            jsonObject.put("price", getPrice());
            jsonObject.put("uses", getUses());
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

