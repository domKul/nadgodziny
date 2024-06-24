package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;

import java.util.List;

class OvertimeMapper {

     public static OvertimeResponseDto mapToOvertimeResponseDto(OvertimeEntity overtime){
         return new OvertimeResponseDto(overtime.getId(),
                 overtime.getCreationDate(),
                 overtime.getOvertimeDate(),
                 overtime.getStatus(),
                 overtime.getDuration());
     }

     public static List<OvertimeResponseDto>mapToOvertimeResponseDtoList(List<OvertimeEntity> overtime){
         return overtime.stream()
                 .map(OvertimeMapper::mapToOvertimeResponseDto)
                 .toList();
     }
}
