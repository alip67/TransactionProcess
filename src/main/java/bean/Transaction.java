package bean;

import java.io.Serializable;
import java.math.BigDecimal;


public class Transaction implements Serializable {
    private String id;
    private String type;
    private BigDecimal amount;
    private String depositId;
    private boolean success;
    private BigDecimal newBalance;
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDepositId() {
        return depositId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public String toString() {
        String st = ("Transaction " + id + " , type: " + type + " , amount: " + amount + " , deposit: " + depositId);
        if (newBalance != null && success)
            st += " succeed, new Balance: " + newBalance;
        return st;
    }
}
