package utils;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtils {
	Configuration conf = new Configuration();
	
	private static SessionFactory sessionAnnotationFactory;
}
