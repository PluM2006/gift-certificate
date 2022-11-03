package ru.clevertec.ecl.interceptors;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.clevertec.ecl.dto.OrderDTO;

@Component
@RequiredArgsConstructor
public class ResponseEntityHandler {

  private final RequestSender requestSender;

  public ResponseEntity<Object> getObjectResponseEntity(CachedBodyHttpServletRequest  requestWrapper,
                                                        List<Integer> ports) {
    return requestSender.forwardRequest(requestWrapper, ports)
        .stream()
        .limit(1)
        .findFirst()
        .orElseThrow(NoSuchElementException::new);
  }

  public List<ResponseEntity<List<OrderDTO>>> getOrderDTOResponseEntity(CachedBodyHttpServletRequest  requestWrapper,
                                                                        List<String> urls) {
    return requestSender.forwardRequestList(requestWrapper, urls);
  }

}
