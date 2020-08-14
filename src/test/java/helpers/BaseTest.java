package helpers;

import daos.EmProducer;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.xml.sax.InputSource;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Application;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@EnableAutoWeld
@AddPackages(EmProducer.class)
public class BaseTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(Void.class); // Dummy Resource
    }

    @Inject
    protected EntityManager em;

    private static final String cleanupFile = "cleanDB.xml";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static IDatabaseConnection con;

    public static Connection getDevConnection()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tdd_it", "tdd_it", "tdd_it");
    }

    private void initDb() throws Exception {
        if (con == null) {
            IDatabaseConnection localCon = new DatabaseConnection(getDevConnection(), "administration");
            DatabaseConfig dbConfig = localCon.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            dbConfig.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);

            con = localCon;
        }
    }

    @BeforeEach
    public void beginTransaction(TestInfo info) throws Exception {
        Method method = info.getTestMethod().orElseThrow(() -> new UnexpectedException(""));

        DBSetup classAnnotation = this.getClass().getAnnotation(DBSetup.class);
        DBSetup methodAnnotation = method.getAnnotation(DBSetup.class);

        if (classAnnotation != null || methodAnnotation != null) {
            initDb();
            cleanDatabase();
            insertData(methodAnnotation == null ? classAnnotation : methodAnnotation);
            em.getTransaction().begin();
        }
        super.setUp();
    }

    @AfterEach
    public void commit() throws Exception {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
            em.clear();
        }
        super.tearDown();
    }

    private static void cleanDatabase() throws DatabaseUnitException, SQLException {
        String path = BaseTest.class.getResource(cleanupFile).getPath();
        IDataSet dataSet = getDataSet(path);
        DatabaseOperation.DELETE_ALL.execute(con, dataSet);
    }

    private static IDataSet getDataSet(String path) throws DatabaseUnitException {
        path = path.replaceAll("file:/", "");
        if ("\\".equals(File.separator)) {
            path = path.replaceAll("/", File.separator + File.separator);
        }
        File xmlFile = new File(path);
        if (xmlFile.exists()) {
            FlatXmlProducer producer = new FlatXmlProducer(new InputSource(xmlFile.getAbsolutePath()));
            producer.setColumnSensing(true);
            return new FlatXmlDataSet(producer);
        } else {
            throw new DatabaseUnitException(path + " not found");
        }
    }

    private void insertData(final DBSetup annotation) throws DatabaseUnitException, SQLException {
        if (annotation.value().equals("")) {
            return;
        }
        URL dataFile = this.getClass().getResource(annotation.value());
        if (dataFile == null) {
            throw new DatabaseUnitException(annotation.value() + " not found");
        }

        String path = dataFile.getPath();
        IDataSet dataSet = getDataSet(path);
        DatabaseOperation.INSERT.execute(con, dataSet);
    }
}
