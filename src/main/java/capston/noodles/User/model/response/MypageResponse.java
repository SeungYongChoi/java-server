package capston.noodles.User.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MypageResponse {
    private Long userIdx;
    private String profileImage;
    private String nickname;
    private String identification;
    private String description;
    private int following;
    private int follower;
    private String postIdxList;
    private String imageList;
    private Integer isFollowed;
}
