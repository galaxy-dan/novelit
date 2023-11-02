package com.galaxy.novelit.comment.mapper;

import com.galaxy.novelit.comment.domain.CommentInfo;
import com.galaxy.novelit.comment.dto.CommentInfoDto;
import com.galaxy.novelit.comment.dto.request.CommentAddRequestDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentInfoMapper {
    CommentInfoDto infoToDto(CommentInfo commentInfo);
    CommentInfo dtoToInfo(CommentInfoDto commentInfoDto);

    CommentInfoDto addDtoToDto(CommentAddRequestDto commentAddRequestDto);

    //List<CommentInfoDto> listToDtolist(List<Comment>)
}
