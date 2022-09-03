package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HibernateUtil {
    private static final Logger log = LogManager.getLogger(HibernateUtil.class.getName());

    private Session session;

    public static void main(String[] args) throws NoSuchFieldException {
        HibernateUtil hibernateUtil = new HibernateUtil();
        Session session = hibernateUtil.getConnection();
        hibernateUtil.isOpen();
        String sql = "select userAccount , username from users";
        List<Object[]> sqlResult = hibernateUtil.getResultSetBySQL(sql);

        for (int i = 0; i < sqlResult.size(); i++) {
            Object[] m = sqlResult.get(i);
            System.out.println(m[0]);
        }
        hibernateUtil.close();
    }

    /**
     * default create session
     */
    public HibernateUtil() {
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
        if (NullUtil.isNotNull(session)) {
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
        if (NullUtil.isNotNull(session)) {
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
     * @return
     */
    public Session getConnection() {
        if (NullUtil.isNull(session)) {
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
     *
     * @param session
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
     *
     * @param session
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
            if (NullUtil.isNotNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.delete(o);
                }
            } else {
                session.delete(obj);
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
            if (NullUtil.isNotNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.delete(o);
                }
            } else {
                session.delete(obj);
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
            if (NullUtil.isNotNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.save(o);
                }
            } else {
                session.save(obj);
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
            if (NullUtil.isNotNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.save(o);
                }
            } else {
                session.save(obj);
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
            if (NullUtil.isNotNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.update(o);
                }
            } else {
                session.update(obj);
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
            if (NullUtil.isNotNull(obj) && ObjectUtil.isArray(obj)) {
                Object[] os = (Object[]) obj;
                for (Object o : os) {
                    session.update(o);
                }
            } else {
                session.update(obj);
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
    public List getResultSetByHQL(String hql) {
        Query query = session.createQuery(hql);
        List entityList = query.list();
        return entityList;
    }

    /**
     * @param sql
     * @return
     */
    public List<Object[]> getResultSetBySQL(String sql) {
        Query query = session.createSQLQuery(sql);
        List<Object[]> entityList = query.getResultList();
        return entityList;
    }
}
