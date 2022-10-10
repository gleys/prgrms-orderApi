package com.github.prgrms.orders;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Review save(Review review) {
    // TODO Auto-generated method stub
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("reviews").usingGeneratedKeyColumns("seq");

    long userSeq = review.getUserSeq();
    long productSeq = review.getProductSeq();
    String content = review.getContent();
    LocalDateTime createAt = review.getCreateAt();

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_seq", userSeq);
    parameters.put("product_seq", productSeq);
    parameters.put("content", content);
    parameters.put("create_at", createAt);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    long reviewSeq = key.longValue();

    return new Review(reviewSeq, userSeq, productSeq, content, createAt);
  }

  @Override
  public Optional<Review> findById(long id) {
    return Optional.empty();
  }

}
