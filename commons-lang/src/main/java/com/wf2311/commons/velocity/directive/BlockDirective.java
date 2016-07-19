package com.wf2311.commons.velocity.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * @author wf2311
 * @time 2016/04/21 21:36.
 */
public class BlockDirective extends Directive {

    /**
     * Return the name of this directive.
     *
     * @return The name of this directive.
     */
    @Override
    public String getName() {
        return "block";
    }

    /**
     * Get the directive type BLOCK/LINE.
     *
     * @return The directive type BLOCK/LINE.
     */
    @Override
    public int getType() {
        return BLOCK;
    }

    /**
     * How this directive is to be rendered
     *
     * @param context
     * @param writer
     * @param node
     * @return True if the directive rendered successfully.
     * @throws IOException
     * @throws ResourceNotFoundException
     * @throws ParseErrorException
     * @throws MethodInvocationException
     */
    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String name = DirectiveUtils.getRequiredArgument(context, node, 0, getName());

        OverrideNodeWrapper overrideNode = getOverrideNode(context, name);
        Node topNode = node.jjtGetChild(1);
        if (overrideNode == null) {
            return topNode.render(context, writer);
        } else {
            DirectiveUtils.setParentForTop(new OverrideNodeWrapper(topNode), overrideNode);
            return overrideNode.render(context, writer);
        }
    }

    private OverrideNodeWrapper getOverrideNode(InternalContextAdapter context, String name) {
        return (OverrideNodeWrapper) context.get(DirectiveUtils.getOverrideVariableName(name));
    }
}
