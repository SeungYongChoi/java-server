/**
 * 20220-05-18
 * 최승용
 *
 */

package capston.noodles.Follow.controller;

import capston.noodles.Follow.model.dto.GetFollowResponse;
import capston.noodles.Follow.service.FollowService;
import capston.noodles.common.response.ResponseMessage;
import capston.noodles.common.response.ResponseSuccessMessage;
import capston.noodles.users.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final JwtProvider jwtProvider;
    private final FollowService followService;

    /**
     * @param toUserIdx : 팔로잉 대상 유저 idx
     * @param request   : request 헤더의 토큰을 통해 팔로우를 신청하는 유저의 idx를 뽑아냄
     * @return api요청 성공시 ResponseSuccessMessage(코드:200, msg: 팔로우 추가 or 삭제 api 요청 성공)
     * api 실패시 에러보냄
     */
    @PostMapping("/following/{toUserIdx}")
    public ResponseMessage changeFollowStatus(@PathVariable("toUserIdx") Long toUserIdx, HttpServletRequest request) {
        String token = request.getHeader("x-auth-token");
        String fromUserIdx = jwtProvider.getUserPk(token);

        followService.changeFollowStatus(toUserIdx, Long.parseLong(fromUserIdx));

        return new ResponseMessage(new ResponseSuccessMessage(200, "팔로우 추가 or 삭제 api 요청 성공"));
    }


    /**
     * @param 팔로우 목록을 볼 상대의 userIdx를 쿼리파라미터로 받는다.
     * @return uesrIdx를 가진 유저 팔로우 목록의 닉네임과 프로필 사진을 list로 리턴한다.
     */
    @GetMapping("/follower/{userIdx}")
    public ResponseMessage<List<GetFollowResponse>> getFollowerForOthers(@PathVariable("userIdx") Long targetIdx, HttpServletRequest request) {
        Long myIdx = jwtProvider.getUserPk(request);
        return new ResponseMessage(followService.getFollower(targetIdx, myIdx));
    }

    /**
     * 쿼리 파라미터로 아무것도 받지 않는다면 현재 로그인한 유저의 팔로우 목록을 가져온다.
     *
     * @return uesrIdx를 가진 유저 팔로우 목록의 닉네임과 프로필 사진을 list로 리턴한다.
     */
    @GetMapping("/follower")
    public ResponseMessage<List<GetFollowResponse>> getFollowerForMe(HttpServletRequest request) {
        Long myIdx = jwtProvider.getUserPk(request);
        // 조회 대상: 자기 자신, 조회 하는 사람 : 자기 자신
        return new ResponseMessage(followService.getFollower(myIdx, myIdx));
    }


    @GetMapping("/following/{userIdx}")
    public ResponseMessage<List<GetFollowResponse>> getFollowingForOthers(@PathVariable("userIdx") Long targetIdx, HttpServletRequest request) {
        Long myIdx = jwtProvider.getUserPk(request);
        return new ResponseMessage(followService.getFollowing(targetIdx, myIdx));
    }

    @GetMapping("/following")
    public ResponseMessage<List<GetFollowResponse>> getFollowingForMe(HttpServletRequest request) {
        Long myIdx = jwtProvider.getUserPk(request);
        return new ResponseMessage(followService.getFollowing(myIdx, myIdx));
    }
}
