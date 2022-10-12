package com.github.prgrms.orders;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {

    private long seq;
    private long productId;
    private ReviewDTO review;
    private OrderState state;
    private String requestMessage;
    private String rejectMessage;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime createAt;

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(Long reviewSeq, Long productSeq, String content, LocalDateTime createAt) {
        this.review = new ReviewDTO(reviewSeq, productSeq, content, createAt);
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    class ReviewDTO {
        private Long seq;
        private Long productId;
        private String content;
        LocalDateTime createAt;

        public ReviewDTO(Long reviewSeq, Long productSeq, String content, LocalDateTime createAt) {
            this.seq = reviewSeq;
            this.productId = productSeq;
            this.content = content;
            this.createAt = createAt;
        }
    }


    public OrderResponseDTO() {
    }

    public OrderResponseDTO(long seq, long productId, OrderState orderState, String requestMessage, String rejectMessage, LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
        this.seq = seq;
        this.productId = productId;
        this.state = orderState;
        this.requestMessage = requestMessage;
        this.rejectMessage = rejectMessage;
        this.completedAt = completedAt;
        this.rejectedAt = rejectedAt;
        this.createAt = createAt;
    }

    static public class Builder {
        private long seq;
        private long productId;
        private OrderState orderState;
        private String requestMessage;
        private String rejectMessage;
        private LocalDateTime completedAt;
        private LocalDateTime rejectedAt;
        private LocalDateTime createAt;

        public Builder() {
        }

        public Builder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }


        public Builder orderState(OrderState orderState) {
            this.orderState = orderState;
            return this;
        }

        public Builder requestMessage(String requestMessage) {
            this.requestMessage = requestMessage;
            return this;
        }

        public Builder rejectMessage(String rejectMessage) {
            this.rejectMessage = rejectMessage;
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

        public OrderResponseDTO build() {
            return new OrderResponseDTO(
                    seq,
                    productId,
                    orderState,
                    requestMessage,
                    rejectMessage,
                    completedAt,
                    rejectedAt,
                    createAt
            );
        }
    }


}
