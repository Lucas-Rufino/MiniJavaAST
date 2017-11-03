package br.ufpe.cin.if688.minijava.visitor;

import br.ufpe.cin.if688.minijava.ast.And;
import br.ufpe.cin.if688.minijava.ast.ArrayAssign;
import br.ufpe.cin.if688.minijava.ast.ArrayLength;
import br.ufpe.cin.if688.minijava.ast.ArrayLookup;
import br.ufpe.cin.if688.minijava.ast.Assign;
import br.ufpe.cin.if688.minijava.ast.Block;
import br.ufpe.cin.if688.minijava.ast.BooleanType;
import br.ufpe.cin.if688.minijava.ast.Call;
import br.ufpe.cin.if688.minijava.ast.ClassDeclExtends;
import br.ufpe.cin.if688.minijava.ast.ClassDeclSimple;
import br.ufpe.cin.if688.minijava.ast.False;
import br.ufpe.cin.if688.minijava.ast.Formal;
import br.ufpe.cin.if688.minijava.ast.Identifier;
import br.ufpe.cin.if688.minijava.ast.IdentifierExp;
import br.ufpe.cin.if688.minijava.ast.IdentifierType;
import br.ufpe.cin.if688.minijava.ast.If;
import br.ufpe.cin.if688.minijava.ast.IntArrayType;
import br.ufpe.cin.if688.minijava.ast.IntegerLiteral;
import br.ufpe.cin.if688.minijava.ast.IntegerType;
import br.ufpe.cin.if688.minijava.ast.LessThan;
import br.ufpe.cin.if688.minijava.ast.MainClass;
import br.ufpe.cin.if688.minijava.ast.MethodDecl;
import br.ufpe.cin.if688.minijava.ast.Minus;
import br.ufpe.cin.if688.minijava.ast.NewArray;
import br.ufpe.cin.if688.minijava.ast.NewObject;
import br.ufpe.cin.if688.minijava.ast.Not;
import br.ufpe.cin.if688.minijava.ast.Plus;
import br.ufpe.cin.if688.minijava.ast.Print;
import br.ufpe.cin.if688.minijava.ast.Program;
import br.ufpe.cin.if688.minijava.ast.This;
import br.ufpe.cin.if688.minijava.ast.Times;
import br.ufpe.cin.if688.minijava.ast.True;
import br.ufpe.cin.if688.minijava.ast.Type;
import br.ufpe.cin.if688.minijava.ast.VarDecl;
import br.ufpe.cin.if688.minijava.ast.While;
import br.ufpe.cin.if688.minijava.symboltable.Method;
import br.ufpe.cin.if688.minijava.symboltable.Class;
import br.ufpe.cin.if688.minijava.symboltable.SymbolTable;
import br.ufpe.cin.if688.minijava.symboltable.Variable;

public class TypeCheckVisitor implements IVisitor<Type> {
	
	public PrettyPrintVisitor ppv = new PrettyPrintVisitor();

	private SymbolTable symbolTable;
    private Method currMethod;
    private Class currClass;
    private boolean isVariable;
    private boolean isMethod;

