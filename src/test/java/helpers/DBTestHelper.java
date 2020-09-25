package helpers;

import lombok.SneakyThrows;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Dependent
public class DBTestHelper {
    @Inject
    protected EntityManager em;

    private static final String cleanupFile = "cleanDB.xml";
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static IDatabaseConnection con;

    @SneakyThrows
    public void setup() {
        initDatabase();
        cleanDatabase();
    }

    @SneakyThrows
    public void using(final Class<?> testClass, final String file) {
        insertData(testClass, file);
        em.getTransaction().begin();
    }

    @SneakyThrows
    public void teardown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
            em.clear();
        }
    }

    private static Connection getDevConnection()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER).newInstance();

        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tdd_it", "tdd_it", "tdd_it");
    }

    private void initDatabase() throws Exception {
        if (con == null) {
            IDatabaseConnection localCon = new DatabaseConnection(getDevConnection(), "administration");
            DatabaseConfig dbConfig = localCon.getConfig();
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            dbConfig.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);

            con = localCon;
        }
    }

    private static void cleanDatabase() throws DatabaseUnitException, SQLException {
        String path = DBTestHelper.class.getResource(cleanupFile).getPath();
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

    private void insertData(final Class<?> testClass, final String file) throws DatabaseUnitException, SQLException {
        URL dataFile = testClass.getResource(file);
        if (dataFile == null) {
            throw new DatabaseUnitException(file+ " not found");
        }

        String path = dataFile.getPath();
        IDataSet dataSet = getDataSet(path);
        DatabaseOperation.INSERT.execute(con, dataSet);
    }
}
