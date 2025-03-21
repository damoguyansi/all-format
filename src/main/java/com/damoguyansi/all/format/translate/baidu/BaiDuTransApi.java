package com.damoguyansi.all.format.translate.baidu;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.damoguyansi.all.format.translate.TransApiInterface;
import com.damoguyansi.all.format.translate.bean.TransResult;
import com.damoguyansi.all.format.util.TranslateUtil;

import java.util.Collections;
import java.util.List;

/**
 * 百度翻译工具类
 *
 * @author damoguyansi
 */
public class BaiDuTransApi implements TransApiInterface {
    private static final String TRANS_API_HOST = "https://api.fanyi.baidu.com/api/trans/vip/translate";
    private static final String APPID = "20180808000192639";
    private static final String SECURITYKEY = "H1lJ3ZYj6YMtyx_j5CGu";

    @Override
    public TransResult translate(String word, String translateType) throws Exception {
        long salt = System.currentTimeMillis();

        String from = "auto";
        String to = "auto";
        if (TranslateUtil.ZH_CN_TO_EN.equals(translateType)) {
            from = "zh";
            to = "en";
        } else if (TranslateUtil.EN_TO_ZH_CN.equals(translateType)) {
            from = "en";
            to = "zh";
        }

        String src = APPID + word + salt + SECURITYKEY; // 加密前的原文
        String sign = SecureUtil.md5(src);
        BaiDuTransRequest request = new BaiDuTransRequest();
        request.setFrom(from);
        request.setTo(to);
        request.setAppid(APPID);
        request.setQ(word);
        request.setSalt("" + salt);
        request.setSign(sign);
        try {
            String result = HttpUtil.get(TRANS_API_HOST, request.toMap());
            BaiDuTransResponse response = JSONUtil.toBean(result, BaiDuTransResponse.class);
            if (response.getError_code() != null) {
                throw new Exception("翻译报错-错误码:" + response.getError_code() + ",错误原因:" + response.getError_msg());
            }

            List<BaiDuTransResult> transResults = response.getTrans_result();
            System.out.println(JSONUtil.toJsonPrettyStr(transResults));

            if (null != transResults && transResults.size() > 0) {
                String dist = transResults.get(0).getDst();
                TransResult gt = new TransResult();
                TransResult.SentencesBean sentencesBean = new TransResult.SentencesBean();
                sentencesBean.setTrans(dist);
                sentencesBean.setOrig(word);
                gt.setSrc(word);
                gt.setSentences(Collections.singletonList(sentencesBean));
                return gt;
            }
            throw new Exception("无结果");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("BaiDu翻译异常" + e.getMessage());
        }
    }
}