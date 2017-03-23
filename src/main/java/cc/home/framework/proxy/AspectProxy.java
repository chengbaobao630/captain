package cc.home.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by cheng on 2017/3/23 0023.
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object params = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(cls, method, params)) {
                before(cls,method,params);
                result = proxyChain.doProxyChain();
                after(cls,method,params);

            }else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy due error",e);
            error(cls,method,params,e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    private boolean intercept(Class<?> cls, Method method, Object params) {
        return true;
    }

    protected  void end(){}
    protected  void error(Class<?> cls, Method method, Object params, Exception e){}
    protected  void after(Class<?> cls, Method method, Object params){}
    protected  void before(Class<?> cls, Method method, Object params){}
    protected  void begin(){}
}
