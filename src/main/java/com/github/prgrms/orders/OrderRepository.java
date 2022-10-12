package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

  Optional<Order> findById(Long id);
  List<OrderResponseDTO> findAll(Long userSeq, long offset, int size);
  OrderResponseDTO findWithReviewById(Long orderSeq);
  void update(Order order);

}
