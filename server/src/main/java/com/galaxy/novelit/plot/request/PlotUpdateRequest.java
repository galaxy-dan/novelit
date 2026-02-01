package com.galaxy.novelit.plot.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record PlotUpdateRequest (@NotBlank String plotUuid,
                                 @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$"
                                         , message = "플롯제목은 한글이나 영어, 숫자만 가능합니다.")
                                 String plotTitle,
                                 String story,
                                 String beginning,
                                 String rising,
                                 String crisis,
                                 String climax,
                                 String ending) {

}
