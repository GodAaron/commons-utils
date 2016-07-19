package com.wf2311.commons.velocity.directive;

import com.wf2311.commons.velocity.handler.RenderHandler;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;

/**
 * @author wf2311
 * @time 2016/04/21 21:27.
 */
public class OverrideDirective extends BaseDirective {
    @Override
    public String getName() {
        return "override";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public void initBean() {

    }

    @Override
    public boolean render(RenderHandler handler) throws RuntimeException, IOException {
        String name = DirectiveUtils.getRequiredArgument(handler.getContext(), handler.getNode(), 0, getName());
        OverrideNodeWrapper override = (OverrideNodeWrapper) handler.getContext().get(DirectiveUtils.getOverrideVariableName(name));
        if (override == null) {
            Node body = handler.getNode().jjtGetChild(1);
            handler.getContext().put(DirectiveUtils.getOverrideVariableName(name), new OverrideNodeWrapper(body));
        } else {
            OverrideNodeWrapper current = new OverrideNodeWrapper(handler.getNode().jjtGetChild(1));
            DirectiveUtils.setParentForTop(current, override);
        }
        return true;
    }
}
