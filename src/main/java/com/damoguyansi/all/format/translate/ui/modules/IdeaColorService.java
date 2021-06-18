package com.damoguyansi.all.format.translate.ui.modules;

import com.intellij.util.ui.UIUtil;

/**
 * @author damoguyansi
 * @description:
 * @create: 2020-06-10 15:39
 **/
public class IdeaColorService extends ColorService {
    @Override
    protected <T> T internalForCurrentTheme(T[] objects) {
        if (objects == null) {
            return null;
        } else if (UIUtil.isUnderDarcula() && objects.length > 1) {
            return objects[1];
        } else {
            return objects[0];
        }
    }
}
