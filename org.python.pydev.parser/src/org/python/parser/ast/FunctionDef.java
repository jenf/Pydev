// Autogenerated AST node
package org.python.parser.ast;
import java.io.DataOutputStream;
import java.io.IOException;

import org.python.parser.SimpleNode;

public class FunctionDef extends stmtType {
	/**
     * 
     */
    private static final long serialVersionUID = -5071587659591504278L;
    public String name;
	public argumentsType args;
	public stmtType[] body;

	public FunctionDef(String name, argumentsType args, stmtType[] body) {
		this.name = name;
		this.args = args;
		this.body = body;
	}

	public FunctionDef(String name, argumentsType args, stmtType[] body,
	SimpleNode parent) {
		this(name, args, body);
		this.beginLine = parent.beginLine;
		this.beginColumn = parent.beginColumn;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("FunctionDef[");
		sb.append("name=");
		sb.append(dumpThis(this.name));
		sb.append(", ");
		sb.append("args=");
		sb.append(dumpThis(this.args));
		sb.append(", ");
		sb.append("body=");
		sb.append(dumpThis(this.body));
		sb.append("]");
		return sb.toString();
	}

	public void pickle(DataOutputStream ostream) throws IOException {
		pickleThis(5, ostream);
		pickleThis(this.name, ostream);
		pickleThis(this.args, ostream);
		pickleThis(this.body, ostream);
	}

	public Object accept(VisitorIF visitor) throws Exception {
		return visitor.visitFunctionDef(this);
	}

	public void traverse(VisitorIF visitor) throws Exception {
		if (args != null)
			args.accept(visitor);
		if (body != null) {
			for (int i = 0; i < body.length; i++) {
				if (body[i] != null)
					body[i].accept(visitor);
			}
		}
	}

}
