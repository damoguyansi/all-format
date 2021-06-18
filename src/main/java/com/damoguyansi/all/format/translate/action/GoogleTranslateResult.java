package com.damoguyansi.all.format.translate.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;

import java.util.List;

/**
 * @author damoguyansi
 * @description: 谷歌翻译结果（为了方便Gson格式化，命名有点奇怪）
 * @create: 2021-05-30 17:58
 **/
public class GoogleTranslateResult {
    private String src;
    private List<SentencesBean> sentences;
    private List<DictBean> dict;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<SentencesBean> getSentences() {
        return sentences;
    }

    public void setSentences(List<SentencesBean> sentences) {
        this.sentences = sentences;
    }

    public List<DictBean> getDict() {
        return dict;
    }

    public void setDict(List<DictBean> dict) {
        this.dict = dict;
    }

    public static class SentencesBean {
        private String trans;
        private String orig;
        private int backend;
        private String translit;
        private String srcTranslit;

        public String getTrans() {
            return trans;
        }

        public void setTrans(String trans) {
            this.trans = trans;
        }

        public String getOrig() {
            return orig;
        }

        public void setOrig(String orig) {
            this.orig = orig;
        }

        public int getBackend() {
            return backend;
        }

        public void setBackend(int backend) {
            this.backend = backend;
        }

        public String getTranslit() {
            return translit;
        }

        public void setTranslit(String translit) {
            this.translit = translit;
        }

        //love
        public String getSrcTranslit() {
            return srcTranslit;
        }

        public void setSrcTranslit(String srcTranslit) {
            this.srcTranslit = srcTranslit;
        }
    }

    public static class DictBean {
        private String pos;
        private int posEnum;
        private List<EntryBean> entry;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public List<EntryBean> getEntry() {
            return entry;
        }

        public void setEntry(List<EntryBean> entry) {
            this.entry = entry;
        }

        public static class EntryBean {
            private String word;
            private double score;
            private List<String> reverseTranslation;

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public List<String> getReverseTranslation() {
                return reverseTranslation;
            }

            public void setReverseTranslation(List<String> reverseTranslation) {
                this.reverseTranslation = reverseTranslation;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='content'>");

        //sentences部分,form
        sb.append("<div class='sentences'>");
        for (SentencesBean sen : this.getSentences()) {
            if (StrUtil.isNotBlank(sen.getOrig())) {
                sb.append(genDiv(16, "#EE6305", true))
                        .append(HtmlUtil.escape(sen.getOrig()))
                        .append("</div>")
                        .append(genDiv(16, "#1C0B93"))
                        .append(HtmlUtil.escape(sen.getTrans()))
                        .append("</div>");
            }
        }
        sb.append("</div>");

        //dict部分
        sb.append("<div class='dicts' style='margin-top:10px;'>");
        if (null != this.getDict()) {
            for (DictBean dict : this.getDict()) {
                //pos部分
                sb.append("<div class='dict-item'>");//form,#A945BA,
                sb.append("    " + genDiv("dict-item-pos", 12, "#3A4A3C", true)).append(dict.getPos()).append("</div>");
                //entry部分
                sb.append("    <div class='entrys' style='margin-left:10px;'>");
                for (DictBean.EntryBean entry : dict.getEntry()) {
                    sb.append("    <div class='entry-item' style='margin-bottom:7px;'");
                    sb.append("        " + genDiv("entry-item-word", 11, "#A945BA")).append(entry.getWord()).append("</div>");
                    sb.append("        " + genDiv("entry-item-translation", 10, "#4141E9", true)).append(CollUtil.join(entry.getReverseTranslation(), " ")).append("</div>");
                    sb.append("    </div>");
                }
                sb.append("    </div>");
                sb.append("</div>");
            }
        }
        sb.append("</div>");

        sb.append("</div>");
        System.out.println(sb.toString());
        return sb.toString();
    }

    private String genDiv(String className, int fontSize, String color, Boolean xieTi) {
        StringBuilder sb = new StringBuilder("<div class='" + className + "' style='");
        sb.append("font-size:" + fontSize + "px;");
        sb.append("color:" + color + ";");
        if (true == xieTi) sb.append("font-style:italic;");
        sb.append("'>");
        return sb.toString();
    }

    private String genDiv(String className, int fontSize, String color) {
        StringBuilder sb = new StringBuilder("<div class='" + className + "' style='");
        sb.append("font-size:" + fontSize + "px;");
        sb.append("color:" + color + ";");
        sb.append("'>");
        return sb.toString();
    }

    private String genDiv(int fontSize, String color, Boolean xieTi) {
        StringBuilder sb = new StringBuilder("<div style='");
        sb.append("font-size:" + fontSize + "px;");
        sb.append("color:" + color + ";");
        if (true == xieTi) sb.append("font-style:italic;");
        sb.append("'>");
        return sb.toString();
    }

    private String genDiv(int fontSize, String color) {
        StringBuilder sb = new StringBuilder("<div style='");
        sb.append("font-size:" + fontSize + "px;");
        sb.append("color:" + color + ";");
        sb.append("'>");
        return sb.toString();
    }
}
