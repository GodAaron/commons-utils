package com.wf2311.commons.velocity.handler;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.tools.view.ViewToolContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wf2311
 * @time 2016/04/21 20:56.
 */
public class RenderHandler extends BaseHandler {
    private InternalContextAdapter context;
    private Writer writer;
    private Node node;

    private Map<String, Object> parameters = new HashMap<String, Object>();

    public RenderHandler(InternalContextAdapter context, Writer writer, Node node) {
        this.context = context;
        this.writer = writer;
        this.node = node;
    }

    public void put(String key, Object value) {
        parameters.put(key, value);
    }

    public void doRender() throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
        Map<String, Object> reduceMap = reduce();
        getBody().render(context, writer);
        reduce(reduceMap);
    }

    private Map<String, Object> reduce() {
        Map<String, Object> reduceMap = new HashMap<>();

        for (String key : parameters.keySet()){
            if (context.containsKey(key)){
                reduceMap.put(key,context.get(key));
            }
            context.put(key,context.get(key));
        }
       return reduceMap;
    }

    private void reduce(Map<String, Object> reduceMap) {
        for (String key : parameters.keySet()) {
            context.remove(key);
        }

        for (String key:reduceMap.keySet()){
            context.put(key,reduceMap.get(key));
        }
    }

    public void write(String str) throws IOException {
        writer.write(str);
    }

    public HttpServletRequest getRequest() {
        ViewToolContext viewContext = (ViewToolContext)context.getInternalUserContext();
        return viewContext.getRequest();

    }
    public Writer getWriter() {
        return writer;
    }

    public InternalContextAdapter getContext() {
        return context;
    }

    public Node getNode() {
        return node;
    }

    public Node getParameter(int index) {
        if (node.jjtGetNumChildren() > index && node.jjtGetChild(index) != null) {
            return node.jjtGetChild(index);
        }
        return null;
    }

    public int getIntParameter(int index) {
        if (node.jjtGetNumChildren() > index && node.jjtGetChild(index) != null) {
            Node n = node.jjtGetChild(index);

            return toInt(n.value(context));
        }
        return 0;
    }

    public long getLongParameter(int index) {
        if (node.jjtGetNumChildren() > index && node.jjtGetChild(index) != null) {
            Node n = node.jjtGetChild(index);
            return toLong(n.value(context));
        }
        return 0;
    }

    public String getStringParameter(int index) {
        if (node.jjtGetNumChildren() > index && node.jjtGetChild(index) != null) {
            Node n = node.jjtGetChild(index);
            return (String) n.value(context);
        }
        return null;
    }

    public Node getBody() {
        if (node.jjtGetNumChildren() > 0) {
            return node.jjtGetChild(node.jjtGetNumChildren() - 1);
        }
        return null;
    }
}
