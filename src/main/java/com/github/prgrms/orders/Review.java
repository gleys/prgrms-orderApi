package com.github.prgrms.orders;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

public class Review {

  private final Long seq;

  private final long userSeq;

  private final long productSeq;

  private String content;

  private final LocalDateTime createAt;

  public Review(long userSeq, long productSeq, String content) {
    this(null, userSeq, productSeq, content, null);
  }

  public Review(Long seq, long userSeq, long productSeq, String content, LocalDateTime createAt) {
    checkArgument(isNotEmpty(content), "name must be provided");
    checkArgument(
            content.length() >= 1 && content.length() <= 1000,
            "content length must be between 1 and 1000 characters"
    );

    this.seq = seq;
    this.userSeq = userSeq;
    this.productSeq = productSeq;
    this.content = content;
    this.createAt = defaultIfNull(createAt, LocalDateTime.now());
  }

  public Long getSeq() {
    return seq;
  }

  public Long getUserSeq() {
    return userSeq;
  }

  public Long getProductSeq() {
    return productSeq;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("userId", userSeq)
      .append("productId", productSeq)
      .append("content", content)
      .append("createAt", createAt)
      .toString();
  }

  static public class Builder {
    
    private Long seq;
    private Long userSeq;
    private Long productSeq;
    private String content;
    private LocalDateTime createAt;

    public Builder() {/*empty*/}

    public Builder(Review review) {
      this.seq = review.seq;
      this.userSeq = review.userSeq;
      this.productSeq = review.productSeq;
      this.content = review.content;
      this.createAt = review.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }
    public Builder userId(Long userSeq) {
      this.userSeq = userSeq;
      return this;
    }
    public Builder productId(Long productSeq) {
      this.productSeq = productSeq;
      return this;
    }
    public Builder content(String content) {
      this.content = content;
      return this;
    }
    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public Review build() {
      return new Review(
        userSeq,
        productSeq,
        content
      );
    }
  }

}