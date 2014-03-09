<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">


    <context:annotation-config/>

    <context:property-placeholder location="jsqltuner-persistence.properties"/>

    <context:component-scan base-package="pl.piotrsukiennik.tuner.persistence,pl.piotrsukiennik.tuner.persistence.impl">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
    </context:component-scan>

    <!--s

    <bean class="org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor"/>

    <bean class="org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor"/>


    <bean id="jsqlTunerPersistenceUnitManager"
          class="org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager">
        <property name="persistenceXmlLocations">
            <list>
                <value>classpath*:META-INF/persistence.xml</value>
            </list>
        </property>
        <property name="defaultDataSource" ref="jsqlTunerDataSource"/>
    </bean>


    <bean id="jsqlTunerEntityManagerFactory"
          class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="persistenceUnitManager" ref="jsqlTunerPersistenceUnitManager"/>
        <property name="jpaProperties">
            <value>
                hibernate.dialect=${jsqltuner.hibernate.dialect}
                hibernate.hbm2ddl.auto=update
                hibernate.show_sql=false
                hibernate.format_sql=false
                hibernate.jdbc.batch_size=20
            </value>
        </property>
    </bean>
-->

    <bean id="jsqlTunerDataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource"
          autowire-candidate="false">
        <property name="driverClassName" value="${jsqltuner.db.driver}"/>
        <property name="url" value="${jsqltuner.db.url}"/>
        <property name="username" value="${jsqltuner.db.username}"/>
        <property name="password" value="${jsqltuner.db.password}"/>
    </bean>


    <bean id="jsqlTunerSessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean"
          autowire-candidate="false">
        <property name="dataSource" ref="jsqlTunerDataSource"/>
        <property name="packagesToScan">
            <list>
                <value>pl.piotrsukiennik.tuner.model</value>
                <value>pl.piotrsukiennik.tuner.model.*</value>
                <value>pl.piotrsukiennik.tuner.model.*.*</value>
            </list>
        </property>
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">${jsqltuner.hibernate.dialect}</prop>
                <prop key="hibernate.show_sql">false</prop>
                <prop key="hibernate.hbm2ddl.auto">create-drop</prop>
            </props>
        </property>
    </bean>
    <bean id="jsqlTunerTransactionManager" class="org.springframework.orm.hibernate4.HibernateTransactionManager"
          autowire-candidate="false">
        <property name="sessionFactory" ref="jsqlTunerSessionFactory"/>
    </bean>

<!--
    <bean id="jsqlTunerTransactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="jsqlTunerEntityManagerFactory"/>
        <property name="dataSource" ref="jsqlTunerDataSource"/>
    </bean>
    -->

</beans>
