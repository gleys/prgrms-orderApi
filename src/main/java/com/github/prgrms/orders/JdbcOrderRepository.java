package com.github.prgrms.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;

@Repository
public class JdbcOrderRepository implements OrderRepository {

  private final NamedParameterJdbcTemplate namedJdbcTemplate;
  private final JdbcTemplate jdbcTemplate;

  public JdbcOrderRepository(NamedParameterJdbcTemplate namedJdbcTemplate, JdbcTemplate jdbcTemplate) {
    this.namedJdbcTemplate = namedJdbcTemplate;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Order> findById(Long id) {
    // TODO Auto-generated method stub
    Order result = jdbcTemplate.queryForObject(
        "select * from orders where seq=?",
            orderMapper,
            id);

    return Optional.ofNullable(result);
  }


  @Override
  public List<OrderResponseDTO> findAll(Long userSeq, long offset, int size) {

    String sql =  "select o.seq, o.review_seq, o.product_seq, o.state, o.request_msg, o.reject_msg, o.completed_at, o.rejected_at, o.create_at," +
                  " r.content, r.create_at as r_create_at" +
                  " from orders o left join reviews r on o.review_seq = r.seq" +
                  " where o.user_seq =:userSeq order by o.seq desc" +
                  " limit :size offset :offset";


//    Map<String, Object> param = Map.of("userSeq", userSeq);
    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("userSeq", userSeq)
            .addValue("size", size)
            .addValue("offset", offset);

    List<OrderResponseDTO> result = namedJdbcTemplate.query(sql, param, OrderResponseDTOMapper);

    return result;
  }

  @Override
  public OrderResponseDTO findWithReviewById(Long orderSeq) {
    String sql = "select o.seq, o.review_seq, o.product_seq, o.state, o.request_msg, o.reject_msg, o.completed_at, o.rejected_at, o.create_at," +
            " r.content, r.create_at as r_create_at" +
            " from orders o left join reviews r on o.review_seq = r.seq" +
            " where o.seq =?";

    OrderResponseDTO result = jdbcTemplate.queryForObject(sql, OrderResponseDTOMapper, orderSeq);

    return result;
  }


  @Override
  public void update(Order order) {

    String sql = "update orders " +
            "set state =:state, review_seq =:review_seq, reject_msg =:reject_msg, rejected_at =:rejected_at, completed_at =:completed_at " +
            "where seq =:id";

    SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("state", order.getState().toString())
            .addValue("review_seq", order.getReviewSeq() == 0 ? null : order.getReviewSeq())
            .addValue("reject_msg", order.getRejectMsg())
            .addValue("rejected_at", order.getRejectedAt())
            .addValue("completed_at", order.getCompletedAt())
            .addValue("id", order.getSeq());

    namedJdbcTemplate.update(sql, parameters);

  }

  private RowMapper<OrderResponseDTO> OrderResponseDTOMapper = (rs, rowNum) -> {

    OrderResponseDTO orderResponseDTO = new OrderResponseDTO.Builder()
            .seq(rs.getLong("seq"))
            .productId(rs.getLong("product_seq"))
            .orderState(OrderState.valueOf(rs.getString("state")))
            .requestMessage(rs.getString("request_msg"))
            .rejectMessage(rs.getString("reject_msg"))
            .completedAt(dateTimeOf(rs.getTimestamp("completed_at")))
            .rejectedAt(dateTimeOf(rs.getTimestamp("rejected_at")))
            .createAt(dateTimeOf(rs.getTimestamp("create_at")))
            .build();

    long reviewSeq = rs.getLong("review_seq");

    if (reviewSeq > 0) {
      Long productSeq = rs.getLong("product_seq");
      String content = rs.getString("content");
      LocalDateTime createAt = dateTimeOf(rs.getTimestamp("r_create_at"));

      orderResponseDTO.setReview(reviewSeq, productSeq, content, createAt);
    }

    return orderResponseDTO;
  };


  static RowMapper<Order> orderMapper = (rs, rowNum) ->
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