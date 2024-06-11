package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.*;
import shoppingmall.project.domain.item.Item;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UploadFile {
    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Lob
    private String uploadFileName;//업로드한 파일명
    @Lob
    private String storeFileName; //uuid등 안 겹치게 만들어서 사용
    //서버 내부에서 관리하는 파일명
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public UploadFile(String uploadFileName, String storeFileName, Item item) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.item = item;
    }

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public void updateUploadFile(String uploadFileName,String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
