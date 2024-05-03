package com.ace.tools;

import com.ace.utilities.Console;
import com.ace.utilities.NullUtil;
import com.ace.utilities.ObjectUtil;
import com.ace.utilities.entity.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;


class HibernateTool {
    private static final Logger log = LogManager.getLogger(HibernateTool.class.getName());

    private Session session;

    public static void main(String[] args) throws NoSuchFieldException {
        HibernateTool hibernateUtil = new HibernateTool();
        Session session = hibernateUtil.getConnection();
        hibernateUtil.isOpen();
        String hql = "select userAccount , username from users";
        String sql = "select * from users";
        List<Users> sqlResult = hibernateUtil.getResultSetBySQL(sql, Users.class);
     //   List<Users> hqlResult = hibernateUtil.getResultSetByHQL(hql, Users.class);

        for (Users user : sqlResult) {
            System.out.println(user.getUserAccount());
        }

        hibernateUtil.close();
    }

    /**
     * default create session
     */
    public HibernateTool() {
        session = null;
        try {
            StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
            session = sf.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test the connection
     */
    public void isOpen(Session session) {
        String status;
        if (NullUtil.isNonNull(session)) {
            status = String.valueOf(session.isOpen());
            if (session.isOpen()) {
                Console.println("connection : " + status, Console.BOLD, Console.BLUE);
            } else {
                Console.println("connection : " + status, Console.BOLD, Console.RED);
            }
        } else {
            Console.println("connection : status is null ", Console.BOLD, Console.RED);
        }

    }

    /**
     * test the connection
     */
    public void isOpen() {
        //   String status;
        if (NullUtil.isNonNull(session)) {
            String status = String.valueOf(session.isOpen());
            if (session.isOpen()) {
                Console.println("connection : " + status, Console.BOLD, Console.BLUE);
            } else {
                Console.println("connection : " + status, Console.BOLD, Console.RED);
            }
        } else {
            Console.println("connection : status is null ", Console.BOLD, Console.RED);
        }

    }

    /**
     * connection to db
     * get Session
     *
     * @return Session
     */
    public Session getConnection() {
        if (NullUtil.isNull(session)) {
            log.info("getResourceAsStream(\"hibernate.cfg.xml\")");
            try {
                StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
                SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
                session = sf.openSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return session;
    }

    /**
     * submit request to db
     */
    public void commitTransaction(Session session) {
        try {
            Transaction tx = session.beginTransaction();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * submit request to db
     */
    public boolean commitTransaction() {
        boolean status = false;
        try {
            Transaction tx = session.beginTransaction();
            tx.commit();
            Console.println("transaction status : " + tx.getStatus(), Console.BOLD, Console.BLUE);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * close connection
     */
    public void close(Session session) {
        try {
            Console.println("session close ******", Console.BLUE, Console.BOLD);
            session.close();
            Console.println("EXIT SYSTEM ******", Console.BLUE, Console.BOLD);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close connection
     */
    public void close() {
        try {
            Console.println("session close ******", Console.BLUE, Console.BOLD);
            session.close();
            Console.println("EXIT SYSTEM ******", Console.BLUE, Console.BOLD);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delById(Session session, Object obj) {
        try {
            if (NullUtil.isNonNull(obj) && Boolean.TRUE.equals(ObjectUtil.isArray(obj))) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.remove(o);
                }
            } else {
                session.remove(obj);
            }

            Console.println("deleted and waiting for submit ....", Console.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delById(Object obj) {
        try {
            if (NullUtil.isNonNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.remove(o);
                }
            } else {
                session.remove(obj);
            }
            Console.println("deleted and waiting for submit ....", Console.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveById(Session session, Object obj) {
        try {
            if (NullUtil.isNonNull(obj) && Boolean.TRUE.equals(ObjectUtil.isArray(obj))) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.persist(o);
                }
            } else {
                session.persist(obj);
            }
            Console.println("saved and waiting for submit ....", Console.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveById(Object obj) {
        try {
            if (NullUtil.isNonNull(obj) && Boolean.TRUE.equals(ObjectUtil.isArray(obj))) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.persist(o);
                }
            } else {
                session.persist(obj);
            }
            Console.println("saved and waiting for submit ....", Console.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateById(Session session, Object obj) {
        try {
            if (NullUtil.isNonNull(obj) && Boolean.TRUE.equals(ObjectUtil.isArray(obj))) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.merge(o);
                }
            } else {
                session.merge(obj);
            }
            Console.println("updated and waiting for submit ....", Console.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param obj
     * @return
     */
    public boolean updateById(Object obj) {
        try {
            if (NullUtil.isNonNull(obj) && Boolean.TRUE.equals(ObjectUtil.isArray(obj))) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.merge(o);
                }
            } else {
                session.merge(obj);
            }
            Console.println("updated and waiting for submit ....", Console.YELLOW);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param hql
     * @return
     */
    public <R> List getResultSetByHQL(String hql, Class<R> resultClass) {
        Query query = session.createQuery(hql, resultClass);
        List entityList = query.list();
        return entityList;
    }

    /**
     * @param sql
     * @return
     */
    public <R> List getResultSetBySQL(String sql, Class<R> resultClass) {
        //hibernate 5
        //Query query = session.createSQLQuery(sql);

        //hibernate 6
        Query query = session.createNativeQuery(sql, resultClass);
        List entityList = query.getResultList();
        return entityList;
    }
}
