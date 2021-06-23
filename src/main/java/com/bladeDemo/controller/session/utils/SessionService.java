package com.bladeDemo.controller.session.utils;

import com.blade.kit.BCrypt;
import com.bladeDemo.commons.models.User;
import com.bladeDemo.controller.session.params.LoginParams;
import com.bladeDemo.controller.session.params.SignUpParams;
import com.bladeDemo.utils.Constants;
import com.bladeDemo.utils.DateUtils;
import com.bladeDemo.utils.SecretKeyGenerator;
import com.bladeDemo.utils.configs.GlobalConfigs;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class SessionService {
    private static GlobalConfigs globalConfigs = GlobalConfigs.getInstance();

    public static Integer insertUser(SignUpParams params){
        Session session = Constants.sessionFactory.openSession();

        User user = new User();
        String token = SecretKeyGenerator.getSecretKey(params.getEmail());

        Map<String, String> password_hash = AuthService.hashPassword(params.getPassword());

        user.setToken(token);
//        user.setFullName(params.getFull_name());
        user.setEmail(params.getEmail());
        user.setPassword(password_hash.get("pw_hash"));
        user.setSalt(password_hash.get("salt"));
        user.setCreatedOn(DateUtils.getCurrentUTCDate());
        user.setUpdatedOn(DateUtils.getCurrentUTCDate());

        try{
            Transaction txn  = session.beginTransaction();

            Integer res = (Integer) session.save(user);
            txn.commit();

            globalConfigs.getThreadPoolsExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Emailer.emailVerification(null, params.getEmail(), token);
                }
            });


            return res;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;

    }

    public static User getUser(Integer id){

        Session session = Constants.sessionFactory.openSession();

        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public static List<User> getAllUsers(){
        Session session = Constants.sessionFactory.openSession();

        try {
            session.beginTransaction();
            List<User> users = session.createQuery("select u from User u" +
                    " where active = :active")
                    .setParameter("active",true)
                    .getResultList();
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;

    }


    public static Object updateEmail(Integer id, String email){

        Session session = Constants.sessionFactory.openSession();

        try {
            session.beginTransaction();
            User user = getUser(id);
            user.setEmail(email);

            session.saveOrUpdate(user);
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }


    public static void forgotPassword(String email){
        Session session = Constants.sessionFactory.openSession();

        try {
            Transaction txn  = session.beginTransaction();

            String token = Emailer.forgotPasswordMailer(email);

            User user = session.createQuery("select u from User u" +
                    " where u.email = :email", User.class)
                    .setParameter("email",email)
                    .getSingleResult();

            user.setToken(token);
            txn.commit();

            return;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return;
    }

    public static User UpdatePassword(String token, String password){
        Session session = Constants.sessionFactory.openSession();

        try {
            Transaction txn  = session.beginTransaction();

            User user = session.createQuery("" +
                    "select u from User u" +
                    " where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();

            Map<String, String> password_hash = AuthService.hashPassword(password);
            user.setPassword(password_hash.get("pw_hash"));
            user.setSalt(password_hash.get("salt"));

            txn.commit();

            return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public static User getUserByToken(String token){
        Session session = Constants.sessionFactory.openSession();

        try {
            session.beginTransaction();

            User user = session.createQuery("" +
                    "select u from User u" +
                    " where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();

            return user;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }


    public static User setUserActive(String token){
        Session session = Constants.sessionFactory.openSession();

        try {
            Transaction txn  = session.beginTransaction();

            User user = session.createQuery("" +
                    "select u from User u" +
                    " where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();
            user.setActive(true);
            user.setToken(null);
            txn.commit();

            return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public static Integer deleteUser(Integer id){
        Session session = Constants.sessionFactory.openSession();

        try {
            session.beginTransaction();
            //TODO
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

   public static User getUserByEmail(String email){
       Session session = Constants.sessionFactory.openSession();

       try {
           session.beginTransaction();

           User user = session.createQuery("select u from User u" +
                   " where u.email = :email", User.class)
                   .setParameter("email",email)
                   .getSingleResult();
           return user;

       }
       catch (Exception e){ }
       finally {
           session.close();
       }
       return null;
    }
   public static Boolean checkUserByEmail(String email){
            return getUserByEmail(email) != null;
    }

   public static Boolean checkUserByToken(String token) throws Exception{
           if (getUserByToken(token) != null)
               return true;

           else
               throw new NoSuchElementException("invalid user");

    }

   public static boolean checkPassword(LoginParams params) {
        User user = getUserByEmail(params.getEmail());

       assert user != null;
       return BCrypt.checkpw(params.getPassword(), user.getPassword());
    }

}
