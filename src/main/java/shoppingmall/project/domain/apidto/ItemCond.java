package shoppingmall.project.domain.apidto;

import lombok.Data;

/**
 * 조건을 가지고 아이템을 찾을 item의 condition객체
 */
@Data
public class ItemCond {
    private int min;
    private int max;
    private String dtype;

    public ItemCond() {
    }

    public ItemCond(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public ItemCond(int min, int max, String dtype) {
        this.min = min;
        this.max = max;
        this.dtype = dtype;
    }
}
