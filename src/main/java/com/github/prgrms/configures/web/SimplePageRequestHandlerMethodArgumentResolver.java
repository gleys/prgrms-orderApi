package com.github.prgrms.configures.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimplePageRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String DEFAULT_OFFSET_PARAMETER = "offset";

  private static final String DEFAULT_SIZE_PARAMETER = "size";

  private static final long DEFAULT_OFFSET = 0;

  private static final int DEFAULT_SIZE = 5;

  private String offsetParameterName = DEFAULT_OFFSET_PARAMETER;

  private String sizeParameterName = DEFAULT_SIZE_PARAMETER;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return Pageable.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
          MethodParameter methodParameter,
          ModelAndViewContainer mavContainer,
          NativeWebRequest webRequest,
          WebDataBinderFactory binderFactory
  ) {
    String offsetString = webRequest.getParameter(offsetParameterName);
    String sizeString = webRequest.getParameter(sizeParameterName);

    // offset, size 가 null일 경우 기본값으로 대체
    Long offset = offsetString == null ? DEFAULT_OFFSET : Long.parseLong(offsetString);
    Integer size = sizeString == null ? DEFAULT_SIZE : Integer.parseInt(sizeString);

    // 0<= offset < Long.MAX , 0<= size <= 5
    offset = offset > Long.MAX_VALUE || offset < 0 ? DEFAULT_OFFSET : offset;
    size = size > 5 || size < 0 ? DEFAULT_SIZE : size;

    SimplePageRequest simplePageRequest = new SimplePageRequest(offset, size);

//    SimplePageRequest simplePageRequest;
//
//    // offset,size 조건 비교를 simplePageRequest 에서 함
//    try {
//      simplePageRequest = new SimplePageRequest(Long.parseLong(offsetString), Integer.parseInt(sizeString));
//    }
//    catch (IllegalArgumentException e) {
//      simplePageRequest = new SimplePageRequest();
//    }

    return simplePageRequest;


    // TODO 구현이 필요 합니다.
  }

  public void setOffsetParameterName(String offsetParameterName) {
    this.offsetParameterName = offsetParameterName;
  }

  public void setSizeParameterName(String sizeParameterName) {
    this.sizeParameterName = sizeParameterName;
  }

}