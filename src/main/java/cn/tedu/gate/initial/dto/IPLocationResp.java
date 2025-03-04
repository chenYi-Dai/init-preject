package cn.tedu.gate.initial.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class IPLocationResp {

    private String ip;

    private IPLocationInfo location;

    private PositionInfo ad_info;

    @Setter
    @Getter
    public static class IPLocationInfo {
        /**
         * 纬度
         */
        private Long lat;
        /**
         * 经度
         */
        private Long lng;
    }

    /**
     * 定位行政区划信息
     */
    @Setter
    @Getter
    public static class PositionInfo {
        /**
         * 国家
         */
        private String nation;
        /**
         * 国家代码（ISO3166标准3位数字码）
         */
        private Long nation_code;
        /**
         * 省
         */
        private String province;
        /**
         * 市
         */
        private String city;
        /**
         * 区
         */
        private String district;
        /**
         * 行政区划代码
         */
        private Long adcode;
    }
}
