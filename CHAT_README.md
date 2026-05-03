# 通义千问聊天应用

这是一个基于Spring Boot和通义千问API的聊天应用，提供类似聊天软件的界面，可以与AI助手进行对话。

## 功能特点

- 简洁美观的聊天界面
- 支持多轮对话，保留聊天历史
- 实时与通义千问AI助手交互
- 响应式设计，适配不同屏幕尺寸

## 技术栈

- 后端：Spring Boot 2.5.13
- 前端：HTML5, CSS3, JavaScript
- AI服务：通义千问API

## 项目结构

```
init-preject/
├── src/main/java/cn/tedu/gate/initial/
│   ├── controller/
│   │   └── QwenController.java        # 通义千问API控制器
│   ├── service/
│   │   └── impl/
│   │       └── QwenServiceImpl.java   # 通义千问服务实现
│   └── InitialApplication.java        # Spring Boot主应用类
├── src/main/resources/
│   ├── static/
│   │   └── chat.html                  # 聊天界面
│   └── application.yml                # 应用配置文件
└── pom.xml                            # Maven项目配置
```

## 如何运行

1. 确保已安装Java 8或更高版本
2. 确保已安装Maven
3. 在项目根目录下执行以下命令启动应用：

```bash
mvn spring-boot:run
```

4. 启动成功后，在浏览器中访问：

```
http://localhost:9009/chat.html
```

## 使用说明

1. 在聊天界面底部的输入框中输入您的问题
2. 点击发送按钮或按Enter键发送消息
3. AI助手将回复您的问题
4. 聊天历史将保留在当前会话中，支持多轮对话

## API接口

应用提供以下REST API接口：

- POST /api/qwen/chat - 与通义千问进行单轮对话
- POST /api/qwen/chat-with-history - 与通义千问进行多轮对话（带历史记录）
- GET /api/qwen/health - 健康检查接口

## 配置说明

在`application.yml`文件中可以配置以下参数：

- `server.port`: 服务器端口，默认为9009
- `dashscope.api-key`: 通义千问API密钥
- `dashscope.model-name`: 通义千问模型名称，默认为qwen-plus

## 注意事项

1. 确保通义千问API密钥已正确配置
2. 确保网络连接正常，能够访问通义千问API服务
3. 聊天历史仅保存在当前会话中，刷新页面后会丢失

## 开发者

如需修改或扩展功能，请参考以下文件：

- `QwenController.java` - 后端API控制器
- `QwenServiceImpl.java` - 通义千问服务实现
- `chat.html` - 前端聊天界面

## 许可证

本项目仅供学习和研究使用。
