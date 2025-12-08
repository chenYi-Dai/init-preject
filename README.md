# init-preject
初始化工程

## ACT_RE_PROCDEF 流程定义表 关键字段说明
该表是用于记录bpmn.xml文件的信息，关键字段为key、name、resource_name

| 字段名 | 数据类型 | 是否必填 | 说明 |
|--------|----------|----------|------|
| `ID_` | VARCHAR(64) | 是 | 流程定义的唯一标识符，格式为：`key:version` |
| `REV_` | INT | 是 | 版本号 |
| `CATEGORY_` | VARCHAR(255) | 否 | 流程分类 |
| `NAME_` | VARCHAR(255) | 否 | 流程名称 |
| `KEY_` | VARCHAR(255) | 是 | 流程定义的key，对应BPMN文件中的process id |
| `VERSION_` | INT | 是 | 流程版本号，相同key的流程每次部署会递增 |
| `DEPLOYMENT_ID_` | VARCHAR(64) | 是 | 关联的部署ID，指向ACT_RE_DEPLOYMENT表 |
| `RESOURCE_NAME_` | VARCHAR(4000) | 是 | BPMN文件名 |
| `DGRM_RESOURCE_NAME_` | VARCHAR(4000) | 否 | 流程图图片文件名 |
| `DESCRIPTION_` | VARCHAR(4000) | 否 | 流程描述 |
| `HAS_START_FORM_KEY_` | TINYINT | 否 | 是否有启动表单（0:无，1:有） |
| `HAS_GRAPHICAL_NOTATION_` | TINYINT | 否 | 是否有图形化标记（0:无，1:有） |
| `SUSPENSION_STATE_` | INT | 是 | 挂起状态（1:激活，2:挂起） |
| `TENANT_ID_` | VARCHAR(255) | 否 | 租户ID，用于多租户环境 |

## 核心字段说明

### 🔑 核心标识字段
- **`KEY_`**: 流程的业务标识，通常与业务逻辑相关
- **`VERSION_`**: 版本控制，相同KEY_的流程定义版本递增
- **`ID_`**: 唯一标识符，格式为 `KEY_:VERSION_`

### 📊 状态字段
- **`SUSPENSION_STATE_`**: 
  - `1` = 激活状态（可以启动流程实例）
  - `2` = 挂起状态（无法启动流程实例）

### 🔗 关联字段
- **`DEPLOYMENT_ID_`**: 关联部署记录，指向 `ACT_RE_DEPLOYMENT` 表
- **`RESOURCE_NAME_`**: BPMN XML文件名
- **`DGRM_RESOURCE_NAME_`**: 流程图图片文件名

## ACT_RE_DEPLOYMENT 部署记录表

该表是用于记录流程部署信息的元数据，每次部署流程定义（BPMN文件）时都会在该表中创建一条部署记录。

| 字段名 | 数据类型 | 是否必填 | 说明 |
|--------|----------|----------|------|
| `ID_` | VARCHAR(64) | 是 | 部署的唯一标识符 |
| `NAME_` | VARCHAR(255) | 否 | 部署名称 |
| `DEPLOY_TIME_` | TIMESTAMP | 是 | 部署时间 |
| `SOURCE_` | VARCHAR(255) | 否 | 部署来源 |
| `CATEGORY_` | VARCHAR(255) | 否 | 部署分类 |
| `TENANT_ID_` | VARCHAR(255) | 否 | 租户ID，用于多租户环境 |

## 核心字段说明

### 🔑 核心标识字段
- **`ID_`**: 部署的唯一标识符，用于关联流程定义和资源文件
- **`NAME_`**: 部署名称，便于识别和管理不同的部署
- **`DEPLOY_TIME_`**: 部署时间戳，记录部署的准确时间

### 🔗 关联关系
- **与 `ACT_RE_PROCDEF` 的关系**: 流程定义表通过 `DEPLOYMENT_ID_` 字段关联到部署表

### 📊 应用场景
- **版本管理**: 每次部署都会生成新的部署记录，便于版本追踪
- **回滚操作**: 可以通过部署ID找到特定版本的流程定义
- **多租户**: 通过 `TENANT_ID_` 支持多租户环境


