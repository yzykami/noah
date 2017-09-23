package com.tzw.noah.models;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tzw.noah.MainActivity;
import com.tzw.noah.logger.Log;
import com.tzw.noah.net.IMsg;
import com.tzw.noah.sdk.MediaManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzy on 2017/8/30.
 */

public class MediaArticle implements Serializable {

    public int articleId;
    public String webClassIds = "";
    public String articleTitle = "";
    public int articleType;
    public String webArticleImage = "";
    public String appArticleImage = "";
    public String h5ArticleImage = "";
    public Object articleContent;
    public String articleAbstract = "";
    public String keyWordIds = "";
    public Object keywords;// = new ArrayList<>();
    //    public Object liker = null;
    public String articleAuthor = "";
    public int ifOriginal;
    public String articleSource = "";
    public int ifAttachment;
    public int ifVideo;
    public int ifWatermark;
    public int ifHot;
    public int ifRecommend;
    public int ifTop;
    public int readNumber;
    public int praiseNumber;
    public int keepNumber;
    public int articleState;
    public int editorType;
    public int editorId;
    public String createTime = "";
    public List<MediaComment> articleCommentObj = new ArrayList<>();
    public List<MediaLike> articleEvaluateObj = new ArrayList<>();
    public List<MediaArticle> relatedArticlesObj;
    public boolean isArticleKeep;
    public int isArticleEvaluate;
    public int evaluateValue;
    public int articleCommentSum;
    public int appListShowType;
    public int articleContentImageNum;

    public String tag;

    public String getAuthor() {
        String author = "";
        if (ifOriginal == 0)
            author = articleSource;
        //如果非原创以及source为空
        if (TextUtils.isEmpty(author))
            author = articleAuthor;

        if (TextUtils.isEmpty(author))
            author = "作者不详";

        return author;
    }

    //文章详情内容分段
    public int TYPE = 0;
    //默认为列表的文章类型
    public final static int TYPE_LIST = 0;
    public final static int TYPE_TITLE = 1;
    public final static int TYPE_WEB_CONTENT = 2;
    public final static int TYPE_COMMENT = 3;
    public final static int TYPE_KEYWORD = 4;
    public final static int TYPE_LIKE = 5;
    public final static int TYPE_ADVERTISE = 6;
    public final static int TYPE_TAG = 7;
    public final static int TYPE_SAFA = 8;
    public final static int TYPE_DIVIDER = 9;

    public boolean isTitle() {
        return TYPE == TYPE_TITLE;
    }

    public boolean isWebContent() {
        return TYPE == TYPE_WEB_CONTENT;
    }

    public boolean isComment() {
        return TYPE == TYPE_COMMENT;
    }

    public boolean isKeyword() {
        return TYPE == TYPE_KEYWORD;
    }

    public boolean isLike() {
        return TYPE == TYPE_LIKE;
    }

    public boolean isTag() {
        return TYPE == TYPE_TAG;
    }

    public boolean isAdvertise() {
        return TYPE == TYPE_ADVERTISE;
    }

    public boolean isSafa() {
        return TYPE == TYPE_SAFA;
    }

    public boolean isDivider() {
        return TYPE == TYPE_DIVIDER;
    }

