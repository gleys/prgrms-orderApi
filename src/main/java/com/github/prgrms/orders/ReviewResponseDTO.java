package com.github.prgrms.orders;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    private long seq;

    private long productId;

    private String content;

    private LocalDateTime creatAt;

    public ReviewResponseDTO(Review review) {
        this.seq = review.getSeq();
        this.productId = review.getProductSeq();
        this.content = review.getContent();
        this.creatAt = review.getCreateAt();
    }
}
