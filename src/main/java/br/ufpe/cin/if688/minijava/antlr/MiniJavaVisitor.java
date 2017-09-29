package br.ufpe.cin.if688.minijava.antlr;

import java.util.Iterator;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import br.ufpe.cin.if688.minijava.antlr.xParser.ClassDeclarationContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.ExpressionContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.GoalContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.IdentifierContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.IntegerLiteralContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.MainClassContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.MethodDeclarationContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.StatementContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.TypeContext;
import br.ufpe.cin.if688.minijava.antlr.xParser.VarDeclarationContext;
import br.ufpe.cin.if688.minijava.ast.And;
import br.ufpe.cin.if688.minijava.ast.ArrayAssign;
import br.ufpe.cin.if688.minijava.ast.ArrayLength;
import br.ufpe.cin.if688.minijava.ast.ArrayLookup;
import br.ufpe.cin.if688.minijava.ast.Assign;
import br.ufpe.cin.if688.minijava.ast.Block;
import br.ufpe.cin.if688.minijava.ast.BooleanType;
import br.ufpe.cin.if688.minijava.ast.Call;
import br.ufpe.cin.if688.minijava.ast.ClassDecl;
import br.ufpe.cin.if688.minijava.ast.ClassDeclExtends;
import br.ufpe.cin.if688.minijava.ast.ClassDeclList;
import br.ufpe.cin.if688.minijava.ast.ClassDeclSimple;
import br.ufpe.cin.if688.minijava.ast.Exp;
import br.ufpe.cin.if688.minijava.ast.ExpList;
import br.ufpe.cin.if688.minijava.ast.False;
import br.ufpe.cin.if688.minijava.ast.Formal;
import br.ufpe.cin.if688.minijava.ast.FormalList;
import br.ufpe.cin.if688.minijava.ast.Identifier;
import br.ufpe.cin.if688.minijava.ast.IdentifierType;
import br.ufpe.cin.if688.minijava.ast.If;
import br.ufpe.cin.if688.minijava.ast.IntArrayType;
import br.ufpe.cin.if688.minijava.ast.IntegerLiteral;
import br.ufpe.cin.if688.minijava.ast.IntegerType;
import br.ufpe.cin.if688.minijava.ast.LessThan;
import br.ufpe.cin.if688.minijava.ast.MainClass;
import br.ufpe.cin.if688.minijava.ast.MethodDecl;
import br.ufpe.cin.if688.minijava.ast.MethodDeclList;
import br.ufpe.cin.if688.minijava.ast.Minus;
import br.ufpe.cin.if688.minijava.ast.NewArray;
import br.ufpe.cin.if688.minijava.ast.NewObject;
import br.ufpe.cin.if688.minijava.ast.Not;
import br.ufpe.cin.if688.minijava.ast.Plus;
import br.ufpe.cin.if688.minijava.ast.Print;
import br.ufpe.cin.if688.minijava.ast.Program;
import br.ufpe.cin.if688.minijava.ast.Statement;
import br.ufpe.cin.if688.minijava.ast.StatementList;
import br.ufpe.cin.if688.minijava.ast.This;
import br.ufpe.cin.if688.minijava.ast.Times;
import br.ufpe.cin.if688.minijava.ast.True;
import br.ufpe.cin.if688.minijava.ast.Type;
import br.ufpe.cin.if688.minijava.ast.VarDecl;
import br.ufpe.cin.if688.minijava.ast.VarDeclList;
import br.ufpe.cin.if688.minijava.ast.While;

public class MiniJavaVisitor implements xVisitor<Object> {

	@Override
	public Object visit(ParseTree pt) {
		return pt.accept(this);
	}

