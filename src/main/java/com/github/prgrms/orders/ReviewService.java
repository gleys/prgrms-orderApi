package com.github.prgrms.orders;

import com.github.prgrms.errors.UnauthorizedException;
import com.github.prgrms.products.Product;
import com.github.prgrms.products.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@Transactional
public class ReviewService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ReviewService(OrderRepository orderRepository, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public Review review(long orderSeq, ReviewRequestDTO request) {
        String content = request.getContent();

        //주문 존재 여부 확인
        Order findOrder = orderRepository.findById(orderSeq)
                .orElseThrow(() -> new IllegalArgumentException("OrderId[" + orderSeq + "] is Not exist "));

        //리뷰 본문 존재여부 확인
        checkArgument(isNotEmpty(content), "name must be provided");
        checkArgument(
                content.length() >= 1 && content.length() <= 1000,
                "content length must be between 1 and 1000 characters"
        );

        //이미 리뷰가 존재하면 작성 불가 (db에서 reviewSeq가 null일경우 rowmapper는 0을 리턴)
        checkArgument(findOrder.getReviewSeq() == 0, "Could not write review for order " + orderSeq + " because have already written");

        OrderState orderState = findOrder.getOrderState();


        //주문 완료상태가 아니면 리뷰 작성 불가
        checkArgument(orderState.equals(OrderState.COMPLETED), "Could not write review for order "
                + orderSeq + " because state(" + orderState + ") is not allowed");


        long userSeq = findOrder.getUserSeq();
        long productSeq = findOrder.getProductSeq();

        //상품의 리뷰 카운트 증가
        Product findProduct = productRepository.findById(productSeq)
                .orElseThrow(() -> new IllegalArgumentException("ProductId[" + productSeq + "] is Not exist "));

        findProduct.addReviewCount();

        Review review = reviewRepository.save(new Review(userSeq, productSeq, content));


        long reviewSeq = review.getSeq();
        Order renewOrder = new Order(findOrder, reviewSeq);

        productRepository.update(findProduct);
        orderRepository.update(renewOrder);

        return review;
    }
}
