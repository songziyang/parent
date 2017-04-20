package com.ydzb.account.service;


public interface IWmVipGradeService {

    /**
     * 用户VIP等级变更
     *
     * @param userId 用户ID
     *
     * @throws Exception
     */
    public void accountUserVipGrade(Long userId) throws Exception;

}
