package com.encore.byebuying.config.advice;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** @RestControllerAdvice
 * @ControllerAdvice와 @ResponseBody를 합쳐놓은 어노테이션 - @ControllerAdvice와 동일한 역할을 수행하고, 추가적으로 @ResponseBody를 통해 객체를 리턴할 수도 있다.
 *  => 단순한 예외처리만 하고 싶을 경우 @ControllerAdvice를 사용, 응답객체까지 전달하고 싶을 경우 @RestControllerAdvice를 사용
 */
@RestControllerAdvice
public class CommonExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

  // TODO: 2022/08/29 CommonResponse가 만들어지면 CommonException -> CommonResponse로 수정
  // new CommonResponse(e)
  // new CommonResponse(data, ExceptionCode)

  /** @ExceptionHanlder
   * 메서드에 선언하고 특정 예외 클래스를 지정해주면 해당 예외가 발생했을 때 메서드에 정의한 로직으로 처리
   * ControllerAdvice 또는 RestControllerAdvice에 정의된 메서드가 아닌 일반 컨트롤러 단에 존재하는 메서드에 선언할 경우, 해당 Controller에만 적용된다
   *  => Controller, RestController에만 적용이 가능하다 -> @Service 등의 빈에서는 적용 안
   */

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = CommonException.class)
  public CommonException defaultErrorHandler(HttpServletRequest req, CommonException e) {
    logger.warn("defaultErrorHandler : " + e.toString());
    return e;
  }

  // methodArgumentTypeMismatchErrorHandler

  // bindingErrorHandler

  // methodArgumentNotValidErrorHandler

  // missingServletRequestParameterErrorHandler

  // httpMessageNotReadableErrorHandler

  // errorHandler

}
