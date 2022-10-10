package com.github.prgrms.orders;

import java.time.LocalDateTime;

public class Order {

  private final Long seq;

  private final Long userSeq;

  private final Long productSeq;

  private final Long reviewSeq;

  private OrderState state;

  private String requestMsg;

  private String rejectMsg;

  private LocalDateTime completedAt;

  private LocalDateTime rejectedAt;

  private LocalDateTime createAt;


  public Order(Long seq, Long userSeq, Long productSeq, Long reviewSeq, OrderState orderState, String requestMsg, String rejectMsg, LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
    this.seq = seq;
    this.userSeq = userSeq;
    this.productSeq = productSeq;
    this.reviewSeq = reviewSeq;
    this.state = orderState;
    this.requestMsg = requestMsg;
    this.rejectMsg = rejectMsg;
    this.completedAt = completedAt;
    this.rejectedAt = rejectedAt;
    this.createAt = createAt;
  }

  public Order(Order before, long reviewSeq) {
    this(
      before.getSeq(),
      before.getUserSeq(),
      before.getProductSeq(),
      reviewSeq,
      before.getOrderState(),
      before.getRequestMsg(),
      before.getRejectMsg(),
      before.getCompletedAt(),
      before.getRejectedAt(),
      before.getCreateAt()
    );
  }

  public Long getSeq() {
    return seq;
  }

  public Long getUserSeq() {
    return userSeq;
  }

  public OrderState getOrderState() {
    return state;
  }

  public Long getProductSeq() {
    return productSeq;
  }

  public Long getReviewSeq() {
    return reviewSeq;
  }

  public String getRequestMsg() {
    return requestMsg;
  }

  public String getRejectMsg() {
    return rejectMsg;
  }

  public LocalDateTime getCompletedAt() {
    return completedAt;
  }

  public LocalDateTime getRejectedAt() {
    return rejectedAt;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }


  static public class Builder {
    private Long seq;
    private Long userSeq;
    private Long productSeq;
    private Long reviewSeq;
    private OrderState orderState;
    private String requestMsg;
    private String rejectMsg;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime createAt;

    public Builder() {/*empty*/}

    public Builder(Order order) {
      this.seq = order.seq;
      this.userSeq = order.userSeq;
      this.productSeq = order.productSeq;
      this.reviewSeq = order.reviewSeq;
      this.orderState = order.state;
      this.requestMsg = order.requestMsg;
      this.rejectMsg = order.rejectMsg;
      this.completedAt = order.completedAt;
      this.rejectedAt = order.rejectedAt;
      this.createAt = order.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }

    public Builder userSeq(Long userSeq) {
      this.userSeq = userSeq;
      return this;
    }

    public Builder productSeq(Long productSeq) {
      this.productSeq = productSeq;
      return this;
    }

    public Builder reviewSeq(Long reviewSeq) {
      this.reviewSeq = reviewSeq;
      return this;
    }

    public Builder orderState(OrderState orderState) {
      this.orderState = orderState;
      return this;
    }

    public Builder requestMsg(String requestMsg) {
      this.requestMsg = requestMsg;
      return this;
    }


    public Builder rejectMsg(String rejectMsg) {
      this.rejectMsg = rejectMsg;
      return this;
    }

    public Builder completedAt(LocalDateTime completedAt) {
      this.completedAt = completedAt;
      return this;
    }

    public Builder rejectedAt(LocalDateTime rejectedAt) {
      this.rejectedAt = rejectedAt;
      return this;
    }

    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public Order build() {

      return new Order(
              seq,
              userSeq,
              productSeq,
              reviewSeq,
              orderState,
              requestMsg,
              rejectMsg,
              completedAt,
              rejectedAt,
              createAt
      );
    }
  }

}