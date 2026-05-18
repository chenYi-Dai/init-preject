package cn.tedu.gate.initial.function;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class LocationNameFunction  {

    @Tool(description = "某个城市有多少人叫某个姓名")
    public String getLocationName(@ToolParam(description = "城市名称") String city, @ToolParam(description = "姓名") String name) {
            // 1. 参数校验
            if (city == null || city.isEmpty()) {
                throw new IllegalArgumentException("城市名称不能为空");
            }
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("姓名不能为空");
            }
            // 3. 这里是你的业务逻辑：调用第三方API、查数据库、计算等
            System.out.println("正在调用真实数据库接口查询：" + city + " " + name);

            // 模拟真实的数据查询结果
            return city + " 有 100 个人叫 " + name + "。";
    }
}
