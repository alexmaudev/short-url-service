package com.alexmau.shorturlaliasservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ShortUrlAlias implements Serializable {

    @JsonProperty("alias")
    private String alias;

    @NotNull
    @JsonProperty("url")
    private String url;
}
