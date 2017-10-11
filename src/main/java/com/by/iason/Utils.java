package com.by.iason;

import com.by.iason.config.SimpleContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class Utils {

    public static SimpleContext context() {
        return ((SimpleContext) SecurityContextHolder.getContext());
    }
}
