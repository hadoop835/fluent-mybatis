package cn.org.atool.fluent.mybatis.formapi;

import cn.org.atool.fluent.mybatis.formservice.model.HomeAddress;
import cn.org.atool.fluent.mybatis.generator.ATM;
import cn.org.atool.fluent.mybatis.generator.shared2.dao.intf.HomeAddressDao;
import cn.org.atool.fluent.mybatis.generator.shared2.entity.HomeAddressEntity;
import cn.org.atool.fluent.mybatis.test1.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeAddressTest extends BaseTest {
    @Autowired
    HomeAddressDao dao;

    @Test
    void test() {
        ATM.dataMap.homeAddress.table().clean();
        dao.save(HomeAddressEntity.builder()
            .address("oooyyyyxxx")
            .city("123")
            .studentId(0L)
            .district("-=0-9")
            .build());
        HomeAddress address = dao.findHomeAddress("ooo");
        want.object(address).eqReflect(new HomeAddress("oooyyyyxxx", "123", "-=0-9"));
    }
}