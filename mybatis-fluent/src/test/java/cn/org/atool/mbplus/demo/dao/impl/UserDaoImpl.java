package cn.org.atool.mbplus.demo.dao.impl;

import cn.org.atool.mbplus.demo.dao.base.UserBaseDao;
import cn.org.atool.mbplus.demo.dao.intf.UserDao;
import cn.org.atool.mbplus.demo.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author generate code
 * @ClassName UserDaoImpl
 * @Description UserEntity数据操作实现类
 */
@Repository
public class UserDaoImpl extends UserBaseDao implements UserDao {
    @Override
    public int count(String userName) {
        return super.query()
                .and.userName.eq(userName)
                .doIt(super::count);
    }

    @Override
    public List<String> selectFields(Long... ids) {
        return super.selectFields(
                super.query().and.id.in(ids),
                UserEntity::getUserName
        );
    }

    @Override
    public List<UserEntity> selectList(Long... ids) {
        return super.query().and.id.in(ids)
                .doIt(super::selectList);
    }

    @Override
    public List<String> selectObjs(Long... ids) {
        return super.selectObjs(
                super.query().select(Column.id, Column.user_name).and.id.in(ids),
                (map) -> (String) map.get(Column.user_name)
        );
    }

    @Override
    public UserEntity selectOne(String likeName) {
        return super.query().and.userName.like(likeName).doIt(super::selectOne);
    }

    @Override
    public String selectOne(long id) {
        return super.selectOne(super.query().and.id.eq(id), UserEntity::getUserName);
    }

    @Override
    public void deleteByQuery(String username) {
        super.query().and.userName.eq(username).doIt(super::deleteByQuery);
    }

    @Override
    public void deleteByQuery(String... userNames) {
        super.query().and.userName.in(userNames).doIt(super::deleteByQuery);
    }

    @Override
    public void updateUserNameById(String newUserName, long id) {
        super.update().set.userName.is(newUserName).and.id.eq(id).doIt(super::update);
    }
}