	public TypeCheckVisitor(SymbolTable st) {
		symbolTable = st;
		this.currClass = null;
		this.currMethod = null;
		this.isVariable = false;
		this.isMethod = false;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n) {
		this.currClass = this.symbolTable.getClass(n.i1.s);
		this.currMethod = this.symbolTable.getMethod("main", this.currClass.getId());
		n.i1.accept(this);
		this.isVariable = true;
		n.i2.accept(this);
		this.isVariable = false;
		n.s.accept(this);
		this.currMethod = null;
		this.currClass = null;
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		this.currClass = this.symbolTable.getClass(n.i.s);
		n.i.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		this.currClass = null;
		return null;
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n) {
		this.currClass = this.symbolTable.getClass(n.i.s);
		n.i.accept(this);
		n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		this.currClass = null;
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(VarDecl n) {
		Type t = n.t.accept(this);
		this.isVariable = true;
		n.i.accept(this);
		this.isVariable = false;
		return t;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n) {
		this.currMethod = this.symbolTable.getMethod(n.i.toString(), this.currClass.getId());
		Type tMet = n.t.accept(this);
		this.isMethod = true;
		n.i.accept(this);
		this.isMethod = false;
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		Type tExp = n.e.accept(this);
		if(!this.symbolTable.compareTypes(tMet, tExp)) {
			System.err.println("1 error: incompatible types: " + this.getTypeName(tExp) + " cannot be converted to " + this.getTypeName(tMet));
			System.exit(0);
		}
		this.currMethod = null;
		return tMet;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n) {
		Type t = n.t.accept(this);
		this.isVariable = true;
		n.i.accept(this);
		this.isVariable = false;
		return t;
	}

	public Type visit(IntArrayType n) {
		return n;
	}

	public Type visit(BooleanType n) {
		return n;
	}

	public Type visit(IntegerType n) {
		return n;
	}

	// String s;
	public Type visit(IdentifierType n) {
		if(!this.symbolTable.containsClass(n.s)) {
			System.err.println("2 error: cannot find symbol: " + n.s);
			System.exit(0);
		}
		return n;
	}

	// StatementList sl;
	public Type visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n) {
		Type t = n.e.accept(this);
		if(!this.symbolTable.compareTypes(t, new BooleanType())) {
			System.err.println("3 error: incompatible types: " + this.getTypeName(t) + " cannot be converted to BooleanType" );
			System.exit(0);
		}
		n.s1.accept(this);
		n.s2.accept(this);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		Type t = n.e.accept(this);
		if(!this.symbolTable.compareTypes(t, new BooleanType())) {
			System.err.println("4 error: incompatible types: " + this.getTypeName(t) + " cannot be converted to BooleanType" );
			System.exit(0);
		}
		n.s.accept(this);
		return null;
	}

	// Exp e;
	public Type visit(Print n) {
		n.e.accept(this);
		return null;
	}

	// Identifier i;
	// Exp e;
	public Type visit(Assign n) {
		this.isVariable = true;
		Type ti = n.i.accept(this);
		this.isVariable = false;
		Type te = n.e.accept(this);
		if(!this.symbolTable.compareTypes(ti, te)) {
			System.err.println("5 error: incompatible types: " + this.getTypeName(te) + " cannot be converted to " + this.getTypeName(ti) );
			System.exit(0);
		}
		return null;
	}

	// Identifier i;
	// Exp e1,e2;
	public Type visit(ArrayAssign n) {
		this.isVariable = true;
		Type ti = n.i.accept(this);
		this.isVariable = false;
		Type te1 = n.e1.accept(this);
		Type te2 = n.e2.accept(this);
		if(!this.symbolTable.compareTypes(ti, new IntArrayType())) {
			System.err.println("6 error: IntArrayType required, but " + this.getTypeName(ti) + " found");
			System.exit(0);
		}
		if(!this.symbolTable.compareTypes(te1, new IntegerType())) {
			System.err.println("7 error: incompatible types: " + this.getTypeName(te1) + " cannot be converted to IntegerType");
			System.exit(0);
		}
		if(!this.symbolTable.compareTypes(te2, new IntegerType())) {
			System.err.println("8 error: incompatible types: " + this.getTypeName(te2) + " cannot be converted to IntegerType");
			System.exit(0);
		}
		return null;
	}

	// Exp e1,e2;
	public Type visit(And n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		Type b = new BooleanType();
		if((!this.symbolTable.compareTypes(t1, b)) || (!this.symbolTable.compareTypes(t2, b))) {
			System.err.println("9 error: bad operand types for binary operator 'AND'");
			System.exit(0);
		}
		return b;
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		Type i = new IntegerType();
		if((!this.symbolTable.compareTypes(t1, i)) || (!this.symbolTable.compareTypes(t2, i))) {
			System.err.println("10 error: bad operand types for binary operator 'LESS THAN'");
			System.exit(0);
		}
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		Type i = new IntegerType();
		if((!this.symbolTable.compareTypes(t1, i)) || (!this.symbolTable.compareTypes(t2, i))) {
			System.err.println("11 error: bad operand types for binary operator 'PLUS'");
			System.exit(0);
		}
		return i;
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		Type i = new IntegerType();
		if((!this.symbolTable.compareTypes(t1, i)) || (!this.symbolTable.compareTypes(t2, i))) {
			System.err.println("12 error: bad operand types for binary operator 'MINUS'");
			System.exit(0);
		}
		return i;
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		Type i = new IntegerType();
		if((!this.symbolTable.compareTypes(t1, i)) || (!this.symbolTable.compareTypes(t2, i))) {
			System.err.println("13 error: bad operand types for binary operator 'TIMES'");
			System.exit(0);
		}
		return i;
	}

