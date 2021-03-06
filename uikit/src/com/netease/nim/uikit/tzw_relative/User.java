package com.netease.nim.uikit.tzw_relative;

//import com.google.gson.reflect.TypeToken;
//import com.tzw.noah.db.MyField;
//import com.tzw.noah.net.IMsg;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yzy on 2017/6/17.
 */

public class User implements Serializable {

//    public int memberId;
    //@MyField(name = "id")
    public int memberNo;
    //@MyField
    public String memberMobile = "";
    //@MyField
    public String memberNickName = "";
    //@MyField
    public String remarkName = "";

//    public String memberPasswd = "";
    //@MyField
    public String memberHeadPic = "";
    //@MyField
    public int memberSex;//(0男、1女)
    //@MyField
    public int memberLevel;
    //@MyField
    public String memberInterest = "";
    //@MyField
    public String memberCharacter = "";
    //@MyField
    public String memberWork = "";
    //@MyField
    public int areaId;
    //@MyField
    public String memberIntroduce = "";
    //@MyField
    public String memberBirthday = "";

//    public String weChatAuthAccount = "";
//    public String qqAuthAccount = "";
//    public String weiboAuthAccount = "";

    //@MyField
    public int growth;
    //@MyField
    public int totalScore;
    //@MyField
    public int score;
    //@MyField
    public double totalBonus;
    //@MyField
    public double bonus;
    //@MyField
    public int netEaseId;
    //@MyField
    public String netEaseToken = "";

//    public int ifEnabled;//(0启用、1禁用)
//    public String passwdUpdateTime = "";
//    public String updateTime = "";

    //@MyField
    public String createTime = "";
    //@MyField
    public int ifStar = 1;
    //@MyField
    public int ifSeeHim = 0;
    //@MyField
    public int ifSeeMe = 0;

//    public boolean isSelf = false;
//    public boolean isAttention = false;
//    public boolean isFans = false;
//    public boolean isBlacklist = false;
//    public String nameFirstChar = "";
//    public List<Character> namePingyin;

    public String getName() {
        if (remarkName.isEmpty())
            return memberNickName;
        else
            return remarkName;
    }

//    public static User load(IMsg iMsg) {
//        return iMsg.getModel("detailsRObj", new User());
//    }


//    public int type = 0;
//
//    public String getRelative() {
//        if (isSelf)
//            return User.RelativeType.Myself;
//        if (isBlacklist) {
//            return User.RelativeType.Blacklist;
//        } else {
//            if (isFans && isAttention) {
//                return User.RelativeType.Friend;
//            } else if (isAttention) {
//                return User.RelativeType.Fowllow;
//            } else if (isFans) {
//                return User.RelativeType.Fans;
//            } else {
//                return User.RelativeType.Stranger;
//            }
//        }
//    }

//    public static class Type {
//        public static int Person = 0;
//        public static int Group = 1;
//    }

//    public static class RelativeType {
//        public static String Stranger = "陌生人";
//        public static String Friend = "好友";
//        public static String Fowllow = "关注";
//        public static String Fans = "粉丝";
//        public static String Blacklist = "黑名单";
//        public static String Myself = "自己";
//    }


//    public static List<User> loadFriendList(IMsg iMsg) {
//        IMsg iMsg2 = iMsg.getJsonObject("");
//        return iMsg.getModelList("friendsRObj", new TypeToken<List<User>>() {
//        }.getType());
////        return iMsg.getModelList("friendsRObj",new TypeToken<User>(){}.getType());
//    }
//
//    public static List<User> loadFansList(IMsg iMsg) {
//        return iMsg.getModelList("fansRObj", new TypeToken<List<User>>() {
//        }.getType());
//    }
//
//    public static List<User> loadFollowList(IMsg iMsg) {
//        return iMsg.getModelList("concernRObj", new TypeToken<List<User>>() {
//        }.getType());
//    }
//
//    public static List<User> loadBlackList(IMsg iMsg) {
//        return iMsg.getModelList("blacksRObj", new TypeToken<List<User>>() {
//        }.getType());
//    }
//
//    public static List<User> loadNearby(IMsg iMsg) {
//        return iMsg.getModelList("nearbyRObj", new TypeToken<List<User>>() {
//        }.getType());
//    }
//
//    public static List<User> loadRecommendUser(IMsg iMsg) {
//        return iMsg.getModelList("recommendUserRObj", new TypeToken<List<User>>() {
//        }.getType());
//    }
//
//
//    public static List<User> loadMyList_Friend(IMsg iMsg) {
//        IMsg iMsg2 = iMsg.getJsonObject("");
//        return iMsg.getModelList("blacksRObj", new TypeToken<List<User>>() {
//        }.getType());
//    }
}
