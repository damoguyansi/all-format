package com.damoguyansi.all.format.tools;

import cn.hutool.json.JSONUtil;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * JSON ↔ YAML ↔ Properties 互转。
 *
 * @author damoguyansi
 */
public class JsonYamlToolPanel extends AbstractToolPanel {

    public JsonYamlToolPanel() {
        addButton("JSON → YAML", this::jsonToYaml);
        addButton("YAML → JSON", this::yamlToJson);
        addButton("YAML → Properties", this::yamlToProps);
        addButton("Properties → YAML", this::propsToYaml);
        addButton("JSON → Properties", this::jsonToProps);
        addButton("Properties → JSON", this::propsToJson);
    }

    @Override
    public String title() {
        return "JSON/YAML";
    }

    private void jsonToYaml() {
        output.setText(dumpYaml(toPlain(JSONUtil.parse(inText()))));
    }

    private void yamlToJson() {
        output.setText(JSONUtil.toJsonPrettyStr(loadYaml()));
    }

    private void yamlToProps() {
        output.setText(PropsConverter.toProperties(loadYaml()));
    }

    private void propsToYaml() {
        output.setText(dumpYaml(PropsConverter.fromProperties(inText())));
    }

    private void jsonToProps() {
        output.setText(PropsConverter.toProperties(toPlain(JSONUtil.parse(inText()))));
    }

    private void propsToJson() {
        output.setText(JSONUtil.toJsonPrettyStr(PropsConverter.fromProperties(inText())));
    }

    /** hutool JSON 对象转成 JDK 容器，便于 snakeyaml / 扁平化处理。 */
    private Object toPlain(Object json) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(json), Object.class);
    }

    private Object loadYaml() {
        return new Yaml().load(inText());
    }

    private String dumpYaml(Object obj) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        return new Yaml(options).dump(obj);
    }
}
