package com.damoguyansi.all.format.translate.bean;

import com.damoguyansi.all.format.util.CollUtil;
import com.damoguyansi.all.format.util.ColorUtil;
import com.damoguyansi.all.format.util.HtmlUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 翻译结果
 *
 * @author damoguyansi
 */
public class TransResult implements Serializable {

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

        public String getTranslit() {
            return translit;
        }

        public void setTranslit(String translit) {
            this.translit = translit;
        }

        public String getSrcTranslit() {
            return srcTranslit;
        }

        public void setSrcTranslit(String srcTranslit) {
            this.srcTranslit = srcTranslit;
        }
    }

    public static class DictBean {
        private String pos;
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
        StringBuilder sb = new StringBuilder("<div class='content'>");

        // 原文、译文、发音（音标/转写）
        sb.append("<div class='sentences'>");
        for (SentencesBean sen : getSentences()) {
            if (sen.getOrig() == null) continue;
            sb.append(line(16, "#EE6305", true, HtmlUtil.escape(sen.getOrig())));
            if (sen.getSrcTranslit() != null && !sen.getSrcTranslit().isEmpty()) {
                sb.append(line(12, ColorUtil.entryTransColor(), false, "[" + HtmlUtil.escape(sen.getSrcTranslit()) + "]"));
            }
            sb.append(line(16, ColorUtil.transColor(), false, HtmlUtil.escape(sen.getTrans())));
            if (sen.getTranslit() != null && !sen.getTranslit().isEmpty()) {
                sb.append(line(12, ColorUtil.entryTransColor(), false, "[" + HtmlUtil.escape(sen.getTranslit()) + "]"));
            }
        }
        sb.append("</div>");

        // 词典：按词性归类的其它释义
        if (getDict() != null && !getDict().isEmpty()) {
            sb.append("<div class='dicts' style='margin-top:10px;'>");
            for (DictBean dict : getDict()) {
                sb.append("<div class='dict-item'>");
                sb.append(line(12, ColorUtil.posColor(), true, HtmlUtil.escape(dict.getPos())));
                sb.append("<div class='entrys' style='margin-left:10px;'>");
                int idx = 0;
                for (DictBean.EntryBean entry : dict.getEntry()) {
                    if (idx++ >= 3) break;
                    sb.append("<div class='entry-item' style='margin-bottom:7px;'>");
                    sb.append(line(11, ColorUtil.entryColor(), false, HtmlUtil.escape(entry.getWord())));
                    if (entry.getReverseTranslation() != null && !entry.getReverseTranslation().isEmpty()) {
                        sb.append(line(10, ColorUtil.entryTransColor(), true,
                                HtmlUtil.escape(CollUtil.join(entry.getReverseTranslation(), " "))));
                    }
                    sb.append("</div>");
                }
                sb.append("</div></div>");
            }
            sb.append("</div>");
        }

        return sb.append("</div>").toString();
    }

    private String line(int fontSize, String color, boolean italic, String text) {
        return "<div style='font-size:" + fontSize + "px;color:" + color + ";"
                + (italic ? "font-style:italic;" : "") + "'>" + text + "</div>";
    }
}
