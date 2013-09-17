package net.isucon.isucon2.application;

import com.sun.jersey.api.view.Viewable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import net.isucon.isucon2.domain.Admin;
import net.isucon.isucon2.domain.OrderRequest;

/**
 * アプリケーション　(ファサード)
 *
 * @author matsumana
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class IsuconApplication {

    @Inject
    Admin admin;
    @Inject
    OrderRequest orderRequest;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response initialize() throws UnsupportedEncodingException, IOException {
        return admin.initialize();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String getCsvData() {
        return admin.getCsvData();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Viewable buy(int variationId, String memberId) {
        return orderRequest.buy(variationId, memberId);
    }
}
