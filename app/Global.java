import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import play.Application;
import play.GlobalSettings;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 01:07
 */
public class Global extends GlobalSettings {

    AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Override
    public void onStart(Application application) {
        ctx.start();
    }

    @Override
    public <T> T getControllerInstance(Class<T> clazz) throws Exception {
        /*if (clazz.isAnnotationPresent(Controller.class)
                || clazz.isAnnotationPresent(Service.class)
                || clazz.isAnnotationPresent(Repository.class)
                || clazz.isAnnotationPresent(Component.class))
            return ctx.getBean(clazz);
        else
            return null;*/
        return ctx.getBean(clazz);
    }
}
