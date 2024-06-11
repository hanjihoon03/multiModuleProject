package shoppingmall.project.additional.log.trace;

import java.util.UUID;

/**
 * 트랜잭션 아이디와 깊이를 알 수 있게 하기 위한 클래스
 */
public class TraceId {
    private String id;
    private int level;
    public TraceId() {
        this.id = createId();
        this.level = 0;
    }
    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }
    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }
    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }
    public boolean isFirstLevel() {
        return level == 0;
    }
    public String getId() {
        return id;
    }
    public int getLevel() {
        return level;
    }
}
