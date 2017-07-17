package com.tzw.noah.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tzw.noah.db.SnsDBManager;
import com.tzw.noah.models.Group;
import com.tzw.noah.models.User;
import com.tzw.noah.net.Callback;
import com.tzw.noah.net.IMsg;
import com.tzw.noah.net.NetHelper;
import com.tzw.noah.net.Param;
import com.tzw.noah.net.WIRequest;
import com.tzw.noah.utils.NetWorkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by yzy on 2017/7/10.
 */

public class SnsManager {
    //添加关注
    //sns/attention
    Context mContext;
    SnsDBManager snsDBManager;

    private static Handler mdelivery;

    public SnsManager(Context mContext) {
        this.mContext = mContext;
        snsDBManager = new SnsDBManager(mContext);
        mdelivery = new Handler(Looper.getMainLooper());
    }


    private IMsg createImsg() {
        IMsg imsg = new IMsg();
        imsg.setSucceed(true);
        return imsg;
    }

    //添加关注
    public void snsAttention(int memberNo, Callback callback) {
        NetHelper.getInstance().snsAttention(memberNo, callback);
    }

    //取消关注
    //sns/unfollow/{memberNo}
    public void snsUnfollow(int memberNo, Callback callback) {
        NetHelper.getInstance().snsUnfollow(memberNo, callback);
    }

    //移除粉丝
    //sns/removeFans/{memberNo}
    public void snsRemoveFans(int memberNo, Callback callback) {
        NetHelper.getInstance().snsRemoveFans(memberNo, callback);
    }

    //添加黑名单
    //sns/blacklist
    public void snsBlacklist(int userid, Callback callback) {
        NetHelper.getInstance().snsBlacklist(userid, callback);
    }

    //移除黑名单
    //sns/removeBlacklist/{memberNo}
    public void snsRemoveBlacklist(int memberNo, Callback callback) {
        NetHelper.getInstance().snsRemoveBlacklist(memberNo, callback);
    }

