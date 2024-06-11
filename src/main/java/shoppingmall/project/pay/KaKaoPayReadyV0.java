package shoppingmall.project.pay;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class KaKaoPayReadyV0 {
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // 받는 결제 페이지
    private Date created_at; // 결제 준비 요청 시간

}
