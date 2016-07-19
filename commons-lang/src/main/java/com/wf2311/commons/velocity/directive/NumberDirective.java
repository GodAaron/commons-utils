package com.wf2311.commons.velocity.directive;

import com.wf2311.commons.velocity.handler.RenderHandler;

import java.io.IOException;

/**
 * 数字处理
 * - 大于1000的显示1k
 * - 大于10000的显示1m
 *
 * @author wf2311
 * @time 2016/04/21 21:06.
 */
public class NumberDirective extends BaseDirective {
    @Override
    public void initBean() {
    }

    @Override
    public boolean render(RenderHandler handler) throws RuntimeException, IOException {
        long value = handler.getLongParameter(0);
        Object out = value;

        if (value > 1000) {
            out = value / 1000 + "k";
        } else if (value > 10000) {
            out = value / 10000 + "m";
        }
        handler.write(out.toString());
        return true;
    }

    /**
     * Return the name of this directive.
     *
     * @return The name of this directive.
     */
    @Override
    public String getName() {
        return "num";
    }

    /**
     * Get the directive type BLOCK/LINE.
     *
     * @return The directive type BLOCK/LINE.
     */
    @Override
    public int getType() {
        return LINE;
    }
}
