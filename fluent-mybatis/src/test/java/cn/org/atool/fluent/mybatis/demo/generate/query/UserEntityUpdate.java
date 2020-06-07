package cn.org.atool.fluent.mybatis.demo.generate.query;

import cn.org.atool.fluent.mybatis.condition.interfaces.IEntityUpdate;
import cn.org.atool.fluent.mybatis.condition.base.AbstractWrapper;
import cn.org.atool.fluent.mybatis.condition.base.MergeSegments;
import cn.org.atool.fluent.mybatis.util.Constants;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import cn.org.atool.fluent.mybatis.demo.generate.entity.UserEntity;
import cn.org.atool.fluent.mybatis.demo.generate.mapping.UserMP;
import cn.org.atool.fluent.mybatis.demo.generate.query.UserEntityWrapperHelper.And;
import cn.org.atool.fluent.mybatis.demo.generate.query.UserEntityWrapperHelper.Set;
import cn.org.atool.fluent.mybatis.demo.generate.query.UserEntityWrapperHelper.UpdateOrder;

import static cn.org.atool.fluent.mybatis.util.MybatisUtil.isEmpty;
import static cn.org.atool.fluent.mybatis.util.MybatisUtil.isNotEmpty;

/**
 * @ClassName UserEntityUpdate
 * @Description UserEntity更新设置
 *
 * @author generate code
 */
public class UserEntityUpdate extends AbstractWrapper<UserEntity, String, UserEntityUpdate>
    implements IEntityUpdate<UserEntityUpdate> {
    /**
    * SQL 更新字段内容，例如：name='1',age=2
    */
    private final List<String> sqlSet;

    private final Map<String, Object> updates = new HashMap<>();

    public final And<UserEntityUpdate> and = new And<>(this);

    public final Set set = new Set(this);

    public final UpdateOrder orderBy = new UpdateOrder(this);

    public UserEntityUpdate(){
        // 如果无参构造函数，请注意实体 NULL 情况 SET 必须有否则 SQL 异常
        this(null);
    }

    public UserEntityUpdate(UserEntity entity) {
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList<>();
    }

    private UserEntityUpdate(UserEntity entity, List<String> sqlSet, AtomicInteger paramNameSeq,
        Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments) {
        super.setEntity(entity);
        this.sqlSet = sqlSet;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
    }

    @Override
    public String getSqlSet() {
        if (isEmpty(sqlSet)) {
            return null;
        }
        return String.join(Constants.COMMA, sqlSet);
    }

    @Override
    public Map<String, String> getProperty2Column() {
        return UserMP.Property2Column;
    }

    @Override
    public Map<String, ? extends Object> getUpdates() {
        return this.updates;
    }

    @Override
    public UserEntityUpdate set(boolean condition, String column, Object value){
        if(condition){
            this.updates.put(column, value);
        }
        return this;
    }

    @Override
    public UserEntityUpdate setSql(boolean condition, String sql) {
        if (condition && isNotEmpty(sql)) {
            sqlSet.add(sql);
        }
        return this;
    }

    @Override
    protected UserEntityUpdate instance() {
        return new UserEntityUpdate(entity, sqlSet, paramNameSeq, paramNameValuePairs, new MergeSegments());
    }

    @Override
    public UserEntityUpdate limit(int from, int limit){
        super.last(String.format("limit %d, %d", from, limit));
        return this;
    }

    @Override
    public UserEntityUpdate limit(int limit){
        super.last(String.format("limit %d", limit));
        return this;
    }
}