	// Exp e1,e2;
	public Type visit(ArrayLookup n) {
		Type t1 = n.e1.accept(this);
		Type t2 = n.e2.accept(this);
		if(!this.symbolTable.compareTypes(t1, new IntArrayType())) {
			System.err.println("14 error: IntArrayType required, but " + this.getTypeName(t1) + " found");
			System.exit(0);
		}
		if(!this.symbolTable.compareTypes(t2, new IntegerType())) {
			System.err.println("15 error: incompatible types: " + this.getTypeName(t2) + " cannot be converted to IntegerType");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e;
	public Type visit(ArrayLength n) {
		Type t = n.e.accept(this);
		if(!this.symbolTable.compareTypes(t, new IntArrayType())) {
			System.err.println("16 error: IntArrayType required, but " + this.getTypeName(t) + " found");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Type visit(Call n) {
		Type to = n.e.accept(this);
		if(to instanceof IdentifierType) {
			Class cCall = this.symbolTable.getClass(((IdentifierType) to).s);
			Method mCall = this.symbolTable.getMethod(n.i.toString(), cCall.getId());
			Class currClassBK = this.currClass;
			this.currClass = cCall;
			this.isMethod = true;
			Type ti = n.i.accept(this);
			this.isMethod = false;
			this.currClass = currClassBK;
			int count = 0;
			while (count < n.el.size()) {
				Type t1 = n.el.elementAt(count).accept(this);
				Variable v = mCall.getParamAt(count);
				if(v == null) {
					System.err.println("17 error: method " + mCall.getId() + " in class " + cCall.getId() + " cannot be applied to given types. Formal argument lists differ in length");
					System.exit(0);
				} else if(!this.symbolTable.compareTypes(t1, v.type())) {
					System.err.println("18 error: incompatible types: " + this.getTypeName(t1) + " cannot be converted to " + this.getTypeName(v.type()));
					System.exit(0);
				}
				count++;
			}
			if(mCall.getParamAt(count) != null) {
				System.err.println("19 error: method " + mCall.getId() + " in class " + cCall.getId() + " cannot be applied to given types. Formal argument lists differ in length");
				System.exit(0);
			}
			return ti;
		} else {
			System.err.println("20 error: IdentifierType required, but " + this.getTypeName(to) + " found");
			System.exit(0);
		}
		return null;
	}

	// int i;
	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(True n) {
		return new BooleanType();
	}

	public Type visit(False n) {
		return new BooleanType();
	}

	// String s;
	public Type visit(IdentifierExp n) {
		Type t = this.symbolTable.getVarType(this.currMethod, this.currClass, n.s);
		return t;
	}

	public Type visit(This n) {
		return this.currClass.type();
	}

	// Exp e;
	public Type visit(NewArray n) {
		Type t = n.e.accept(this);
		if(!this.symbolTable.compareTypes(t, new IntegerType())) {
			System.err.println("21 error: incompatible types: " + this.getTypeName(t) + " cannot be converted to IntegerType");
			System.exit(0);
		}
		return new IntArrayType();
	}

	// Identifier i;
	public Type visit(NewObject n) {
		return n.i.accept(this);
	}

	// Exp e;
	public Type visit(Not n) {
		Type t = n.e.accept(this);
		Type b = new BooleanType();
		if(!this.symbolTable.compareTypes(t, b)) {
			System.err.println("22 error: bad operand type for unary operator 'NOT'");
			System.exit(0);
		}
		return b;
	}

	// String s;
	public Type visit(Identifier n) {
		if(this.isVariable) {
			return this.symbolTable.getVarType(this.currMethod, this.currClass, n.toString());
		} else if(this.isMethod) {
			return this.symbolTable.getMethodType(n.toString(), this.currClass.getId());
		} else {
			Class c = this.symbolTable.getClass(n.toString());
			if(c == null) {
				System.err.println("23 error: cannot find symbol: " + n.toString());
				System.exit(0);
			}
			return c.type();
		}
	}
	
	private String getTypeName(Type t) {
		if(t instanceof IdentifierType) {
			return ((IdentifierType) t).s;
		} else if(t != null){
			return t.getClass().getSimpleName();
		} else {
			return "null";
		}
	}
}
