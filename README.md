# 控制灯开关
LightControlApplication
#### 实验目的
•	熟悉灯的控制流程
•	熟悉Switch组件使用
#### 实验过程
1.	按下开关灯面板，手机接收灯开关状态，更新UI
2.	手机打开灯光，关闭灯光
##### 拓展
•	熟悉灯光控制指令的发送封装

#### 核心类说明
| 类路径                                        | 描述                              |
| ------------------------------------------ | ------------------------------- |
| `owon/sdk/util/DeviceMessagesManager.java` | 设备操作类，封装灯光、电机、传感器等设备的控制与查询方法    |
| `owon/sdk/util/SocketMessageListener.java` | 数据回调类，包含登录结果、设备操作结果、设备状态获取等回调方法 |

###### 设备数据回调方法
类名： SocketMessageListener.java
方法： getMessage
| 字段名         | 类型    | 描述                 |
| ----------- | ----- | ------------------ |
| `commandID` | int   | 数据回调码（对应回调类型常量）    |
| `bean`      | class | 数据返回基类，需强转为对应设备的子类 |


#### 灯光控制

方法: setUpSwitch

#### 参数

| 参数名     | 类型    | 描述         |
|------------|---------|--------------|
| infobean   | class   | 设备实体     |
| switchgear | boolean | 灯开关       |

#### 返回值

bean 转换子类 `z_UpdateSwitchgearBean`

| 属性        | 类型   | 描述              |
|-------------|--------|-------------------|
| switchgear  | String | on 开，off 关     |
| ieee        | String | 设备物理地址      |
| ep          | int    | 设备节点          |




#### 数据回调码
回调方法： getMessage
| 属性                                    | 描述           |
| ------------------------------------- | ------------ |
| `Constants.UpdateEPList`              | 获取网关列表       |
| `Constants.ZigBeeGetEPList`           | 获取设备列表       |
| `Constants.SmartLightSetupSwitchgear` | 设置灯光结果       |
| `Constants.UpdateSwitchgear`          | 查询或控制灯后的状态回调 |
| `Constants.UpdateLight`               | 物理控制灯后的状态回调  |
| `Constants.THI_UPDATE`                | 温湿度传感器数据回调   |
| `Constants.ILLUM_UPDATE`              | 光照传感器数据回调    |
| `Constants.MotionSensorUpdate`        | 人体传感器查询状态回调  |
| `Constants.MotionSensor`              | 人体传感触发数据回调   |
| `Constants.WarningSensor`             | 烟雾监测数据回调     |

#### 设备类型
| 属性                                            | 描述        |
| --------------------------------------------- | --------- |
| `Constants.LIGHT_601`                         | 灯，只有开关    |
| `Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB` | 可调节亮度和色温灯 |
| `DeviceTypeCode.TH_SENSOR`                    | 温湿度传感器    |
| `DeviceTypeCode.LX_SENSOR`                    | 光照传感器     |
| `DeviceTypeCode.SMOKE_SENSOR_ZONE`            | 烟雾报警器     |
| `DeviceTypeCode.MOTION_SENSOR_ZONE`           | 人体传感器     |
| `DeviceTypeCode.AC_SENSOR`                    | 红外转发器     |
| `DeviceTypeCode.WARN_SENSOR`                  | 声光报警器     |
| `DeviceTypeCode.WARN_MOTOR`                   | 窗帘电机      |
| `DeviceTypeCode.DOOR_SENSOR`                  | 门磁感应器     |



