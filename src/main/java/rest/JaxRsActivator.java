package rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
    private Set<Object> singletons = new HashSet<>();

    public JaxRsActivator() {
        singletons.add(new BookService());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
