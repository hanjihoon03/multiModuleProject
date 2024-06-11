package shoppingmall.project.pay;

import lombok.Data;
import shoppingmall.project.pay.response.AmountV0;
import shoppingmall.project.pay.response.CardV0;

import java.util.Date;

@Data
public class KakaoPayApprovalV0 {

    //response
    private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private AmountV0 amount;
    private CardV0 card_info;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private Date created_at, approved_at;

}
