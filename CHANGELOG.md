# AllFormat Plugin Changelog

## [2.0.0] (2026/06/05)
A major rewrite — AllFormat is now a dockable, all-in-one developer toolbox. (重大版本：AllFormat 全面重构为可停靠的一体化开发者工具箱)

### Highlights (亮点)
- Lives in a dockable **Tool Window** (no more popup dialog); compact, flat, follows the IDE theme (改为可停靠**工具窗口**，移除弹框；紧凑扁平、跟随 IDE 主题)
- **Translation rebuilt** on Microsoft Translator via the platform HTTP SDK: auto language detection, pronunciation/phonetics and dictionary meanings, in-editor **Ctrl+Alt+U** balloon, and type-to-translate (基于平台 HTTP SDK 用微软翻译重构：自动识别语种、发音/音标与词典释义、编辑器内 **Ctrl+Alt+U** 气泡、输入即译)

### New tools (新增工具)
- JSON format/minify · Base64 · Unicode/URL/MD5 · QR code (generate / decode / paste image) (JSON 格式化压缩、Base64、Unicode/URL/MD5、二维码生成识别与粘贴图片)
- Generator: timestamp ⇄ date, UUID / Snowflake ID, random password (生成器：时间戳⇄日期、UUID/雪花 ID、随机密码)
- Hash: MD5 / SHA-1 / SHA-256 / SHA-512 / HMAC-SHA256 (哈希全家桶)
- JSON ⇄ YAML ⇄ Properties conversion (JSON⇄YAML⇄Properties 互转)
- Text Diff (line-level) (文本对比)

### Quality (工程质量)
- Removed legacy popup, Google/Baidu engines and dead code; logging uses the platform Logger (移除旧弹框、Google/百度引擎与死代码；统一平台日志)
- Settings page (auto-translate delay, smart clipboard); wrapping toolbars so buttons never clip in narrow windows (设置页 + 自动换行工具栏)
- IDE compatibility bound to 2024.2–2025.2 (兼容 2024.2–2025.2)

## [1.4.2] (2025/09/17)
- Bug fixes.
- Bug 修复

## [1.4.1] (2025/03/21)
- Optimize the display of forms (优化窗体显示)
- Bug fixes.
- Bug 修复

## [1.4.0] (2022/10/24)
- Change Google Translation to Baidu Translation (更换 Google 翻译为百度翻译)
- Add URL encoding and URL decoding (新增加 URL 编码、URL 解码)
- Integrate Md5, Unicode and Url codes into one panel (融合 Md5、Unicode、Url 编码到一个 panel)

## [1.3.0] (2011/11/18)
- Solve the problem of inaccessible Google translation address (解决 Google 翻译地址不通问题)

## [1.2.0] (2021/06/23)
- Add 2, 8, 10, 16, 32 base conversion (增加 2 进制、8 进制、10 进制、16 进制、32 进制转换)
- UI interface adjustments, dark theme label font color tweak (UI 界面调整变化，暗黑主题标签字体颜色调整)
- Optimize translation panel, support multi-word translation (优化翻译板块，多词一起翻译)
- Add bubble translation with Ctrl+Alt+U for selected words (增加选中单词 Ctrl+Alt+U 进行气泡翻译)

## [1.1.2] (2021/01/27)
- Add Chinese-English translation module (增加中英文翻译模块)
- Optimize window closing speed (优化关闭窗口速度)
- Add QR code copy-paste function (增加二维码复制粘贴功能)
- Modify QR code container (修改二维码容器)
- Remove QR code image upload (去除上传二维码图片)

## [1.1.1] (2020/11/27)
- Add "About Project" link (新增加关于项目连接)
- Optimize dark theme content background color (优化黑暗主题内容背景颜色)
- Optimize SQL formatting (优化 SQL 格式化)
- Modify default window size (修改默认窗体大小)
- Add QR code image upload recognition (增加上传二维码图片识别)
- Add caching for pin-to-top and line-wrap settings (增加置顶、换行设置缓存)

## [1.1.0] (2020/09/23)
- New: Base64 generate
- New: Unicode conversion
- New: qr code string
- New: format json string
- New: format xml string
- New: format map string
- New: format html string