    //获取我的好友列表
    //sns/friends
    public void snsFriends(final Callback callback) {
        if (NetWorkUtils.isNetworkAvailable(mContext))
            NetHelper.getInstance().snsFriends(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        callback.onAfter();
                        callback.onFailure(call, e);
                    }
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        //保存到本地数据库
                        if (iMsg.isSucceed()) {
                            List<User> userList = User.loadFriendList(iMsg);
                            iMsg.Data = userList;
                            snsDBManager.UpdateFriendList(userList);
                        }
                    } catch (Exception e) {

                    }
                    callback.onAfter();
                    callback.onResponse(iMsg);
                }
            });
        else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        final IMsg iMsg = createImsg();
                        iMsg.Data = snsDBManager.getSnsFriendList();
                        mdelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onAfter();
                                callback.onResponse(iMsg);
                            }
                        });
                    }
                }
            });
        }
    }

    //获取我的关注列表
    //sns/concern
    public void snsFollow(final Callback callback) {
        if (NetWorkUtils.isNetworkAvailable(mContext))
            NetHelper.getInstance().snsConcern(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        callback.onAfter();
                        callback.onFailure(call, e);
                    }
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        //保存到本地数据库
                        if (iMsg.isSucceed()) {
                            List<User> userList = User.loadFollowList(iMsg);
                            iMsg.Data = userList;
                            snsDBManager.UpdateFollowList(userList);
                        }
                    } catch (Exception e) {

                    }
                    callback.onAfter();
                    callback.onResponse(iMsg);
                }
            });
        else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        final IMsg iMsg = createImsg();
                        iMsg.Data = snsDBManager.getSnsFollowList();
                        mdelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onAfter();
                                callback.onResponse(iMsg);
                            }
                        });
                    }
                }
            });
        }
    }

    //获取我的粉丝列表
    //sns/fans
    public void snsFans(final Callback callback) {
        if (NetWorkUtils.isNetworkAvailable(mContext))
            NetHelper.getInstance().snsFans(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        callback.onAfter();
                        callback.onFailure(call, e);
                    }
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        //保存到本地数据库
                        if (iMsg.isSucceed()) {
                            List<User> userList = User.loadFansList(iMsg);
                            iMsg.Data = userList;
                            snsDBManager.UpdateFansList(userList);
                        }
                    } catch (Exception e) {

                    }
                    callback.onAfter();
                    callback.onResponse(iMsg);
                }
            });
        else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        final IMsg iMsg = createImsg();
                        iMsg.Data = snsDBManager.getSnsFansList();
                        mdelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onAfter();
                                callback.onResponse(iMsg);
                            }
                        });
                    }
                }
            });
        }
    }

    //获取我的黑名单列表
    //sns/blacks
    public void snsBlacks(final Callback callback) {
        if (NetWorkUtils.isNetworkAvailable(mContext))
            NetHelper.getInstance().snsBlacks(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        callback.onAfter();
                        callback.onFailure(call, e);
                    }
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        //保存到本地数据库
                        if (iMsg.isSucceed()) {
                            List<User> userList = User.loadBlackList(iMsg);
                            iMsg.Data = userList;
                            snsDBManager.UpdateBlacklist(userList);
                        }
                    } catch (Exception e) {

                    }
                    callback.onAfter();
                    callback.onResponse(iMsg);
                }
            });
        else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        final IMsg iMsg = createImsg();
                        iMsg.Data = snsDBManager.getSnsBlacklist();
                        mdelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onAfter();
                                callback.onResponse(iMsg);
                            }
                        });
                    }
                }
            });
        }
    }

    //获取我的好友,关注,粉丝,黑名单列表
    //sns/myList
    public void snsMyList(Callback callback) {
        NetHelper.getInstance().snsMyList(callback);
    }

    //获取附近的人,不用做缓存
    public void snsNearby(Callback callback) {
        NetHelper.getInstance().snsNearby(callback);
    }

    //获取推荐的人,不用做缓存
    public void snsRecommendUser(Callback callback) {
        NetHelper.getInstance().snsRecommendUser(callback);
    }

    public boolean isInBlacklist(int id) {
        return snsDBManager.isInBlacklist(id);
    }

    public void snsInfo(final User user, final Callback callback) {
//        if (NetWorkUtils.isNetworkAvailable(mContext))
        List<Param> body = new ArrayList<>();
        body.add(new Param("remarkName", user.remarkName));
        body.add(new Param("ifStar", user.ifStar));
        body.add(new Param("ifSeeHim", user.ifSeeHim));
        body.add(new Param("ifSeeMe", user.ifSeeMe));
        NetHelper.getInstance().snsInfo(user.memberNo, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onAfter();
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(IMsg iMsg) {
                try {
                    //保存到本地数据库
                    if (iMsg.isSucceed()) {
                        snsDBManager.updateUser(user);

                    }
                } catch (Exception e) {

                }
                callback.onAfter();
                callback.onResponse(iMsg);
            }
        });
    }

    public void snsDetail(final User user, final Callback callback) {
        if (NetWorkUtils.isNetworkAvailable(mContext))
            NetHelper.getInstance().snsDetails2(user.memberNo, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        callback.onAfter();
                        callback.onFailure(call, e);
                    }
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        //保存到本地数据库
                        if (iMsg.isSucceed()) {
                            User u = User.load(iMsg);
                            snsDBManager.updateUser(u);
                            iMsg.Data = u;
                        }
                    } catch (Exception e) {

                    }
                    callback.onAfter();
                    callback.onResponse(iMsg);
                }
            });
        else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        final IMsg iMsg = createImsg();
                        iMsg.Data = snsDBManager.getUserDetail(user.memberNo);
                        mdelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onAfter();
                                callback.onResponse(iMsg);
                            }
                        });
                    }
                }
            });
        }
    }


    ///////////////////////////////////////////////////////////////////
    //////////////////////// 群组接口  /////////////////////////////////
    ///////////////////////////////////////////////////////////////////


    //创建多人会话
    //sns/createDiscuss
    public void snsCreateDiscuss(List<String> ids, Callback callback) {
        NetHelper.getInstance().snsCreateDiscuss(ids, callback);
    }

    //获取我的多人会话和群列表
    //sns/groups
    public void snsGroups(final Callback callback) {
        if (NetWorkUtils.isNetworkAvailable(mContext))
            NetHelper.getInstance().snsGroups(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) {
                        callback.onAfter();
                        callback.onFailure(call, e);
                    }
                }

                @Override
                public void onResponse(IMsg iMsg) {
                    try {
                        //保存到本地数据库
                        if (iMsg.isSucceed()) {
                            List<Group> userList = Group.loadDiscussList(iMsg);
                            iMsg.Data = userList;
                            //TODO 保存本地数据库
                            //snsDBManager.UpdateFriendList(userList);
                        }
                    } catch (Exception e) {

                    }
                    callback.onAfter();
                    callback.onResponse(iMsg);
                }
            });
        else {
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    if (callback != null) {
//                        final IMsg iMsg = createImsg();
//                        iMsg.Data = snsDBManager.getSnsFriendList();
//                        mdelivery.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.onAfter();
//                                callback.onResponse(iMsg);
//                            }
//                        });
//                    }
//                }
//            });
        }
    }

    //获取群成员（多人会话、群组）
    //sns/getMembers
    public void snsGetMembers(int groupId, Callback callback) {
        NetHelper.getInstance().snsGetMembers(groupId, callback);
    }

    //移交群主（多人会话、群组）
    //sns/transfer/
    public void snsTransfer(int groupId, int memberNo, Callback callback) {
        NetHelper.getInstance().snsTransfer(groupId, memberNo, callback);
    }

    //获取群组的类别
    //sns/groupType
    public void snsGroupType(Callback callback) {
        NetHelper.getInstance().snsGroupType(callback);
    }

    //创建群组
    //sns/createGroup
    public void snsCreateGroup(List<Param> body, Callback callback) {
        NetHelper.getInstance().snsCreateGroup(body, callback);
    }

    //邀请加群
    //sns/addUsersToGroup
    public void snsAddUsersToGroup(int groupId, List<String> ids, Callback callback) {
        NetHelper.getInstance().snsAddUsersToGroup(groupId, ids, callback);
    }

    // 移除群成员
    //sns/kickUsersFromGroup
    public void snsKickUsersFromGroup(int groupId, List<String> ids, Callback callback) {
        NetHelper.getInstance().snsKickUsersFromGroup(groupId, ids, callback);
    }

    // 修改群昵称
    //sns/updateNickToGroup
    public void snsUpdateNickToGroup(int groupId, List<Param> body, Callback callback) {
        NetHelper.getInstance().snsUpdateNickToGroup(groupId, body, callback);
    }

    //群组:添加管理员
    //sns/addManagersToGroup
    public void snsAddManagersToGroup(int groupId, List<String> ids, Callback callback) {
        NetHelper.getInstance().snsAddManagersToGroup(groupId, ids, callback);
    }

    // 移除群管理员
    //sns/removeManagersFromGroup
    public void snsRemoveManagersFromGroup(int groupId, List<String> ids, Callback callback) {
        NetHelper.getInstance().snsRemoveManagersFromGroup(groupId, ids, callback);
    }

    // 更新群资料
    //sns/updateGroupInfo
    public void snsUpdateGroupInfo(int groupId, List<Param> body, Callback callback) {
        NetHelper.getInstance().snsUpdateGroupInfo(groupId, body, callback);
    }

    //群组设置(是否允许成员邀请、是否允许匿名聊天、加群验证方式)
    //sns/settingOfGroup
    public void snsSettingOfGroup(int groupId, List<Param> body, Callback callback) {
        NetHelper.getInstance().snsSettingOfGroup(groupId, body, callback);
    }

}
