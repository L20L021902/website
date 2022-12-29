package letscode;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Start implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //do stuff
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //Init database tables
        if (!Database.initTables()) {
            System.out.println("Couldn't init databases!");
            System.exit(-1);
        }

        Mock.mockRegister();
        Mock.mockGoods();
        Mock.mockClients();
    }
}
