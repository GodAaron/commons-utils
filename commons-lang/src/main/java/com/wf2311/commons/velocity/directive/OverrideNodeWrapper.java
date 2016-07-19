package com.wf2311.commons.velocity.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import java.io.IOException;
import java.io.Writer;

/**
 * @author wf2311
 * @time 2016/04/21 21:38.
 */
public class OverrideNodeWrapper extends SimpleNode {
    Node current;

    OverrideNodeWrapper parentNode;


    public OverrideNodeWrapper(Node node) {
        super(1);
        this.current = node;
    }


    public boolean render(InternalContextAdapter context, Writer writer)
            throws IOException, MethodInvocationException, ParseErrorException, ResourceNotFoundException {
        OverrideNodeWrapper preNode = (OverrideNodeWrapper) context.get(DirectiveUtils.OVERRIDE_CURRENT_NODE);
        try {
            context.put(DirectiveUtils.OVERRIDE_CURRENT_NODE, this);
            return current.render(context, writer);
        } finally {
            context.put(DirectiveUtils.OVERRIDE_CURRENT_NODE, preNode);
        }
    }
}
