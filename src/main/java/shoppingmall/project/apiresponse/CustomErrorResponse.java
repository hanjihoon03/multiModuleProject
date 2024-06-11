package shoppingmall.project.apiresponse;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private String connection;
    private String contentLength;
    private String date;
}
