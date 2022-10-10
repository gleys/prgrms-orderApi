package com.github.prgrms.orders;

import java.util.Optional;

public interface OrderRepository {

  Optional<Order> findById(Long id);
  void update(Order order);

}
