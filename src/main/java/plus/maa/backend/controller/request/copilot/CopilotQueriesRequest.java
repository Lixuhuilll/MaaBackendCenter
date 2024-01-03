package plus.maa.backend.controller.request.copilot;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LoMu
 * Date  2022-12-26 2:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopilotQueriesRequest {
    private int page = 0;
    @Max(value = 50, message = "单页大小不得超过50")
    private int limit = 10;
    private String levelKeyword;
    private String operator;
    private String content;
    private String document;
    private String uploaderId;
    private boolean desc = true;
    private String orderBy;
    private String language;
}
