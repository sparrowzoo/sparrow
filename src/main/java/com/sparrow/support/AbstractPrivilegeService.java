package com.sparrow.support;

import com.sparrow.constant.User;
import com.sparrow.protocol.PrivilegeSupport;

/**
 * @author: zh_harry@163.com
 * @date: 2019-04-06 17:54
 * @description:
 */
public abstract class AbstractPrivilegeService implements PrivilegeSupport {
    @Override
    public boolean accessible(Long writer, Long currentUserId) {
        // 当前是游客则无操作权限
        if (User.VISITOR_ID.equals(currentUserId)) {
            return false;
        }
        // 帖子作者是当前用户id
        if (writer.equals(currentUserId)) {
            return true;
        }
        return false;
    }
}
