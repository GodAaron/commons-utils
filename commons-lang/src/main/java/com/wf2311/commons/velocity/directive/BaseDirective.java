package com.wf2311.commons.velocity.directive;

import com.wf2311.commons.velocity.handler.RenderHandler;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * @author wf2311
 * @time 2016/04/21 21:07.
 */
public abstract class BaseDirective extends Directive {
    public abstract void initBean();

    public abstract boolean render(RenderHandler handler) throws RuntimeException, IOException;

    /**
     * 重载init方法，初始自定义指令的配制参数
     *
     * @param rs
     * @param context
     * @param node
     * @throws TemplateInitException
     */
    @Override
    public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {
        super.init(rs, context, node);
        initBean();
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        return render(new RenderHandler(context, writer, node));
    }
}
