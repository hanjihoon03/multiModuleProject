package shoppingmall.project.pay.response;

import lombok.Data;

@Data
public class AmountV0 {
    private Integer total, tax_free, vat, point, discount;
}
