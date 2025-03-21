package com.damoguyansi.all.format.translate;

import com.damoguyansi.all.format.translate.bean.TransResult;

public interface TransApiInterface {
    TransResult translate(String word, String translateType) throws Exception;
}
