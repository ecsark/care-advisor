import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 01:07
 */
public class Global extends GlobalSettings {

    private class ActionWrapper extends Action.Simple {
        public ActionWrapper(Action<?> action) {
            this.delegate = action;
        }

        @Override
        public Promise<Result> call(Http.Context ctx) throws java.lang.Throwable {
            Promise<Result> result = this.delegate.call(ctx);
            Http.Response response = ctx.response();
            response.setHeader("Access-Control-Allow-Origin", "*");
            return result;
        }
    }

    @Override
    public Action<?> onRequest(Http.Request request, java.lang.reflect.Method actionMethod) {
        return new ActionWrapper(super.onRequest(request, actionMethod));
    }

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
