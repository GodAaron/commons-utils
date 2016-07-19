package com.wf2311.commons.velocity.directive;

import org.apache.velocity.runtime.directive.Parse;

/**
 * @author wf2311
 * @time 2016/04/21 21:35.
 */
public class ExtendsDirective extends Parse {
    @Override
    public String getName() {
        return "extends";
    }
}
