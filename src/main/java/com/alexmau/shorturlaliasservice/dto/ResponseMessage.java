package com.alexmau.shorturlaliasservice.dto;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Сообщение ответа.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ResponseMessage implements Serializable {

  /**
   * Сообщение.
   */
  private String message;

  /**
   * Код.
   */
  private Integer code;
}
