package com.github.prgrms.orders;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.security.JwtAuthentication;

import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.prgrms.utils.ApiUtils.success;
import static com.github.prgrms.utils.ApiUtils.ApiResult;


@RestController
@RequestMapping("api/orders")
public class OrderRestController {
  // TODO findAll, findById, accept, reject, shipping, complete 메소드 구현이 필요합니다.

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PatchMapping(path = "{id}/accept")
    public ApiResult<Boolean> accept(@PathVariable Long id) {
        return success(orderService.accept(id));
    }


    @PatchMapping(path = "{id}/shipping")
    public ApiResult<Boolean> shipping(@PathVariable Long id) {
        return success(orderService.shipping(id));
    }

    @PatchMapping(path = "{id}/complete")
    public ApiResult<Boolean> complete(@PathVariable Long id) {
        return success(orderService.complete(id));
    }

    @PatchMapping(path = "{id}/reject")
    public ApiResult<Boolean> reject(@PathVariable Long id,  @RequestBody(required = false) OrderRejectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException();
        }
        return success(orderService.reject(id, request.getMessage()));
    }

    @GetMapping(path = "/{id}")
    public ApiResult<OrderResponseDTO> findById(@PathVariable Long id) {
        return success(orderService.findById(id));
    }

    @GetMapping
    public ApiResult<List<OrderResponseDTO>> findAll(
            @AuthenticationPrincipal JwtAuthentication authentication,
            Pageable pageable
    ) {
        Long userSeq = authentication.id;
        List<OrderResponseDTO> orders = orderService.findAll(userSeq, pageable);

        return success(orders);
    }


}