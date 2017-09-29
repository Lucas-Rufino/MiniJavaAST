package br.ufpe.cin.if688.minijava.antlr;

// Generated from x.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link xParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface xVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link xParser#goal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoal(xParser.GoalContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(xParser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(xParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(xParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(xParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(xParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(xParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(xParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(xParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link xParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(xParser.IntegerLiteralContext ctx);
}