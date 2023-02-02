package priv.pront.yygh.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.user.UserInfo;
import priv.pront.yygh.vo.user.LoginVo;
import priv.pront.yygh.vo.user.UserAuthVo;
import priv.pront.yygh.vo.user.UserInfoQueryVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 09:57
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> loginUser(LoginVo loginVo);

    /**
     * 判断用户是否存在
     *
     * @return
     */
    UserInfo selectWxInfoOpenId(String openid);

    /**
     * @param userId     用户的id
     * @param userAuthVo 认证数据的vo对象
     */
    void serAuth(Long userId, UserAuthVo userAuthVo);

    /**
     * 用户列表查询带分页
     *
     * @param pageParam
     * @param userInfoQueryVo
     * @return
     */
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    /**
     * 用户锁定接口
     *
     * @param userId
     * @param status
     */
    void lock(Long userId, Integer status);

    /**
     * 展示用户详情信息
     *
     * @param userId
     * @return
     */
    Map<String, Object> show(Long userId);

    /**
     * 审批用户
     *
     * @param userId
     * @param authStatus
     */
    void approval(Long userId, Integer authStatus);
}
