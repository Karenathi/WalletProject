package model;

import java.util.Objects;

public class Payment {
    private String id;
    private String paymentMode;
    private String paymentStatus;

    //Constructor
    public Payment(String id, String payment_mode, String payment_status) {
        this.id = id;
        this.paymentMode = payment_mode;
        this.paymentStatus = payment_status;
    }

    //Getter
    public String getId() {
        return id;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    //Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setPayment_mode(String paymentMode) {
        if(paymentMode.equalsIgnoreCase("cash") || paymentMode.equalsIgnoreCase("debit card") || paymentMode.equalsIgnoreCase("tranfer") ||paymentMode.equalsIgnoreCase("voucher") ||paymentMode.equalsIgnoreCase("mobile payment")){
            this.paymentMode = paymentMode;
        }
    }

    public void setPayment_status(String paymentStatus) {
        if(paymentStatus.equalsIgnoreCase("bank reconciliation") || paymentStatus.equalsIgnoreCase("cancelled") || paymentStatus.equalsIgnoreCase("validated")) {
            this.paymentStatus = paymentStatus;
        }
    }

    //Equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(getId(), payment.getId()) && Objects.equals(getPaymentMode(), payment.getPaymentMode()) && Objects.equals(getPaymentStatus(), payment.getPaymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPaymentMode(), getPaymentStatus());
    }

    //Tostring
    @Override
    public String toString() {
        return "Payment:" +
                "id='" + id + '\'' +
                ", payment_mode='" + paymentMode + '\'' +
                ", payment_status='" + paymentStatus;
    }
}