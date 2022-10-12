package com.github.prgrms.orders;

import com.github.prgrms.security.JwtAuthentication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.github.prgrms.utils.ApiUtils.ApiResult;
import static com.github.prgrms.utils.ApiUtils.success;
import static java.lang.Thread.sleep;


@RestController
@RequestMapping("api/orders")
public class ReviewRestController {
  // TODO review 메소드 구현이 필요합니다.

  private final ReviewService reviewService;
  private final ApplicationContext ac;

  public ReviewRestController(ReviewService reviewService, ApplicationContext ac) {
    this.reviewService = reviewService;
    this.ac = ac;
  }


  @PostMapping(path = "/{id}/review")
  public ApiResult<ReviewResponseDTO> review(
          @AuthenticationPrincipal JwtAuthentication authentication,
          @PathVariable Long id,
          @RequestBody ReviewRequestDTO request
  ) throws InterruptedException {
    return success(new ReviewResponseDTO(reviewService.review(id, request)));
  }

}