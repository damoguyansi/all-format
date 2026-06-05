# AllFormat 开发计划 (P0–P3)

> 定位：**"会翻译的开发者工具箱"**。先用翻译（音标+词典+编辑器内划词/输入即译）建立护城河，
> 再用离线工具箱提升日常打开率。形态对齐 IDEA 原生（ToolWindow），同时**保留独立窗口**，二者可切换。

## 架构原则
- UI 内容统一收敛到根面板 `centerPanel`，**同一套面板**既能放进独立 `JFrame`，也能放进 `ToolWindow`。
- 全局状态用 `PersistentStateComponent`（`AppSettings`）持久化：显示形态、翻译引擎/Key、防抖时长、默认 Tab。
- 每个工具 = 一个子面板/一个操作，新增工具应"零结构成本"。
- 网络一律走平台 `HttpRequests` 且只在后台线程；UI 更新回到 EDT。
- 纯离线工具优先用成熟库（不拷贝 GPL 源码）：`java-jwt`、`snakeyaml`/`jackson`、`commons-codec`、`cron-utils`。

---

## P0 — 地基（结构性，最高优先级）
- [x] **窗口 / ToolWindow 双形态 + 切换开关**（保留独立窗口；新增可停靠 ToolWindow；设置与面板内按钮均可切换）
- [x] **设置页** `Configurable` + `AppSettings`（显示形态、自动翻译防抖、智能剪贴板；翻译引擎/Key 待补）
- [ ] **i18n**：`messages/AllFormatBundle.properties`（EN / 中文），消除散落的中文硬编码/Unicode 转义
- [x] 核心路径日志改用平台 `Logger`，去掉 `System.out`/`printStackTrace`（深层 util 待续）

## P1 — 工具箱达标（纯离线，高 ROI）
- [x] 时间戳 ↔ 日期（含时区、毫秒/秒）
- [x] UUID / 雪花 ID 生成
- [x] JWT 解码（header/payload/过期校验）
- [x] Hash 全家桶：MD5 / SHA-1 / SHA-256 / SHA-512 / HMAC-SHA256
- [x] JSON ↔ YAML 互转（XML/JSONPath 待补）
- [x] 文本 Diff（行级 LCS）
- [ ] Cron 表达式解释（Unix/Quartz/Spring）
- 注：Regex / Color / JWT 已按需求移除

## P2 — 编辑器内联（粘性来源）
- [ ] 右键菜单 + Intention：对选中文本就地 格式化 / Base64 / URL / 大小写 / 命名转换
- [ ] 划词翻译增强：生词本收藏、翻译历史、整段（注释/字符串）翻译
- [ ] "粘贴即识别"：剪贴板是 JSON/JWT/Base64/URL 时，打开自动跳到对应 Tab（已部分具备，做成可配置）

## P3 — 质量与增长
- [ ] Marketplace SEO：标题/关键词含 Developer Tools / 工具箱 / 翻译
- [ ] 高质量截图 + 演示 GIF（主打"写代码时划词翻译带音标"）
- [ ] 双语 README + 文档站
- [ ] 渠道发布：掘金 / SegmentFault / V2EX（中文盘）+ Reddit r/IntelliJIDEA / DevToys 社区（国际盘）
- [ ] 无遥测承诺、启动性能基线

---

## 里程碑顺序
1. P0 双形态切换 ✅ → 设置页 → i18n
2. P1 工具逐个加（每个：子面板 + 操作 + 单测）
3. P2 编辑器内联
4. P3 上架与增长
