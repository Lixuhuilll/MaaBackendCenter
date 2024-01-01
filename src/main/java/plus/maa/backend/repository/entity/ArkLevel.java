package plus.maa.backend.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 地图数据
 *
 * @author john180
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("maa_level")
public class ArkLevel {
    public static final ArkLevel EMPTY = new ArkLevel();

    @Id
    private String id;
    private String levelId;
    @Indexed
    private String stageId;
    //文件版本, 用于判断是否需要更新
    private String sha;
    //地图类型, 例: 主线、活动、危机合约
    private String catOne;
    //所属章节, 例: 怒号光明、寻昼行动
    private String catTwo;
    //地图ID, 例: 7-18、FC-1
    private String catThree;
    //地图名, 例: 冬逝、爱国者之死
    private String name;
    private int width;
    private int height;
    // 只是服务器认为的当前版本地图是否开放
    @Nullable
    private Boolean isOpen;
    // 非实际意义上的活动地图关闭时间，只是服务器认为的关闭时间
    @Nullable
    private LocalDateTime closeTime;
}
