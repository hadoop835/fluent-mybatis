package cn.org.atool.fluent.mybatis.base.entity;

import cn.org.atool.fluent.mybatis.base.IEntity;

import java.util.List;

import static cn.org.atool.fluent.mybatis.mapper.FluentConst.*;

/**
 * 只需要Entity属性就可以执行的数据操作方法
 *
 * @author darui.wu
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface IRichEntity extends IRich, IEntity {
    /**
     * 持久化entity到数据库, 调用 EntityMapper.insert(Entity)方法
     *
     * @param <E> IEntity类型
     * @return ignore
     */
    default <E extends IEntity> E save() {
        this.invoke(RE_Save, false);
        return (E) this;
    }

    /**
     * 按entity的主键更新entity非空字段, 调用 EntityMapper.updateById(id)方法
     *
     * @param <E> IEntity类型
     * @return ignore
     */
    default <E extends IEntity> E updateById() {
        if (this.findPk() == null) {
            throw new RuntimeException("the primary of entity can't be null.");
        }
        this.invoke(RE_UpdateById, false);
        return (E) this;
    }

    /**
     * 根据id查找entity, 调用 EntityMapper.findById(id)方法
     *
     * @param <E> IEntity类型
     * @return ignore
     */
    default <E extends IEntity> E findById() {
        if (this.findPk() == null) {
            throw new RuntimeException("the primary of entity can't be null.");
        }
        IEntity entity = this.invoke(RE_FindById, false);
        return (E) entity;
    }

    /**
     * 物理删除entity, 调用 EntityMapper.deleteById(id)方法
     */
    default void deleteById() {
        if (this.findPk() == null) {
            throw new RuntimeException("the primary of entity can't be null.");
        }
        this.invoke(RE_DeleteById, false);
    }

    /**
     * 逻辑删除entity, 调用 EntityMapper.logicDeleteById(id)方法
     */
    default void logicDeleteById() {
        if (this.findPk() == null) {
            throw new RuntimeException("the primary of entity can't be null.");
        }
        this.invoke(RE_LogicDeleteById, false);
    }

    /**
     * entity非空字段作为条件查询列表, 调用 EntityMapper.listByMap(map)方法
     *
     * @param <E> IEntity类型
     * @return ignore
     */
    default <E extends IEntity> List<E> listByNotNull() {
        return this.<List>invoke(RE_ListByNotNull, false);
    }
}