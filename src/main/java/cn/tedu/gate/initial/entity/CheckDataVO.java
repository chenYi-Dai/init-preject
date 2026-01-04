package cn.tedu.gate.initial.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDataVO {

    private String name;

    private String fileUrl;

    private String age;

    private String imageUrl;
}
