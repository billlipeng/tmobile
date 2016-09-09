package tmobile;

import java.util.Objects;

/**
 * Created by zero on 9/8/16.
 */
public class Name{
    private Integer id;
    private Integer amount;
    private String name;
    private Integer sum;



    public void setName(Integer amount, String name, Integer sum){
        this.amount = amount;
        this.name = name;
        this.sum = sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
