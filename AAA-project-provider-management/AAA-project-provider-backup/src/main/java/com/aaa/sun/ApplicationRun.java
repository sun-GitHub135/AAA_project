package com.aaa.sun;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/10 21:20
 * @Description
 */
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
public class ApplicationRun {

}
