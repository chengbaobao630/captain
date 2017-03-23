package cc.home.framework.proxy;

/**
 * Created by cheng on 2017/3/23 0023.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain)throws Throwable;
}
