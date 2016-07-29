# iFamily
## 介绍
- 实现WIFI下自动备份照片，备份参数可以设置。
- 实现随时随地浏览个人/家庭媒体库资料。
- 目标: 安全、简单
- 多客户端支持:手机、电视、pad

## 蓝图
- 数据备份
- 多客户端支持(IOS、Android)
- 随时随地浏览(安全浏览，认证，数据加密传输)
- 为媒体信息设置标签(分类)，比如：人物、地点等信息
- 家庭NAS(高可用、高性能)存储 + 文件浏览器(图片浏览器、音乐、视频媒体播放器)
- 工业级的安全性、可靠性

## 路线图

### 一期功能
- WIFI下自动备份照片和视频资料
- 可扩展的服务器端架构
- 适配不同的存储
- 客户端参数设置
  - WIFI下备份开关
  - 服务器地址配置
- 客户端列出文件名
- 服务器端配置文件备份目录
- 一期备份与浏览均在局域网进行，暂不考虑安全性(数据加密存储、通信层安全性)
- 身份认证(基于配置表)
- 可扩展的API，变更点都可扩展:
  - 身份认证
  - 传输层
  - 文件存储层
- 一期实现多用户支持(可以基于配置表)