	@Override
	public Object visitChildren(RuleNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitGoal(GoalContext ctx) {
		MainClass mc = (MainClass) ctx.mainClass().accept(this);
		
		ClassDeclList cdl = new ClassDeclList();
		Iterator<ClassDeclarationContext> it = ctx.classDeclaration().iterator();
		while(it.hasNext()) {
			cdl.addElement((ClassDecl) it.next().accept(this));
		}
		
		return new Program(mc, cdl);
	}

	@Override
	public Object visitMainClass(MainClassContext ctx) {
		Identifier i1 = (Identifier) ctx.identifier(0).accept(this);
		Identifier i2 = (Identifier) ctx.identifier(1).accept(this);
		Statement s = (Statement) ctx.statement().accept(this);
		return new MainClass(i1, i2, s);
	}

	@Override
	public Object visitClassDeclaration(ClassDeclarationContext ctx) {
		Identifier i = (Identifier) ctx.identifier(0).accept(this);
		
		VarDeclList vdl = new VarDeclList();
		Iterator<VarDeclarationContext> itVdc = ctx.varDeclaration().iterator();
		while(itVdc.hasNext()) {
			vdl.addElement((VarDecl) itVdc.next().accept(this));
		}

		MethodDeclList mdl = new MethodDeclList();
		Iterator<MethodDeclarationContext> itMdc = ctx.methodDeclaration().iterator();
		while(itMdc.hasNext()) {
			mdl.addElement((MethodDecl) itMdc.next().accept(this));
		}
		
		if(ctx.identifier().size() > 1) {
			Identifier i2 = (Identifier) ctx.identifier(1).accept(this);
			return new ClassDeclExtends(i, i2, vdl, mdl);
		} else return new ClassDeclSimple(i, vdl, mdl);
	}

	@Override
	public Object visitVarDeclaration(VarDeclarationContext ctx) {
		Type t = (Type) ctx.type().accept(this);
		Identifier i = (Identifier) ctx.identifier().accept(this);
		return new VarDecl(t, i);
	}

	@Override
	public Object visitMethodDeclaration(MethodDeclarationContext ctx) {
		Type t = (Type) ctx.type(0).accept(this);
		Identifier i = (Identifier) ctx.identifier(0).accept(this);

		FormalList fl = new FormalList();
		Iterator<TypeContext> itt = ctx.type().iterator();
		Iterator<IdentifierContext> itd = ctx.identifier().iterator();
		itt.next();
		itd.next();
		while(itt.hasNext() && itd.hasNext()) {
			Formal f = new Formal((Type) itt.next().accept(this), (Identifier) itd.next().accept(this));
			fl.addElement(f);
		}

		VarDeclList vdl = new VarDeclList();
		Iterator<VarDeclarationContext> itVdc = ctx.varDeclaration().iterator();
		while(itVdc.hasNext()) {
			vdl.addElement((VarDecl) itVdc.next().accept(this));
		}
		
		StatementList sl = new StatementList();
		Iterator<StatementContext> itStm = ctx.statement().iterator();
		while(itStm.hasNext()) {
			sl.addElement((Statement) itStm.next().accept(this));
		}

		Exp e = (Exp) ctx.expression().accept(this);
		return new MethodDecl(t, i, fl, vdl, sl, e);
	}

	@Override
	public Object visitType(TypeContext ctx) {
		String str = ctx.getText();
		switch(str) {
			case "boolean" : return new BooleanType();
			case "int[]"   : return new IntArrayType();
			case "int"     : return new IntegerType();
			default        : return new IdentifierType(str);
		}
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		switch(ctx.getStart().getText()) {
			case "{" : 
				StatementList sl = new StatementList();
				Iterator<StatementContext> itSc = ctx.statement().iterator();
				while(itSc.hasNext()) {
					sl.addElement((Statement) itSc.next().accept(this));
				}
				return new Block(sl);
			case "if" :
				Exp e = (Exp) ctx.expression(0).accept(this);
				Statement s1 = (Statement) ctx.statement(0).accept(this);
				Statement s2 = (Statement) ctx.statement(1).accept(this);
				return new If(e, s1, s2);
			case "while" :
				Exp e1 = (Exp) ctx.expression(0).accept(this);
				Statement s3 = (Statement) ctx.statement(0).accept(this);
				return new While(e1, s3);
			case "System.out.println" :
				return new Print((Exp) ctx.expression(0).accept(this));
			default: 
				if(ctx.expression().size() == 1) {
					Identifier i = (Identifier) ctx.identifier().accept(this);
					Exp e2 = (Exp) ctx.expression(0).accept(this);
					return new Assign(i, e2);
				} else {
					Identifier i = (Identifier) ctx.identifier().accept(this);
					Exp e3 = (Exp) ctx.expression(0).accept(this);
					Exp e4 = (Exp) ctx.expression(1).accept(this);
					return new ArrayAssign(i, e3, e4);
				}
		}
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		int numExp = ctx.expression().size();
		int numChild = ctx.getChildCount();
		
		if(numChild >= 5) {
			String op = ctx.getChild(3).getText();
			if (op.equals("(")) {
				Exp e = (Exp) ctx.expression(0).accept(this);
				Identifier i = (Identifier) ctx.identifier().accept(this);

				ExpList el = new ExpList();
				Iterator<ExpressionContext> ite = ctx.expression().iterator();
				ite.next();
				while(ite.hasNext()) {
					el.addElement((Exp) ite.next().accept(this));
				}

				return new Call(e, i, el);
			}
		}

		if (numExp == 2) {
			Exp e1 = (Exp) ctx.expression(0).accept(this);
			Exp e2 = (Exp) ctx.expression(1).accept(this);
			
			if (numChild == 3) {
				switch(ctx.getChild(1).getText()) {
					case "&&" : return new And(e1, e2);
					case "+"  : return new Plus(e1, e2);
					case "-"  : return new Minus(e1, e2);
					case "<"  : return new LessThan(e1, e2);
					default  : return new Times(e1, e2);
				}
			} else return new ArrayLookup(e1, e2);
		} else if (numExp == 1) {
			Exp e = (Exp) ctx.expression(0).accept(this);
			switch(ctx.getChild(1).getText()) {
				case "!"   : return new Not(e);
				case "."   : return new ArrayLength(e);
				case "("   : return (Exp) ctx.expression(0).accept(this);
				default    : return new NewArray(e);
			}
		} else {
			String s = ctx.getStart().getText();
			switch(s) {
				case "false" : return new False();
				case "this"  : return new This();
				case "true"  : return new True();
				case "new"   : return new NewObject((Identifier) ctx.identifier().accept(this));
				default      :
					if(s.matches("\\d+")) {
						return (IntegerLiteral) ctx.integerLiteral().accept(this);
					} else {
						return (Identifier) ctx.identifier().accept(this);
					}
			}
		}
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		return new Identifier(ctx.getText());
	}

	@Override
	public Object visitIntegerLiteral(IntegerLiteralContext ctx) {
		return new IntegerLiteral(Integer.parseInt(ctx.getText()));
	}
	
}
