package com.bladeDemo.utils;

import com.bladeDemo.commons.Connection;
import org.hibernate.SessionFactory;

public class Constants {
public static final SessionFactory sessionFactory = Connection.getSessionFactory();

}
