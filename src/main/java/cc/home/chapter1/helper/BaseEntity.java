package cc.home.chapter1.helper;

/**
 * Created by cheng on 2016/8/29 0029.
 */
public abstract class BaseEntity {


    public  abstract <T> T getId() ;


    public String getKeyColumn(){
        return "id";
    }
}
