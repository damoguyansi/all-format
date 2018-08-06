package com.damoguyansi.all.format.util;

import java.io.ByteArrayInputStream;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlFormat {

	public static String format(String src) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new ByteArrayInputStream(src.getBytes("UTF-8")));
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		String s = outputter.outputString(doc);
		return s;
	}
}


