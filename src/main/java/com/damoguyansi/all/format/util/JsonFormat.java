package com.damoguyansi.all.format.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * desc:
 *
 * @author guolong.qu
 * @date 2018/7/18 10:01
 */
public class JsonFormat {
	public static String format(String src) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
		mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
		mapper.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
		mapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true);
		mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		mapper.configure(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, true);
		mapper.configure(SerializationFeature.INDENT_OUTPUT,true);


		Object obj = mapper.readValue(src, Object.class);
		String s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		return s;
	}
}
