package com.github.prgrms.orders;

import com.github.prgrms.security.JwtAuthentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.github.prgrms.utils.ApiUtils.ApiResult;
import static com.github.prgrms.utils.ApiUtils.success;


@RestController
@RequestMapping("api/orders")
public class ReviewRestController {
  // TODO review 메소드 구현이 필요합니다.

  private final ReviewService reviewService;

  public ReviewRestController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping(path = "/{id}/review")
  public ApiResult<ReviewResponseDTO> review(
          @AuthenticationPrincipal JwtAuthentication authentication,
          @PathVariable Long id,
          @RequestBody ReviewRequestDTO request
  ) {
    return success(new ReviewResponseDTO(reviewService.review(id, request)));
  }

}