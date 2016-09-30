package com.mark.ifamily.util;

/**
 * Created by mark.zhu on 2016/9/29.
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 */
public class FactoryFinder {

    public interface ObjectFactory {
        /**
         * @param path the full service path
         * @return
         */
        public Object create(String path,String key) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException;

    }

    /**
     * The default implementation of Object factory which works well in standalone applications.
     */
    protected static class StandaloneObjectFactory implements ObjectFactory {
        final ConcurrentHashMap<String, Class> classMap = new ConcurrentHashMap<String, Class>();

        public Object create(final String path,final String key) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
            Class clazz = classMap.get(path);
            if (clazz == null) {
                clazz = loadClass(loadProperties(path),key);
                classMap.put(path, clazz);
            }
            return clazz.newInstance();
        }

        static public Class loadClass(Properties properties,String key) throws ClassNotFoundException, IOException {

            String className = properties.getProperty(key);
            if (className == null) {
                throw new IOException("Expected property is missing: class");
            }
            Class clazz = null;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader != null) {
                try {
                    clazz = loader.loadClass(className);
                } catch (ClassNotFoundException e) {
                    // ignore
                }
            }
            if (clazz == null) {
                clazz = FactoryFinder.class.getClassLoader().loadClass(className);
            }

            return clazz;
        }

        static public Properties loadProperties(String uri) throws IOException {
            // lets try the thread context class loader first
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = StandaloneObjectFactory.class.getClassLoader();
            }
            InputStream in = classLoader.getResourceAsStream(uri);
            if (in == null) {
                in = FactoryFinder.class.getClassLoader().getResourceAsStream(uri);
                if (in == null) {
                    throw new IOException("Could not find factory class for resource: " + uri);
                }
            }

            // lets load the file
            BufferedInputStream reader = null;
            try {
                reader = new BufferedInputStream(in);
                Properties properties = new Properties();
                properties.load(reader);
                return properties;
            } finally {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    // ================================================================
    // Class methods and properties
    // ================================================================
    private static ObjectFactory objectFactory = new StandaloneObjectFactory();

    public static ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public static void setObjectFactory(ObjectFactory objectFactory) {
        FactoryFinder.objectFactory = objectFactory;
    }

    // ================================================================
    // Instance methods and properties
    // ================================================================
    private final String path;

    public FactoryFinder(String path) {
        this.path = path;
    }

    /**
     * Creates a new instance of the given key
     *
     * @param key is the key to add to the path to find a text file containing
     *            the factory name
     * @return a newly created instance
     */
    public Object newInstance(String key) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        return objectFactory.create(path, key);
    }


}
