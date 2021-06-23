package com.bladeDemo.connectors.utils;

import com.bladeDemo.controller.session.utils.SessionService;
import com.bladeDemo.commons.models.User;
import com.bladeDemo.commons.models.quickbooks.*;
import com.bladeDemo.connectors.params.PullTrackerParams;
import com.bladeDemo.connectors.params.QBBusinessParams;
import com.bladeDemo.utils.Constants;
import com.bladeDemo.utils.DateUtils;
import com.bladeDemo.utils.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.oauth2.exception.OAuthException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QBService {

    public static int createQBBusiness(QBBusinessParams params, String email) throws OAuthException {
        Session session = Constants.sessionFactory.openSession();

        User user = SessionService.getUserByEmail(email);

        QBBusiness qbBusiness = new QBBusiness();


        try {
            Transaction txn  = session.beginTransaction();

            Map<String, String> tokens = QuickBooksOAuth.getBearerToken(params.getCode());
            String token = tokens.get("token");
            String refresh_token = tokens.get("refresh-token");

            qbBusiness.setToken(token);
            qbBusiness.setRealmId(params.getRealmId());
            qbBusiness.setRefreshToken(refresh_token);
            qbBusiness.setConnectionDate(DateUtils.getCurrentUTCDate());
            qbBusiness.setCode(params.getCode());
//            qbBusiness.setUser(user);

            int id = (int) session.save(qbBusiness);
            txn.commit();
            return id;
        } catch (OAuthException e){
            throw new OAuthException(e.getMessage());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            session.close();
        }
    }




    public static QBBusiness getQBBusinessById(Integer id){

        Session session = Constants.sessionFactory.openSession();

        try{
            session.beginTransaction();

            QBBusiness qb = session.get(QBBusiness.class, id);
            return qb;
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }

    public static List<QBBusiness> getAllQBBusinesses(){

        Session session = Constants.sessionFactory.openSession();
        List<QBBusiness> res = null;

        try{
            session.beginTransaction();

            res = session.createQuery("select q from QBBusiness q",
                    QBBusiness.class)
                    .getResultList();

            return res;
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return res;
    }


    public static QBBusiness getQBBusinessByRealmId(String realmId){

        Session session = Constants.sessionFactory.openSession();

        try{
            session.beginTransaction();

            QBBusiness qb = session.createQuery("select q from QBBusiness q" +
                    " where q.realmId = :realmId", QBBusiness.class)
                    .setParameter("realmId", realmId)
                    .getSingleResult();

            return qb;
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }

    public static void updateQBBusinessTokens(Integer id, String token, String refresh_token){

        Session session = Constants.sessionFactory.openSession();

        try{
            session.beginTransaction();

            QBBusiness qb = getQBBusinessById(id);
            qb.setToken(token);
            qb.setRefreshToken(refresh_token);

            session.getTransaction().commit();

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }

    }

    public void saveObject(Object ob){
        Session session = Constants.sessionFactory.openSession();

        try{
            Transaction txn = session.beginTransaction();
            session.saveOrUpdate(ob);
            txn.commit();

        }finally {
            session.close();
        }
    }

    public void batchSaveObject(Map<String, Object> map, String modelName){
        Session session = Constants.sessionFactory.openSession();
        Transaction txn = session.beginTransaction();

        int totalDataCount = (int) map.get("total-count");
        System.out.println("Total count= "+ totalDataCount);

        ArrayList data = (ArrayList) map.get(modelName);
        int batchSize = getBatchSize(totalDataCount);

        try{

            for (int i=0; i< totalDataCount; i++){
                session.saveOrUpdate(data.get(i));

                if(i % batchSize == 0){
                    System.out.println("clearing first level catch with batch of " + batchSize);

                    session.flush();
                    session.clear();
                }

            }

            txn.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    private int getBatchSize(int totalDataCount) {
        if (totalDataCount >= 5000)
            return 500;
        else{
            if (totalDataCount >= 4999)
                return 300;
            else
                return 50;
        }
    }

    public <T> T setCompositeKey(T element){
        return null;
    }

    public ArrayList SwitchObject(String modelName, String data, int userId) throws JsonProcessingException {
        ArrayList arrayList = null;

            User user = SessionService.getUser(userId);
            CompositeId compositeId = null;
            List list = JSON.parse(data, ArrayList.class);

            switch (modelName) {
                case "Account":

                    arrayList = new ArrayList();


                    for (Object o : list) {
                        compositeId = new CompositeId();
                        Account account = JSON.parse(JSON.json(o), Account.class);
                        compositeId.setId(account.getId());
                        account.setUser(user);
                        account.setCompositeId(compositeId);
                        arrayList.add(account);
                    }
                    return arrayList;

                case "Bill":
                    arrayList = new ArrayList();

                    for (Object o : list) {
                        compositeId = new CompositeId();
                        Bill bill = JSON.parse(JSON.json(o), Bill.class);
                        compositeId.setId(bill.getId());
                        bill.setUser(user);
                        bill.setCompositeId(compositeId);
                        arrayList.add(bill);
                    }

                    return arrayList;

                case "BillPayment":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        BillPayment billPayment = JSON.parse(JSON.json(o), BillPayment.class);
                        compositeId.setId(billPayment.getId());
                        billPayment.setUser(user);
                        billPayment.setCompositeId(compositeId);
                        arrayList.add(billPayment);
                    }


                    return arrayList;

                case "Customer":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        Customer customer = JSON.parse(JSON.json(o), Customer.class);
                        compositeId.setId(customer.getId());
                        customer.setUser(user);
                        customer.setCompositeId(compositeId);

                        arrayList.add(customer);
                    }


                    return arrayList;
//
                case "Invoice":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        Invoice invoice = JSON.parse(JSON.json(o), Invoice.class);
                        compositeId.setId(invoice.getId());
                        invoice.setUser(user);
                        invoice.setCompositeId(compositeId);

                        arrayList.add(invoice);
                    }


                    return arrayList;

                case "Payment":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        Payment payment = JSON.parse(JSON.json(o), Payment.class);
                        compositeId.setId(payment.getId());
                        payment.setUser(user);
                        payment.setCompositeId(compositeId);

                        arrayList.add(payment);
                    }


                    return arrayList;
//
                case "PaymentMethod":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        PaymentMethod paymentMethod = JSON.parse(JSON.json(o), PaymentMethod.class);
                        compositeId.setId(paymentMethod.getId());
                        paymentMethod.setUser(user);
                        paymentMethod.setCompositeId(compositeId);

                        arrayList.add(paymentMethod);
                    }


                    return arrayList;

                case "RefundReceipt":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        RefundReceipt refundReceipt = JSON.parse(JSON.json(o), RefundReceipt.class);
                        compositeId.setId(refundReceipt.getId());
                        refundReceipt.setUser(user);
                        refundReceipt.setCompositeId(compositeId);

                        arrayList.add(refundReceipt);
                    }


                    return arrayList;

                case "SalesReceipt":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        SalesReceipt sR = JSON.parse(JSON.json(o), SalesReceipt.class);
                        compositeId.setId(sR.getId());
                        sR.setUser(user);
                        sR.setCompositeId(compositeId);

                        arrayList.add(sR);
                    }


                    return arrayList;

                case "Vendor":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        Vendor vendor = JSON.parse(JSON.json(o), Vendor.class);
                        compositeId.setId(vendor.getId());
                        vendor.setUser(user);
                        vendor.setCompositeId(compositeId);

                        arrayList.add(vendor);
                    }


                    return arrayList;

                case "VendorCredit":

                    arrayList = new ArrayList();
                    for (Object o : list) {
                        compositeId = new CompositeId();
                        VendorCredit vC = JSON.parse(JSON.json(o), VendorCredit.class);
                        compositeId.setId(vC.getId());
                        vC.setUser(user);
                        vC.setCompositeId(compositeId);

                        arrayList.add(vC);
                    }


                    return arrayList;


                default:
                    System.out.println("Default Switch Method");
                    return null;
            }


    }


    // Pull Tracker

    public static QBPullTracker createTrackerItem(PullTrackerParams params){
        Session session = Constants.sessionFactory.openSession();
        QBPullTracker qbPullTracker = new QBPullTracker();


        try{
            Transaction txn = session.beginTransaction();


            qbPullTracker.setUser(params.getUser());
            qbPullTracker.setTableName(params.getTableName());

            qbPullTracker.setLastPullDateLimit(params.getConnectionDate());

            int id = (int) session.save(qbPullTracker);
            txn.commit();
            return getTrackerById(id);
        } catch (Exception e){
            e.printStackTrace();
            return qbPullTracker;
        } finally {
            session.close();
        }


    }

    public static QBPullTracker getTrackerById(int id){
        Session session = Constants.sessionFactory.openSession();

        try{
            session.beginTransaction();

            QBPullTracker tracker = session.get(QBPullTracker.class, id);

            return tracker;

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }

    public static QBPullTracker getTrackerByUserNTabName(User user, String tableName){
        Session session = Constants.sessionFactory.openSession();

        try{
            session.beginTransaction();

            String query = "select t from QBPullTracker t where t.tableName = :tableName and t.user = :user";

            QBPullTracker qbPullTracker = (QBPullTracker) session.createQuery(query)
                    .setParameter("tableName", tableName)
                    .setParameter("user", user).getSingleResult();

            if (qbPullTracker != null)
                return qbPullTracker;

            else
                return null;

        } catch (Exception exception){
            return null;

        }finally {
            session.close();
        }

    }

    public static void UpdateLastPullTime(QBPullTracker tracker, LocalDateTime dateTime){
        Session session = Constants.sessionFactory.openSession();

        try{
            session.beginTransaction();
            tracker.setLastPullDateLimit(dateTime);

            session.getTransaction().commit();

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}
