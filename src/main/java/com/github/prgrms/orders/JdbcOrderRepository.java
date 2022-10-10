package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcOrderRepository implements OrderRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Order> findById(Long id) {
    // TODO Auto-generated method stub
    List<Order> results = jdbcTemplate.query(
            "select * from orders where seq=?",
            mapper,
            id
    );

    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public void update(Order order) {
    // TODO Auto-generated method stub
    jdbcTemplate.update(
            "update orders set review_seq=?, state=?, request_msg=?, reject_msg=?, completed_at=?, rejected_at=?" +
                    " where seq=?",
            order.getReviewSeq(),
            order.getOrderState().toString(),
            order.getRequestMsg(),
            order.getRejectMsg(),
            order.getCompletedAt(),
            order.getRejectedAt(),
            order.getSeq()
    );
  }


  static RowMapper<Order> mapper = (rs, rowNum) ->

          new Order.Builder()
                  .seq(rs.getLong("seq"))
                  .userSeq(rs.getLong("user_seq"))
                  .productSeq(rs.getLong("product_seq"))
                  .reviewSeq(rs.getLong("review_seq"))
                  .orderState(OrderState.valueOf(rs.getString("state")))
                  .requestMsg(rs.getString("request_msg"))
                  .rejectMsg(rs.getString("reject_msg"))
                  .completedAt(dateTimeOf(rs.getTimestamp("completed_at")))
                  .rejectedAt(dateTimeOf(rs.getTimestamp("rejected_at")))
                  .createAt(dateTimeOf(rs.getTimestamp("create_at")))
                  .build();
}