package org.curcon.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@SpringBootApplication
public class CurConApplication extends JpaBaseConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(CurConApplication.class, args);
    }

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}

    @Override
    protected Map<String, Object> getVendorProperties() {
        return new HashMap<String, Object>();
    }

}