    public MediaArticle makeTitle() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.articleTitle = this.articleTitle;
        ma.articleAuthor = this.articleAuthor;
        ma.createTime = this.createTime;
        ma.TYPE = TYPE_TITLE;
        return ma;
    }

    public MediaArticle makeContent() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.articleContent = this.articleContent;
        ma.TYPE = TYPE_WEB_CONTENT;
        return ma;
    }

    public MediaArticle makeComment() {
        MediaArticle ma = new MediaArticle();
        ma.articleCommentObj = this.articleCommentObj;
        ma.articleId = articleId;
        ma.TYPE = TYPE_COMMENT;
        return ma;
    }

    public MediaArticle makeKeyword() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.keywords = this.keywords;
        ma.TYPE = TYPE_KEYWORD;
        return ma;
    }

    public MediaArticle makeLiker() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.articleEvaluateObj = this.articleEvaluateObj;
        ma.praiseNumber = this.praiseNumber;
        ma.isArticleEvaluate = this.isArticleEvaluate;
        ma.TYPE = TYPE_LIKE;
        return ma;
    }

    public MediaArticle makeAdvertise() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.TYPE = TYPE_ADVERTISE;
        return ma;
    }

    public MediaArticle makeTag(String tag) {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.tag = tag;
        ma.TYPE = TYPE_TAG;
        return ma;
    }

    public MediaArticle makeSafa() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.TYPE = TYPE_SAFA;
        return ma;
    }

    public MediaArticle makeDivider() {
        MediaArticle ma = new MediaArticle();
        ma.articleId = articleId;
        ma.TYPE = TYPE_DIVIDER;
        return ma;
    }

    public List<String> getKeywords() {
        if (keywords != null && keywords instanceof ArrayList) {
            return (List<String>) keywords;
        }
        return new ArrayList<String>();
    }

    public String getContentString() {
        if (articleContent != null && articleContent instanceof String)
            return articleContent.toString();
        return "";
    }

    public List<GalleryArticle> getContentList() {
        if (articleContent != null && articleContent instanceof ArrayList) {
            Gson gson = new GsonBuilder().create();
            try {
                //创建一个JsonParser
                JsonParser parser = new JsonParser();
                //通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
                JsonElement el = parser.parse(gson.toJson(articleContent));

                List<GalleryArticle> list = gson.fromJson(el, new TypeToken<List<GalleryArticle>>() {
                }.getType());
                return list;
            } catch (Exception e) {
                Log.log("IMSG", e);
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    public static List<MediaArticle> loadList(IMsg iMsg) {
        return iMsg.getModelList("articleListRObj", new TypeToken<List<MediaArticle>>() {
        }.getType());
    }

    public static MediaArticle load(IMsg iMsg) {
        IMsg iMsg2 = iMsg.getJsonObject("articlesRObj");
        MediaArticle ma = iMsg2.getModel("detailsObj", new TypeToken<MediaArticle>() {
        }.getType());
        List<MediaComment> mcs = iMsg.getJsonObject("articlesRObj").getModelList("articleCommentObj", new TypeToken<List<MediaComment>>() {
        }.getType());
        ma.articleCommentObj = mcs;
        List<MediaLike> mls = iMsg.getJsonObject("articlesRObj").getModelList("articleEvaluateObj", new TypeToken<List<MediaLike>>() {
        }.getType());
        ma.articleEvaluateObj = mls;
        List<MediaArticle> mla = iMsg.getJsonObject("articlesRObj").getModelList("relatedArticlesObj", new TypeToken<List<MediaArticle>>() {
        }.getType());
        ma.relatedArticlesObj = mla;
        try {
            int isArticleEvaluate = iMsg.result.getAsJsonObject().getAsJsonObject("articlesRObj").getAsJsonPrimitive("isArticleEvaluate").getAsInt();
            boolean isArticleKeep = iMsg.result.getAsJsonObject().getAsJsonObject("articlesRObj").getAsJsonPrimitive("isArticleKeep").getAsBoolean();
            ma.isArticleEvaluate = isArticleEvaluate;
            ma.isArticleKeep = isArticleKeep;
        } catch (Exception e) {
            Log.log("mediaArticle", e);
        }
        return ma;
    }


    /*
    List Item
     */


    public int LIST_TYPE;
    public final static int LIST_TYPE_TXT = 1131;
    public final static int LIST_TYPE_PIC_RL = 1132;
    public final static int LIST_TYPE_PIC_UD = 1134;
    public final static int LIST_TYPE_PIC_UD_BIG = 1133;
    public final static int LIST_TYPE_VIEWPAGER = 3;
    public final static int LIST_TYPE_KEYWORD = 4;
    public final static int LIST_TYPE_LIKE = 5;
    public final static int LIST_TYPE_ADVERTISE = 6;
    public final static int LIST_TYPE_TAG = 7;

    public boolean isListTxt() {
        if (TYPE != TYPE_LIST)
            return false;
//        return LIST_TYPE_TXT == getListType();
        return appListShowType == LIST_TYPE_TXT;
    }

    public boolean isListPicRL() {
        if (TYPE != TYPE_LIST)
            return false;
        return appListShowType == LIST_TYPE_PIC_RL;
    }

    public boolean isListPicUD() {
        if (TYPE != TYPE_LIST)
            return false;
        return appListShowType == LIST_TYPE_PIC_UD;
    }

    public boolean isListPicUDBig() {
        if (TYPE != TYPE_LIST)
            return false;
        return appListShowType == LIST_TYPE_PIC_UD_BIG;
    }

    public boolean isListViewpager() {
        if (TYPE != TYPE_LIST)
            return false;
        return LIST_TYPE_VIEWPAGER == LIST_TYPE;
    }

//    public boolean isComment() {
//        return TYPE == TYPE_COMMENT;
//    }
//
//    public boolean isKeyword() {
//        return TYPE == TYPE_KEYWORD;
//    }
//
//    public boolean isLike() {
//        return TYPE == TYPE_LIKE;
//    }
//
//    public boolean isTag() {
//        return TYPE == TYPE_TAG;
//    }
//
//    public boolean isAdvertise() {
//        return TYPE == TYPE_ADVERTISE;
//    }


    public int getListType() {
        if (TextUtils.isEmpty(this.appArticleImage)) {
            return LIST_TYPE_TXT;
        } else {
            return LIST_TYPE_PIC_RL;
        }
    }

//    public MediaArticle makeTitle() {
//        MediaArticle ma = new MediaArticle();
//        ma.articleTitle = this.articleTitle;
//        ma.createTime = this.createTime;
//        ma.TYPE = TYPE_TITLE;
//        return ma;
//    }
//
//    public MediaArticle makeContent() {
//        MediaArticle ma = new MediaArticle();
//        ma.articleContent = this.articleContent;
//        ma.TYPE = TYPE_WEB_CONTENT;
//        return ma;
//    }
//
//    public MediaArticle makeKeyword() {
//        MediaArticle ma = new MediaArticle();
//        ma.keywords = this.keywords;
//        ma.TYPE = TYPE_KEYWORD;
//        return ma;
//    }
//
//    public MediaArticle makeLiker() {
//        MediaArticle ma = new MediaArticle();
//        ma.liker = this.liker;
//        ma.TYPE = TYPE_LIKE;
//        return ma;
//    }
//
//    public MediaArticle makeAdvertise() {
//        MediaArticle ma = new MediaArticle();
//        ma.liker = this.liker;
//        ma.TYPE = TYPE_ADVERTISE;
//        return ma;
//    }
//
//    public MediaArticle makeTag(String tag) {
//        MediaArticle ma = new MediaArticle();
//        ma.tag = tag;
//        ma.TYPE = TYPE_TAG;
//        return ma;
//    }

    public int ARTICLE_TYPE;
    public final static int ARTICLE_TYPE_ARTICLE = 1124;
    public final static int ARTICLE_TYPE_PICGALLERY = 1126;
    public final static int ARTICLE_TYPE_VIDEO = 1125;

    public boolean isArticleTypeArticle() {
        return articleType == ARTICLE_TYPE_ARTICLE;
    }

    public boolean isArticleTypPicGallery() {
        return articleType == ARTICLE_TYPE_PICGALLERY;
    }

    public boolean isArticleTypVideo() {
        return articleType == ARTICLE_TYPE_VIDEO;
    }


    public class GalleryArticle implements Serializable {
        public String image = "";
        public String text = "";
    }
}
