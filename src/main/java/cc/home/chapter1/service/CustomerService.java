package cc.home.chapter1.service;

import cc.home.chapter1.domain.Customer;
import cc.home.chapter1.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by cheng on 2017/3/22 0022.
 */
public class CustomerService {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);

    public List<Customer> getCustomerList(){
            String sql = "select *  from customer";
            return DatabaseHelper.queryEntityList(Customer.class,sql);
    }

    public boolean createCustomer(Customer customer){
        return DatabaseHelper.insertEntity(customer);
    }

    public boolean updateCustomer(Customer customer){
        return DatabaseHelper.updateEntity(customer);
    }

    public boolean deleteCustomer(long id){
        try {
            return DatabaseHelper.deleteEntity(Customer.class,id);
        } catch (IllegalAccessException | InstantiationException e) {
            LOGGER.error("deleteCustomer due error",e);
            return false;
        }
    }

}
