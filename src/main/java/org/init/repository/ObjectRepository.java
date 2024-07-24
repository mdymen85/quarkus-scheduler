package org.init.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface ObjectRepository extends PanacheRepository<TestObject> {

    void process();

}
