package cn.org.atool.mbplus.test.basedao;

import cn.org.atool.mbplus.demo.dao.intf.UserDao;
import cn.org.atool.mbplus.demo.dm.table.UserTableMap;
import cn.org.atool.mbplus.demo.entity.UserEntity;
import cn.org.atool.mbplus.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author darui.wu
 * @create 2019/10/29 9:36 下午
 */
public class DeleteByEntityIdsTest extends BaseTest {
    @Autowired
    private UserDao dao;

    @Test
    public void test_deleteByEntityIds() throws Exception {
        db.table(t_user).clean().insert(new UserTableMap(10).init());
        dao.deleteByEntityIds(Arrays.asList(new UserEntity().setId(1L), new UserEntity().setId(5L)));
        db.sqlList().wantFirstSql().eq("DELETE FROM t_user WHERE id IN ( ? , ? )");
        db.table(t_user).count().isEqualTo(8);
    }
}
