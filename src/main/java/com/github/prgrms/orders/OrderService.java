package com.github.prgrms.orders;

import com.github.prgrms.configures.web.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.github.prgrms.orders.OrderState.*;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll(Long userSeq, Pageable pageable) {
        long offset = pageable.getOffset();
        int size = pageable.getSize();

        List<OrderResponseDTO> result = orderRepository.findAll(userSeq, offset, size);
        return result;
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        return orderRepository.findWithReviewById(id);
    }

    public Boolean accept(Long orderSeq) {
        Order findOrder = orderRepository.findById(orderSeq).get();
        OrderState state = findOrder.getOrderState();

        if (state.equals(REQUESTED)) {
            findOrder.changeOrderState(ACCEPTED);
            orderRepository.update(findOrder);
            return true;
        }
        return false;
    }

    public Boolean reject(Long orderSeq, String rejectMessage) {
        Order findOrder = orderRepository.findById(orderSeq).get();
        OrderState state = findOrder.getState();

        if (state.equals(REQUESTED)) {
            findOrder.changeOrderState(REJECTED);
            findOrder.setRejectMsg(rejectMessage);
            findOrder.setRejectedAt(LocalDateTime.now());
            orderRepository.update(findOrder);
            return true;
        }
        return false;
    }

    public Boolean shipping(Long orderSeq) {
        Order findOrder = orderRepository.findById(orderSeq).get();
        OrderState state = findOrder.getState();

        if (state.equals(ACCEPTED)) {
            findOrder.changeOrderState(SHIPPING);
            orderRepository.update(findOrder);
            return true;
        }
        return false;
    }

    public Boolean complete(Long orderSeq) {
        Order findOrder = orderRepository.findById(orderSeq).get();
        OrderState state = findOrder.getState();

        if (state.equals(SHIPPING)) {
            findOrder.changeOrderState(COMPLETED);
            findOrder.setCompletedAt(LocalDateTime.now());
            orderRepository.update(findOrder);
            return true;
        }
        return false;
    }
}
