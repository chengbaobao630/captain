package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by cheng on 2017/3/23 0023.
 */
public class CglibProxy implements MethodInterceptor {

    private static CglibProxy cglibProxy = new CglibProxy();

    private CglibProxy() {
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invoke(o,objects);
        after();
        return result;
    }

    private void after() {
    }

    private void before() {
    }

    public <T> T getProxy(Class<T> cls){
        return (T) Enhancer.create(cls,this);
    }


    public static CglibProxy getInstance(){
        return cglibProxy;
    }
}
