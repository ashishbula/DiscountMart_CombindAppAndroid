package in.discountmart.business.model_business.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class Dy_InvoiceResponse extends BaseResponse{
    @SerializedName("invoice")
    @Expose
    ArrayList<Map<String,String>> invoice;
    @SerializedName("recordcount")
    String recordcount;

    public ArrayList<Map<String, String>> getInvoice() {
        return invoice;
    }

    public void setInvoice(ArrayList<Map<String, String>> invoice) {
        this.invoice = invoice;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}
