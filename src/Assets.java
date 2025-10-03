import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;

public abstract class Assets{
    public BigInteger revenue;
    public BufferedImage icon;
    public BigInteger cost;
    public BigInteger count;
    public Assets(){
        revenue = BigInteger.ZERO;
        icon = null;
        cost = BigInteger.ZERO;
        count = BigInteger.ZERO;
    }
    public Assets(int revenue, int cost, int count, BufferedImage icon){
        this.revenue = BigInteger.valueOf(revenue);
        this.icon = icon;
        this.cost = BigInteger.valueOf(cost);
        this.count = BigInteger.valueOf(count);
    }
    public BigInteger getRevenue(){
        return revenue;
    }
    public void increaseCount(){
        count = count.add(BigInteger.valueOf(1));
    }
    public void decreaseCount(){
        count = count.subtract(BigInteger.valueOf(1));
    }

}