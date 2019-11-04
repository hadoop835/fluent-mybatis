package cn.org.atool.mbplus.demo;

import cn.org.atool.mbplus.base.IEntity;
import cn.org.atool.mbplus.base.IEntityQuery;
import cn.org.atool.mbplus.base.IEntityUpdate;
import cn.org.atool.mbplus.base.IMapperDao;

/**
 * base dao 自定义接口
 *
 * @param <E>
 * @param <Q>
 * @param <U>
 */
public interface MyCustomerInterface<E extends IEntity, Q extends IEntityQuery<Q, E>, U extends IEntityUpdate<U>>
        extends IMapperDao<E, Q, U> {
    default Q defaultQuery() {
        return this.query().eq("is_deleted", false);
    }
}
