package com.newbanker.fac;

import com.newbanker.fac.undertow.model.Animal;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.Reflection;
import com.hankcs.hanlp.HanLP;

import java.util.List;

/**
 * @Author roger
 * @Date $ $
 */
public class HelloWords {

    public static void main(String[] args) {
        String content = "泛娱乐帝国布局现颓势？\n" +
                "　　\n" +
                "进入2019年，王思聪旗下的公司频频传来坏消息。\n" +
                "　　\n" +
                "今年3月初，熊猫直播在官方微博上宣布将关闭服务器，证实了此前有关熊猫直播倒闭的传言。天眼查数据显示，今年7月1日和8月2日，熊猫互娱被法院两次列为失信被执行人，也就是俗称的“老赖”。\n" +
                "　　\n" +
                "今年下半年以来，其亲手创办的香蕉计划旗下多家公司股权陆续遭到冻结。香蕉计划旗下包括游戏、体育、经纪演出、影视、音乐等多个子公司。从司法协助信息来看，目前上海香蕉计划娱乐文化有限公司、上海香蕉影视计划文化发展有限公司、上海香蕉计划电子游戏有限公司三家公司均有股权遭到冻结，冻结股权价值超过7000万元。\n" +
                "　　\n" +
                "王思聪持股80%的上海水晶荔枝娱乐文化有限公司(下称“水晶荔枝娱乐”)则在今年7月15日股权遭到冻结，冻结日期至2022年7月14日，王思聪所持800万元股权遭到冻结。天眼查信息显示，水晶荔枝娱乐由王思聪和演员林更新共同创立。\n" +
                "10月17日，天眼查数据显示，由王思聪担任董事长并持股100%的北京普思投资有限公司股权遭到上海市宝山区人民法院冻结，具体冻结数额不祥，冻结日期至2022年10月14日。\n" +
                "　　\n" +
                "据中新经纬记者计算，截至发稿，王思聪名下冻结股权价值合计已经超过8445万元。根据胡润百富榜的最新数据，王健林家族以1200 亿元财富排名第九，不到一亿元的股权冻结相比于整个家族的财富仅仅是九牛一毛，更不及王健林的“一个小目标”。不过，对于王思聪来说，要想在泛娱乐领域做出一番成绩，真正撕下“富二代”的标签，需要的不仅是“先见之明”或“风口一日游”，更是所投企业长期稳定可持续的发展。\n" +
                "　　\n" +
                "\n" +
                "天眼查信息显示，王思聪在20家公司担任法人，在33家公司担任股东，在34家公司担任高管，对108家企业拥有实际控制权。";
        List<String> keywordList = HanLP.extractKeyword(content, 10);
        System.out.println(keywordList);

        List<String> phraseList = HanLP.extractPhrase(content, 20);

        System.out.println(phraseList);
        phraseList.removeIf(x -> {
            for (String keyword : keywordList) {
                if (x.contains(keyword))
                    return false;
            }
            return true;
        });

        System.out.println(phraseList);
    }
}
