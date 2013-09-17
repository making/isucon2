package net.isucon.isucon2.domain;

import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.Transient;
import net.isucon.isucon2.repository.IsuonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基底ドメイン
 *
 * @author matsumana
 */
public class BaseDomain implements Serializable {

    @Inject
    @Transient
    IsuonRepository repository;
    @Transient
    Logger logger = LoggerFactory.getLogger(getClass());
}
